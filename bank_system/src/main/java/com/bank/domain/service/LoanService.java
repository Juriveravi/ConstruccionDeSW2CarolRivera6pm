package com.bank.domain.service;

import com.bank.domain.enums.LoanStatus;
import com.bank.domain.enums.LoanType;
import com.bank.domain.model.Loan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Facade service for loan operations.
 * Coordinates between CreateLoanService and ApproveLoanService.
 */
@Service
public class LoanService {

    private final CreateLoanService createLoanService;
    private final ApproveLoanService approveLoanService;

    public LoanService(CreateLoanService createLoanService,
                       ApproveLoanService approveLoanService) {
        this.createLoanService = createLoanService;
        this.approveLoanService = approveLoanService;
    }

    /**
     * Request a new loan (delegates to CreateLoanService).
     */
    @Transactional
    public Loan requestLoan(String clientDocumentId, LoanType type,
                            BigDecimal requestedAmount, int termMonths) {
        return createLoanService.requestLoan(clientDocumentId, type, requestedAmount, termMonths);
    }

    /**
     * Approve a loan (delegates to ApproveLoanService).
     */
    @Transactional
    public Loan approveLoan(Long loanId, BigDecimal approvedAmount,
                            BigDecimal interestRate) {
        return approveLoanService.approveLoan(loanId, approvedAmount, interestRate);
    }

    /**
     * Reject a loan (delegates to ApproveLoanService).
     */
    @Transactional
    public Loan rejectLoan(Long loanId) {
        return approveLoanService.rejectLoan(loanId);
    }

    /**
     * Disburse a loan (delegates to ApproveLoanService).
     */
    @Transactional
    public Loan disburseLoan(Long loanId) {
        return approveLoanService.disburseLoan(loanId);
    }

    /**
     * Get a loan by ID (delegates to CreateLoanService).
     */
    public Loan getLoan(Long loanId) {
        return createLoanService.getLoan(loanId);
    }

    /**
     * Get loans by client (delegates to CreateLoanService).
     */
    public List<Loan> getLoansByClient(String documentId) {
        return createLoanService.getLoansByClient(documentId);
    }

    /**
     * Get loans by status (delegates to CreateLoanService).
     */
    public List<Loan> getLoansByStatus(LoanStatus status) {
        return createLoanService.getLoansByStatus(status);
    }
}