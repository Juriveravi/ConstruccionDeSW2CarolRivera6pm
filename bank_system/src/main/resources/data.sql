INSERT INTO clients (identification, email, phone, address, name, document_id, status, client_type)
VALUES ('12345678', 'test@example.com', '555-0100', '123 Main Street', 'Test Client', 'DOC1234', 'ACTIVE', 'NATURAL_PERSON');

INSERT INTO bank_accounts (account_number, type, owner_id, balance, currency, status, opening_date)
VALUES ('ACC-0000000001', 'SAVINGS', 'DOC1234', 10000.00, 'COP', 'ACTIVE', '2026-05-01');

INSERT INTO bank_accounts (account_number, type, owner_id, balance, currency, status, opening_date)
VALUES ('ACC-0000000002', 'CHECKING', 'DOC1234', 5000.00, 'COP', 'ACTIVE', '2026-05-01');

INSERT INTO loans (type, client_document_id, requested_amount, approved_amount, interest_rate, term_months, status, approval_date, disbursement_date, target_account_number)
VALUES ('PERSONAL', 'DOC1234', 500000.00, NULL, NULL, 12, 'UNDER_REVIEW', NULL, NULL, NULL);

INSERT INTO transfers (origin_account, destination_account, amount, creation_date, approval_date, status, creator_user_id, approver_user_id)
VALUES ('ACC-0000000001', 'ACC-0000000002', 1500.00, GETDATE(), NULL, 'PENDING_APPROVAL', 1, NULL);
