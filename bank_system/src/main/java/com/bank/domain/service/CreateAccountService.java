package com.bank.domain.service;

import com.bank.domain.enums.AccountStatus;
import com.bank.domain.enums.AccountType;
import com.bank.domain.enums.Currency;
import com.bank.domain.enums.UserStatus;
import com.bank.domain.model.BankAccount;
import com.bank.domain.model.Client;
import com.bank.domain.port.BankAccountRepository;
import com.bank.domain.port.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Service responsible for creating and opening bank accounts.
 * Single Responsibility: Account creation operations.
 */
@Service
public class CreateAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final ClientRepository clientRepository;

    public CreateAccountService(BankAccountRepository bankAccountRepository,
                                ClientRepository clientRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.clientRepository = clientRepository;
    }

    /**
     * Open a new bank account for an active client.
     *
     * @param clientDocumentId the client's document ID
     * @param type the type of account
     * @param currency the currency
     * @return the created bank account
     * @throws IllegalArgumentException if client not found
     * @throws IllegalStateException if client is not active
     */
    @Transactional
    public BankAccount openAccount(String clientDocumentId, AccountType type, Currency currency) {
        Client client = clientRepository.findByDocumentId(clientDocumentId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + clientDocumentId));

        if (client.getStatus() == UserStatus.INACTIVE || client.getStatus() == UserStatus.BLOCKED) {
            throw new IllegalStateException("Cannot open account for a client with status " + client.getStatus());
        }

        BankAccount account = new BankAccount();
        account.setAccountNumber(generateAccountNumber());
        account.setType(type);
        account.setOwnerId(clientDocumentId);
        account.setBalance(BigDecimal.ZERO);
        account.setCurrency(currency);
        account.setStatus(AccountStatus.ACTIVE);
        account.setOpeningDate(LocalDate.now());

        return bankAccountRepository.save(account);
    }

    /**
     * Generate a unique account number.
     *
     * @return a unique account number
     */
    private String generateAccountNumber() {
        return "ACC-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
    }
}
