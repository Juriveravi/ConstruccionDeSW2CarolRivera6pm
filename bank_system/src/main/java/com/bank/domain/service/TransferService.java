package com.bank.domain.service;

import com.bank.domain.enums.AccountStatus;
import com.bank.domain.enums.TransferStatus;
import com.bank.domain.model.BankAccount;
import com.bank.domain.model.OperationLog;
import com.bank.domain.model.Transfer;
import com.bank.domain.port.BankAccountRepository;
import com.bank.domain.port.OperationLogRepository;
import com.bank.domain.port.TransferRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final BankAccountRepository bankAccountRepository;
    private final OperationLogRepository operationLogRepository;

    /** Monto máximo sin aprobación (configurable en application.properties) */
    @Value("${bank.transfer.approval-threshold:5000000}")
    private BigDecimal approvalThreshold;

    public TransferService(TransferRepository transferRepository,
                           BankAccountRepository bankAccountRepository,
                           OperationLogRepository operationLogRepository) {
        this.transferRepository = transferRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.operationLogRepository = operationLogRepository;
    }

    @Transactional
    public Transfer createTransfer(String originAccountNumber, String destinationAccountNumber,
                                   BigDecimal amount, Long creatorUserId) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }

        BankAccount origin = getActiveAccount(originAccountNumber);

        Transfer transfer = new Transfer();
        transfer.setOriginAccount(originAccountNumber);
        transfer.setDestinationAccount(destinationAccountNumber);
        transfer.setAmount(amount);
        transfer.setCreationDate(LocalDateTime.now());
        transfer.setCreatorUserId(creatorUserId);

        // Determinar si requiere aprobación
        if (amount.compareTo(approvalThreshold) > 0) {
            transfer.setStatus(TransferStatus.PENDING_APPROVAL);
        } else {
            transfer.setStatus(TransferStatus.PENDING_APPROVAL);
            executeTransfer(transfer, origin, creatorUserId);
        }

        return transferRepository.save(transfer);
    }

    @Transactional
    public Transfer approveTransfer(Long transferId, Long approverUserId) {
        Transfer transfer = getTransfer(transferId);

        if (transfer.getStatus() != TransferStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("La transferencia no está en espera de aprobación.");
        }

        BankAccount origin = getActiveAccount(transfer.getOriginAccount());

        if (origin.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new IllegalStateException("Saldo insuficiente en la cuenta origen.");
        }

        transfer.setApprovalDate(LocalDateTime.now());
        transfer.setApproverUserId(approverUserId);
        executeTransfer(transfer, origin, approverUserId);

        return transferRepository.save(transfer);
    }

    @Transactional
    public Transfer rejectTransfer(Long transferId, Long approverUserId) {
        Transfer transfer = getTransfer(transferId);

        if (transfer.getStatus() != TransferStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("La transferencia no está en espera de aprobación.");
        }

        transfer.setStatus(TransferStatus.REJECTED);
        transfer.setApproverUserId(approverUserId);

        return transferRepository.save(transfer);
    }

    /** Tarea programada: expira transferencias pendientes > 60 minutos */
    @Scheduled(fixedDelay = 60_000) // cada minuto
    @Transactional
    public void expireStaleTransfers() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(60);
        List<Transfer> stale = transferRepository.findPendingOlderThan(threshold);

        for (Transfer t : stale) {
            t.setStatus(TransferStatus.EXPIRED);
            transferRepository.save(t);

            operationLogRepository.save(new OperationLog(null,
                    "VENCIMIENTO_TRANSFERENCIA",
                    LocalDateTime.now(),
                    t.getCreatorUserId(), "SISTEMA",
                    String.valueOf(t.getId()),
                    Map.of(
                            "motivo", "Falta de aprobación a tiempo",
                            "fechaVencimiento", LocalDateTime.now().toString(),
                            "idCreador", t.getCreatorUserId())));
        }
    }

    public Transfer getTransfer(Long transferId) {
        return transferRepository.findById(transferId)
                .orElseThrow(() -> new IllegalArgumentException("Transferencia no encontrada: " + transferId));
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private void executeTransfer(Transfer transfer, BankAccount origin, Long actorUserId) {
        if (origin.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new IllegalStateException("Saldo insuficiente en la cuenta origen.");
        }

        BigDecimal originBefore = origin.getBalance();
        origin.setBalance(originBefore.subtract(transfer.getAmount()));
        bankAccountRepository.save(origin);

        BigDecimal destBefore = BigDecimal.ZERO;
        BigDecimal destAfter = BigDecimal.ZERO;
        bankAccountRepository.findByAccountNumber(transfer.getDestinationAccount()).ifPresent(dest -> {
            dest.setBalance(dest.getBalance().add(transfer.getAmount()));
            bankAccountRepository.save(dest);
        });

        transfer.setStatus(TransferStatus.EXECUTED);

        operationLogRepository.save(new OperationLog(null,
                "TRANSFERENCIA_EJECUTADA",
                LocalDateTime.now(),
                actorUserId, "SISTEMA",
                String.valueOf(transfer.getId()),
                Map.of(
                        "monto", transfer.getAmount(),
                        "saldoAntesOrigen", originBefore,
                        "saldoDespuesOrigen", origin.getBalance(),
                        "cuentaDestino", transfer.getDestinationAccount())));
    }

    private BankAccount getActiveAccount(String accountNumber) {
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada: " + accountNumber));
        if (account.getStatus() == AccountStatus.BLOCKED || account.getStatus() == AccountStatus.CANCELLED) {
            throw new IllegalStateException("La cuenta " + accountNumber + " no está operativa.");
        }
        return account;
    }
}