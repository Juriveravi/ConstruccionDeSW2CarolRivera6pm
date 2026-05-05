# SRP Refactoring - Banco System Services

## 📋 Resumen de Cambios

Se ha refactorizado la arquitectura de servicios del sistema bancario aplicando el **Principio de Responsabilidad Única (SRP)**, donde cada servicio ahora tiene **una única razón para cambiar**.

---

## 🔄 Servicios Nuevos Creados

### 1. **BankAccountQueryService**
**Responsabilidad:** Consultas y búsquedas de cuentas bancarias

**Métodos:**
```java
public BankAccount findByAccountNumber(String accountNumber)
public List<BankAccount> findByOwner(String documentId)
```

**Inyección en controladores:**
```java
private final BankAccountQueryService bankAccountQueryService;

// En constructor
public BankAccountController(BankAccountQueryService queryService) {
    this.bankAccountQueryService = queryService;
}
```

---

### 2. **LoanRequestService**
**Responsabilidad:** Creación y gestión de solicitudes de préstamo

**Métodos principales:**
```java
public Loan requestLoan(String clientDocumentId, LoanType type, 
                        BigDecimal requestedAmount, int termMonths)
public Loan getLoan(Long loanId)
public List<Loan> getLoansByClient(String documentId)
public List<Loan> getLoansByStatus(LoanStatus status)
```

---

### 3. **LoanApprovalService**
**Responsabilidad:** Aprobación, rechazo y desembolso de préstamos

**Métodos principales:**
```java
public Loan approveLoan(Long loanId, BigDecimal approvedAmount, 
                        BigDecimal interestRate)
public Loan rejectLoan(Long loanId)
public Loan disburseLoan(Long loanId)
```

---

### 4. **TransferApprovalService**
**Responsabilidad:** Aprobación y rechazo de transferencias

**Métodos principales:**
```java
public Transfer approveTransfer(Long transferId)
public Transfer rejectTransfer(Long transferId)
```

---

### 5. **TransferExecutionService**
**Responsabilidad:** Ejecución de transferencias y gestión de caducidad

**Métodos principales:**
```java
public Transfer executeTransfer(Long transferId)
@Scheduled(fixedDelay = 60_000)
public void expireStaleTransfers()
```

---

## ✏️ Servicios Existentes Actualizados

### **BankAccountService**
**Antes:** Creación + búsquedas de cuentas  
**Ahora:** Solo creación/apertura de cuentas

```java
// ✅ Mantiene:
public BankAccount openAccount(String clientDocumentId, AccountType type, 
                               Currency currency)

// ❌ Removido (migrado a BankAccountQueryService):
public BankAccount findByAccountNumber(String accountNumber)
public List<BankAccount> findByOwner(String documentId)
```

---

### **ClientService**
**Antes:** CRUD + logging de operaciones  
**Ahora:** Solo CRUD de clientes

```java
public Client createClient(Client client)
public Client findByDocumentId(String documentId)
public Client updateStatus(String documentId, UserStatus status)

// ❌ Removido: Logging de operaciones
// Usar OperationLogService como servicio separado
```

---

### **LoanService**
**Patrón:** Servicio Facade (Fachada)

Se convierte en un orquestador que delega a servicios especializados:
```java
public Loan requestLoan(...)        // → Delega a LoanRequestService
public Loan approveLoan(...)        // → Delega a LoanApprovalService
public Loan rejectLoan(...)         // → Delega a LoanApprovalService
public Loan disburseLoan(...)       // → Delega a LoanApprovalService
```

---

### **TransferService**
**Antes:** Creación + aprobación + ejecución + expiración  
**Ahora:** Solo creación de transferencias

```java
// ✅ Mantiene:
public Transfer createTransfer(String originAccountNumber, 
                               String destinationAccountNumber, 
                               BigDecimal amount)

// ❌ Removido (migrado a servicios especializados):
public Transfer approveTransfer(Long transferId)          // → TransferApprovalService
public Transfer rejectTransfer(Long transferId)           // → TransferApprovalService
public void expireStaleTransfers()                         // → TransferExecutionService
// (método privado) executeTransfer()                      // → TransferExecutionService
```

---

## 🔌 Cómo Actualizar los Controladores

### Ejemplo: **BankAccountController**

**Antes:**
```java
@Service
public class BankAccountController {
    private final BankAccountService bankAccountService;
    
    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccount> getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(bankAccountService.findByAccountNumber(accountNumber));
    }
}
```

