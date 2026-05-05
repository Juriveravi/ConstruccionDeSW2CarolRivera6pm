-- Cliente de prueba
MERGE INTO clients AS t
USING (SELECT 'DOC1234' AS document_id) AS s ON t.document_id = s.document_id
WHEN NOT MATCHED THEN
    INSERT (identification, email, phone, address, name, document_id, status, client_type)
    VALUES ('12345678', 'test@example.com', '555-0100', '123 Main Street', 'Test Client', 'DOC1234', 'ACTIVE', 'NATURAL_PERSON');

-- Cuenta de ahorros
MERGE INTO bank_accounts AS t
USING (SELECT 'ACC-0000000001' AS account_number) AS s ON t.account_number = s.account_number
WHEN NOT MATCHED THEN
    INSERT (account_number, type, owner_id, balance, currency, status, opening_date)
    VALUES ('ACC-0000000001', 'SAVINGS', 'DOC1234', 10000.00, 'COP', 'ACTIVE', '2026-05-01');

-- Cuenta corriente
MERGE INTO bank_accounts AS t
USING (SELECT 'ACC-0000000002' AS account_number) AS s ON t.account_number = s.account_number
WHEN NOT MATCHED THEN
    INSERT (account_number, type, owner_id, balance, currency, status, opening_date)
    VALUES ('ACC-0000000002', 'CHECKING', 'DOC1234', 5000.00, 'COP', 'ACTIVE', '2026-05-01');

-- Prestamo de prueba
MERGE INTO loans AS t
USING (SELECT 'DOC1234' AS client_document_id, 'PERSONAL' AS type) AS s
    ON t.client_document_id = s.client_document_id AND t.type = s.type
WHEN NOT MATCHED THEN
    INSERT (type, client_document_id, requested_amount, approved_amount, interest_rate, term_months, status, approval_date, disbursement_date, target_account_number)
    VALUES ('PERSONAL', 'DOC1234', 500000.00, NULL, NULL, 12, 'UNDER_REVIEW', NULL, NULL, NULL);

-- Transferencia de prueba
MERGE INTO transfers AS t
USING (SELECT 'ACC-0000000001' AS origin_account, 'ACC-0000000002' AS destination_account, 'PENDING_APPROVAL' AS status) AS s
    ON t.origin_account = s.origin_account AND t.destination_account = s.destination_account AND t.status = s.status
WHEN NOT MATCHED THEN
    INSERT (origin_account, destination_account, amount, creation_date, approval_date, status, creator_user_id, approver_user_id)
    VALUES ('ACC-0000000001', 'ACC-0000000002', 1500.00, GETDATE(), NULL, 'PENDING_APPROVAL', 1, NULL);