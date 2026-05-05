package com.bank.domain.service;

import com.bank.domain.enums.LoanStatus;
import com.bank.domain.enums.LoanType;
import com.bank.domain.enums.UserStatus;
import com.bank.domain.model.Client;
import com.bank.domain.model.Loan;
import com.bank.domain.port.ClientRepository;
import com.bank.domain.port.LoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service responsible for creating and managing loan requests.
 * Single Responsibility: Processing loan requests from clients.
 */
@Service
public class CreateLoanService {

    private final LoanRepository loanRepository;
    private final ClientRepository clientRepository;

    public CreateLoanService(LoanRepository loanRepository,
                             ClientRepository clientRepository) {
        this.loanRepository = loanRepository;
        this.clientRepository = clientRepository;
    }

    /**
     * Create a new loan request for a client.
     *
     * @param clientDocumentId the client's document ID
     * @param loanType the type of loan
     * @param requestedAmount the requested loan amount
     * @param termMonths the loan term in months
     * @return the created loan in UNDER_REVIEW status
     * @throws IllegalArgumentException if client not found
     * @throws IllegalStateException if client is not active
     */
    @Transactional
    public Loan requestLoan(String clientDocumentId, LoanType loanType,
                            BigDecimal requestedAmount, int termMonths) {
        Client client = clientRepository.findByDocumentId(clientDocumentId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + clientDocumentId));

        if (client.getStatus() != UserStatus.ACTIVE) {
            throw new IllegalStateException("The client is not active.");
        }

        Loan loan = new Loan();
        loan.setType(loanType);
        loan.setClientDocumentId(clientDocumentId);
        loan.setRequestedAmount(requestedAmount);
        loan.setTermMonths(termMonths);
        loan.setStatus(LoanStatus.UNDER_REVIEW);

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

    /**
     * Get all loans for a specific client.
     *
     * @param documentId the client's document ID
     * @return list of loans
     */
    public List<Loan> getLoansByClient(String documentId) {
        return loanRepository.findByClientDocumentId(documentId);
    }

    /**
     * Get all loans with a specific status.
     *
     * @param status the loan status
     * @return list of loans
     */
    public List<Loan> getLoansByStatus(LoanStatus status) {
        return loanRepository.findByStatus(status);
    }
}
