package com.bank.application.dto;

import com.bank.domain.model.BankAccount;
import com.bank.domain.model.Client;
import com.bank.domain.model.Loan;
import com.bank.domain.model.Transfer;

public final class DtoMapper {

    private DtoMapper() {
    }

    public static ClientResponse toClientResponse(Client client) {
        String clientType = client.getClass().getSimpleName();
        return new ClientResponse(
                client.getId(),
                client.getDocumentId(),
                client.getName(),
                client.getIdentification(),
                client.getEmail(),
                client.getPhone(),
                client.getAddress(),
                client.getStatus(),
                clientType
        );
    }

    public static BankAccountResponse toBankAccountResponse(BankAccount account) {
        return new BankAccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getType(),
                account.getOwnerId(),
                account.getBalance(),
                account.getCurrency(),
                account.getStatus(),
                account.getOpeningDate()
        );
    }

    public static LoanResponse toLoanResponse(Loan loan) {
        return new LoanResponse(
                loan.getId(),
                loan.getType(),
                loan.getClientDocumentId(),
                loan.getRequestedAmount(),
                loan.getApprovedAmount(),
                loan.getInterestRate(),
                loan.getTermMonths(),
                loan.getStatus(),
                loan.getApprovalDate(),
                loan.getDisbursementDate(),
                loan.getTargetAccountNumber()
        );
    }

    public static TransferResponse toTransferResponse(Transfer transfer) {
        return new TransferResponse(
                transfer.getId(),
                transfer.getOriginAccount(),
                transfer.getDestinationAccount(),
                transfer.getAmount(),
                transfer.getCreationDate(),
                transfer.getApprovalDate(),
                transfer.getStatus(),
                transfer.getCreatorUserId(),
                transfer.getApproverUserId()
        );
    }
}
