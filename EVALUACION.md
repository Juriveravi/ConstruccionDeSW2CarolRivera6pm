# EVALUACIÓN - ConstruccionDeSW2CarolRivera6pm

## Información General
- **Estudiante(s):** Carol Julieth Rivera Villa (Juriveravi)
- **Rama evaluada:** develop
- **Fecha de evaluación:** 2026-03-23

---

## Tabla de Calificación

| # | Criterio | Peso | Puntaje (1–5) | Nota ponderada |
|---|---|---|---|---|
| 1 | Modelado de dominio | 25% | 4 | 1.00 |
| 2 | Relaciones entre entidades | 15% | 4 | 0.60 |
| 3 | Uso de Enums | 15% | 3 | 0.45 |
| 4 | Manejo de estados | 5% | 3 | 0.15 |
| 5 | Tipos de datos | 5% | 4 | 0.20 |
| 6 | Separación Usuario vs Cliente | 10% | 4 | 0.40 |
| 7 | Bitácora | 5% | 4 | 0.20 |
| 8 | Reglas básicas de negocio | 5% | 3 | 0.15 |
| 9 | Estructura del proyecto | 10% | 3 | 0.30 |
| 10 | Repositorio | 10% | 1 | 0.10 |
| **TOTAL** | | **100%** | | **3.55** |

## Penalizaciones
- Ninguna (código en inglés, nombres correctos).

## Bonus
- +2: Herencia correcta (`Client` abstracta → `NaturalPerson` / `Company`) ✓ → +0.2

## Nota Final: 3.75 / 5.0

---

## Análisis por Criterio

### 1. Modelado de dominio — 4/5
Entidades presentes: `Client` (abstracta), `NaturalPerson`, `Company`, `User`, `BankAccount`, `Loan`, `Transfer`, `OperationLog`. Falta `BankingProduct` como catálogo de productos bancarios. Las principales entidades del dominio están cubiertas con buena definición.

### 2. Relaciones entre entidades — 4/5
`BankAccount` tiene `Client owner` ✓. `User` tiene `Client associatedClient` ✓. Los servicios `LoanService` y `TransferService` orquestan operaciones entre entidades. Relaciones coherentes en el dominio.

### 3. Uso de Enums — 3/5
Enums presentes: `AccountStatus`, `LoanStatus`, `TransferStatus`, `UserRole`. Faltan: `AccountType`, `LoanType`, `Currency`, `UserStatus`, `ProductCategory`. `BankAccount` no tiene campo de tipo de cuenta ni moneda como enum.

### 4. Manejo de estados — 3/5
Los estados de cuenta, préstamo y transferencia usan enums ✓. Falta `UserStatus` y `AccountType`.

### 5. Tipos de datos — 4/5
`BankAccount` usa `BigDecimal balance` ✓ y `LocalDate openingDate` ✓. `OperationLog` usa `LocalDateTime timestamp` ✓. Detalles menores: los getters/setters son manuales (no usa Lombok), pero es válido.

### 6. Separación Usuario vs Cliente — 4/5
`Client` es abstracta con `NaturalPerson` y `Company` como subtipos ✓. `User` es una entidad separada con referencia `Client associatedClient` ✓. Separación correcta. Leve debilidad: `Client` no tiene campos suficientes de identificación.

### 7. Bitácora — 4/5
`OperationLog` tiene `Map<String, Object> details` ✓, `LocalDateTime timestamp` ✓, `int userId` y métodos de acceso. Sin embargo, `operationType` es `String` en lugar de enum, y `OperationLog` está ubicada en `infrastructure.logging` en lugar del dominio.

### 8. Reglas básicas de negocio — 3/5
Existe `LoanService` y `TransferService` en la capa de aplicación con cierta lógica de negocio. Las entidades de dominio no tienen métodos de negocio propios.

### 9. Estructura del proyecto — 3/5
Existe separación de capas: `domain/model`, `domain/enums`, `domain/repository`, `application/service`, `infrastructure/logging`. Sin embargo, `OperationLog` no debería estar en `infrastructure`. La estructura es parcialmente correcta.

### 10. Repositorio — 1/5
- **Nombre:** `ConstruccionDeSW2CarolRivera6pm` — correcto.
- **README:** **No existe archivo README.md** en el repositorio.
- **Commits:** Solo 1 commit con código. Sin formato ADD/CHG.
- **Ramas:** Tiene `develop` ✓.
- **Tag:** No hay tag.

---

## Fortalezas
- Modelo de dominio claro y bien estructurado.
- Jerarquía correcta: `Client` abstracta → `NaturalPerson` / `Company`.
- Separación User/Client con relación explícita.
- `OperationLog` con `Map<String, Object>`.
- Uso de `BigDecimal` y `LocalDate`.

## Oportunidades de mejora
- Agregar enums faltantes: `AccountType`, `LoanType`, `Currency`, `UserStatus`, `ProductCategory`.
- Mover `OperationLog` al dominio (`domain/model`), no a `infrastructure`.
- Agregar `BankingProduct` como entidad de catálogo.
- Crear el README con información de la materia, integrantes, tecnología y cómo ejecutar.
- Hacer más commits con formato ADD/CHG y agregar tag de entrega.
- Agregar identificador al `Client` (número de documento, ID).