**Después:**
```java
@Service
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final BankAccountQueryService bankAccountQueryService;
    
    public BankAccountController(BankAccountService bankAccountService,
                                BankAccountQueryService bankAccountQueryService) {
        this.bankAccountService = bankAccountService;
        this.bankAccountQueryService = bankAccountQueryService;
    }
    
    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccount> getAccount(@PathVariable String accountNumber) {
        // ✅ Usa el servicio de consulta
        return ResponseEntity.ok(bankAccountQueryService.findByAccountNumber(accountNumber));
    }
    
    @PostMapping
    public ResponseEntity<BankAccount> openAccount(@RequestBody OpenAccountRequest request) {
        // ✅ Usa el servicio de creación
        BankAccount account = bankAccountService.openAccount(
            request.getClientDocumentId(),
            request.getType(),
            request.getCurrency()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }
}
```

### Ejemplo: **LoanController**

**Actualizaciones necesarias:**
```java
@Service
public class LoanController {
    private final LoanRequestService loanRequestService;
    private final LoanApprovalService loanApprovalService;
    
    public LoanController(LoanRequestService loanRequestService,
                         LoanApprovalService loanApprovalService) {
        this.loanRequestService = loanRequestService;
        this.loanApprovalService = loanApprovalService;
    }
    
    @PostMapping
    public ResponseEntity<Loan> requestLoan(@RequestBody LoanRequest request) {
        // ✅ Usa LoanRequestService
        Loan loan = loanRequestService.requestLoan(
            request.getClientDocumentId(),
            request.getType(),
            request.getRequestedAmount(),
            request.getTermMonths()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(loan);
    }
    
    @PutMapping("/{loanId}/approve")
    public ResponseEntity<Loan> approveLoan(
        @PathVariable Long loanId,
        @RequestBody ApprovalRequest request) {
        // ✅ Usa LoanApprovalService
        Loan loan = loanApprovalService.approveLoan(
            loanId,
            request.getApprovedAmount(),
            request.getInterestRate()
        );
        return ResponseEntity.ok(loan);
    }
}
```

---

## 🧪 Cambios en Tests

Los tests deben actualizar sus mocks:

**Antes:**
```java
@Test
public void testFindByAccountNumber() {
    BankAccountService service = new BankAccountService(repository, clientRepository);
    service.findByAccountNumber("ACC-123");
}
```

**Después:**
```java
@Test
public void testFindByAccountNumber() {
    BankAccountQueryService service = new BankAccountQueryService(repository);
    service.findByAccountNumber("ACC-123");
}
```

---

## 📊 Tabla Comparativa

| Servicio | Responsabilidad Anterior | Responsabilidad Ahora |
|----------|--------------------------|----------------------|
| **BankAccountService** | Crear + Consultar cuentas | Solo crear cuentas |
| **BankAccountQueryService** | N/A | Consultar cuentas |
| **ClientService** | CRUD + Logging | Solo CRUD |
| **LoanService** | Solicitud + Aprobación + Desembolso | Orquestación (delegación) |
| **LoanRequestService** | N/A | Solicitudes de préstamo |
| **LoanApprovalService** | N/A | Aprobación/rechazo/desembolso |
| **TransferService** | Crear + Aprobar + Ejecutar + Expirar | Solo crear transferencias |
| **TransferApprovalService** | N/A | Aprobar/rechazar transferencias |
| **TransferExecutionService** | N/A | Ejecutar/expirar transferencias |

---

## ✅ Validación de SRP

Cada servicio ahora tiene una única responsabilidad clara:

- ✅ **BankAccountService**: Crear cuentas
- ✅ **BankAccountQueryService**: Consultar cuentas
- ✅ **ClientService**: Gestionar información de clientes
- ✅ **LoanRequestService**: Procesar solicitudes de préstamo
- ✅ **LoanApprovalService**: Evaluar y aprobar préstamos
- ✅ **TransferService**: Crear solicitudes de transferencia
- ✅ **TransferApprovalService**: Validar transferencias
- ✅ **TransferExecutionService**: Ejecutar transferencias
- ✅ **OperationLogService**: Registrar operaciones

---

## 🚀 Próximos Pasos

1. **Actualizar controladores** - Inyectar los nuevos servicios
2. **Revisar y actualizar tests** - Usar los servicios correctos
3. **Actualizar documentación API** - Reflejar las nuevas responsabilidades
4. **Considerar Aspect-Oriented Programming (AOP)** para el logging transversal de operaciones

