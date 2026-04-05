package com.bank.domain.service;

import com.bank.domain.enums.AccountStatus;
import com.bank.domain.enums.LoanStatus;
import com.bank.domain.enums.LoanType;
import com.bank.domain.enums.UserStatus;
import com.bank.domain.model.BankAccount;
import com.bank.domain.model.Client;
import com.bank.domain.model.Loan;
import com.bank.domain.model.OperationLog;
import com.bank.domain.port.BankAccountRepository;
import com.bank.domain.port.ClientRepository;
import com.bank.domain.port.LoanRepository;
import com.bank.domain.port.OperationLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final ClientRepository clientRepository;
    private final BankAccountRepository bankAccountRepository;
    private final OperationLogRepository operationLogRepository;

    public LoanService(LoanRepository loanRepository,
                       ClientRepository clientRepository,
                       BankAccountRepository bankAccountRepository,
                       OperationLogRepository operationLogRepository) {
        this.loanRepository = loanRepository;
        this.clientRepository = clientRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.operationLogRepository = operationLogRepository;
    }

    @Transactional
    public Loan requestLoan(String clientDocumentId, LoanType type,
                            BigDecimal requestedAmount, int termMonths) {
        Client client = clientRepository.findByDocumentId(clientDocumentId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + clientDocumentId));

        if (client.getStatus() != UserStatus.ACTIVE) {
            throw new IllegalStateException("The client is not active.");
        }

        Loan loan = new Loan();
        loan.setType(type);
        loan.setClientDocumentId(clientDocumentId);
        loan.setRequestedAmount(requestedAmount);
        loan.setTermMonths(termMonths);
        loan.setStatus(LoanStatus.UNDER_REVIEW);

        return loanRepository.save(loan);
    }

    @Transactional
    public Loan approveLoan(Long loanId, BigDecimal approvedAmount,
                            BigDecimal interestRate, Long analystUserId) {
        Loan loan = getLoan(loanId);

        if (loan.getStatus() != LoanStatus.UNDER_REVIEW) {
            throw new IllegalStateException("Only loans in UNDER_REVIEW status can be approved.");
        }

        loan.setApprovedAmount(approvedAmount);
        loan.setInterestRate(interestRate);
        loan.setStatus(LoanStatus.APPROVED);
        loan.setApprovalDate(LocalDate.now());

        operationLogRepository.save(buildLog("APROBACION_PRESTAMO", analystUserId, "ANALISTA",
                String.valueOf(loanId), Map.of(
                        "montoAprobado", approvedAmount,
                        "tasa", interestRate,
                        "estadoAnterior", "EN_ESTUDIO",
                        "nuevoEstado", "APROBADO",
                        "idAnalista", analystUserId)));

        return loanRepository.save(loan);
    }

    @Transactional
    public Loan rejectLoan(Long loanId, Long analystUserId) {
        Loan loan = getLoan(loanId);

        if (loan.getStatus() != LoanStatus.UNDER_REVIEW) {
            throw new IllegalStateException("Only loans in UNDER_REVIEW status can be rejected.");
        }

        loan.setStatus(LoanStatus.REJECTED);

        operationLogRepository.save(buildLog("RECHAZO_PRESTAMO", analystUserId, "ANALISTA",
                String.valueOf(loanId), Map.of("estadoAnterior", "EN_ESTUDIO", "nuevoEstado", "RECHAZADO")));

        return loanRepository.save(loan);
    }

    @Transactional
    public Loan disburseLoan(Long loanId, Long analystUserId) {
        Loan loan = getLoan(loanId);

        if (loan.getStatus() != LoanStatus.APPROVED) {
            throw new IllegalStateException("Only APPROVED loans can be disbursed.");
        }
        if (loan.getApprovedAmount() == null || loan.getApprovedAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("The approved amount must be greater than zero.");
        }
        if (loan.getTargetAccountNumber() == null) {
            throw new IllegalStateException("The target account for disbursement must be defined.");
        }

        BankAccount target = bankAccountRepository.findByAccountNumber(loan.getTargetAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Target account not found."));

        if (target.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("The target account is not active.");
        }

        BigDecimal balanceBefore = target.getBalance();
        target.setBalance(balanceBefore.add(loan.getApprovedAmount()));
        bankAccountRepository.save(target);

        loan.setStatus(LoanStatus.DISBURSED);
        loan.setDisbursementDate(LocalDate.now());

        operationLogRepository.save(buildLog("DESEMBOLSO_PRESTAMO", analystUserId, "ANALISTA",
                String.valueOf(loanId), Map.of(
                        "monto", loan.getApprovedAmount(),
                        "cuentaDestino", target.getAccountNumber(),
                        "saldoAntes", balanceBefore,
                        "saldoDespues", target.getBalance())));

        return loanRepository.save(loan);
    }

    public Loan getLoan(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found: " + loanId));
    }

    public List<Loan> getLoansByClient(String documentId) {
        return loanRepository.findByClientDocumentId(documentId);
    }

    public List<Loan> getLoansByStatus(LoanStatus status) {
        return loanRepository.findByStatus(status);
    }

    private OperationLog buildLog(String type, Long userId, String role,
                                  String productId, Map<String, Object> details) {
        return new OperationLog(null, type, LocalDateTime.now(), userId, role, productId, details);
    }
}