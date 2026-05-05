package com.bank.domain.service;

import com.bank.domain.enums.AccountStatus;
import com.bank.domain.enums.LoanStatus;
import com.bank.domain.model.BankAccount;
import com.bank.domain.model.Loan;
import com.bank.domain.port.BankAccountRepository;
import com.bank.domain.port.LoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Service responsible for approving, rejecting, and disbursing loans.
 * Single Responsibility: Loan approval and disbursement operations.
 */
@Service
public class ApproveLoanService {

    private final LoanRepository loanRepository;
    private final BankAccountRepository bankAccountRepository;

    public ApproveLoanService(LoanRepository loanRepository,
                              BankAccountRepository bankAccountRepository) {
        this.loanRepository = loanRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Approve a loan with specified amount and interest rate.
     *
     * @param loanId the loan ID to approve
     * @param approvedAmount the approved amount
     * @param interestRate the interest rate
     * @return the approved loan
     * @throws IllegalArgumentException if loan not found
     * @throws IllegalStateException if loan is not in UNDER_REVIEW status
     */
    @Transactional
    public Loan approveLoan(Long loanId, BigDecimal approvedAmount,
                            BigDecimal interestRate) {
        Loan loan = getLoan(loanId);

        if (loan.getStatus() != LoanStatus.UNDER_REVIEW) {
            throw new IllegalStateException("Only loans in UNDER_REVIEW status can be approved.");
        }

        loan.setApprovedAmount(approvedAmount);
        loan.setInterestRate(interestRate);
        loan.setStatus(LoanStatus.APPROVED);
        loan.setApprovalDate(LocalDate.now());

        return loanRepository.save(loan);
    }

    /**
     * Reject a loan request.
     *
     * @param loanId the loan ID to reject
     * @return the rejected loan
     * @throws IllegalArgumentException if loan not found
     * @throws IllegalStateException if loan is not in UNDER_REVIEW status
     */
    @Transactional
    public Loan rejectLoan(Long loanId) {
        Loan loan = getLoan(loanId);

        if (loan.getStatus() != LoanStatus.UNDER_REVIEW) {
            throw new IllegalStateException("Only loans in UNDER_REVIEW status can be rejected.");
        }

        loan.setStatus(LoanStatus.REJECTED);

        return loanRepository.save(loan);
    }

    /**
     * Disburse an approved loan to the target account.
     *
     * @param loanId the loan ID to disburse
     * @return the disbursed loan
     * @throws IllegalArgumentException if loan or target account not found
     * @throws IllegalStateException if loan is not APPROVED or account is not ACTIVE
     */
    @Transactional
    public Loan disburseLoan(Long loanId) {
        Loan loan = getLoan(loanId);

        if (loan.getStatus() != LoanStatus.APPROVED) {
            throw new IllegalStateException("Only APPROVED loans can be disbursed.");
        }
        if (loan.getApprovedAmount() == null || loan.getApprovedAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("The approved amount must be greater than zero.");
        }
        if (loan.getTargetAccountNumber() == null || loan.getTargetAccountNumber().isEmpty()) {
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

        return loanRepository.save(loan);
    }

    /**
     * Retrieve a loan by its ID.
     *
     * @param loanId the loan ID
     * @return the loan
     * @throws IllegalArgumentException if loan not found
     */
    public Loan getLoan(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found: " + loanId));
    }
}
