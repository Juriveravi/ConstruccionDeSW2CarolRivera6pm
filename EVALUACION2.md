# EVALUACION 2 - ConstruccionDeSW2CarolRivera6pm

## Informacion general
- Estudiante(s): Integrantes no informados en README.md (usuario GitHub: Juriveravi, nombre inferido: Carol Rivera)
- Rama evaluada: develop
- Commit evaluado: ee6e6e1cb886b1ad6e564dc641e4c37a0680e2d5
- Fecha: 2026-04-11
- Nota: No existe `README.md` en la rama `develop`.

---

## Tabla de calificacion

| # | Criterio | Peso | Puntaje (1-5) | Parcial |
|---|---|---|---|---|
| 1 | Modelado de dominio | 20% | 3 | 0.60 |
| 2 | Modelado de puertos | 20% | 2 | 0.40 |
| 3 | Modelado de servicios de dominio | 20% | 2 | 0.40 |
| 4 | Enums y estados | 10% | 3 | 0.30 |
| 5 | Reglas de negocio criticas | 10% | 2 | 0.20 |
| 6 | Bitacora y trazabilidad | 5% | 1 | 0.05 |
| 7 | Estructura interna de dominio | 10% | 2 | 0.20 |
| 8 | Calidad tecnica base en domain | 5% | 3 | 0.15 |
| | **Total base** | | | **2.30** |

### Calculo
Nota base = (3*20 + 2*20 + 2*20 + 3*10 + 2*10 + 1*5 + 2*10 + 3*5) / 100 = 230 / 100 = **2.30**

---

## Penalizaciones aplicadas

| Penalizacion | Porcentaje | Motivo |
|---|---|---|
| Logica de negocio critica fuera de domain | -20% | `LoanService` y `TransferService` estan en `application/service/`, no en `domain/services/` |

Nota tras penalizacion: 2.30 × 0.80 = **1.84**

---

## Nota final
**1.8 / 5.0**

---

## Hallazgos

### Criterio 1 - Modelado de dominio (3/5)
- Entidades en `domain/model/`: `BankAccount`, `Client`, `Company`, `Loan`, `NaturalPerson`, `Transfer`, `User`.
- Jerarquia de cliente: `Client` (base), `Company`, `NaturalPerson`.
- Falta: `ProductoBancario`, `AuditLog`/`OperationLog` en el dominio (la bitacora esta en infraestructura).
- `OperationLog` se encuentra en `infraestructure/logging/` — no es una entidad de dominio en este proyecto.
- Falta: catalogo de productos bancarios con tipos y categorias.

### Criterio 2 - Modelado de puertos (2/5)
- Existen `BankAccountRepository`, `LoanRepository`, `TransferRepository` en `domain/repository/`.
- Estan en el paquete `domain/` lo cual es positivo.
- **Problema:** Estan nombrados como `Repository` (tecnologico) y no como `Port` (semantico de dominio).
- Falta: `ClientPort`, `UserPort`, `BitacoraPort`, `ProductoBancarioPort`.
- Los metodos de los repositorios no se pudieron verificar en detalle, pero la estructura sugiere metodos CRUD tecnologicos.

### Criterio 3 - Servicios de dominio (2/5)
- `LoanService` y `TransferService` existen en `application/service/`.
- **Problema:** Estan en la capa `application`, no en `domain/services/`.
- El dominio no tiene propios servicios de caso de uso.
- Puede haber logica de negocio en `application/service/` pero no cuenta para la evaluacion de domain.

### Criterio 4 - Enums y estados (3/5)
- Enums en `domain/enums/`: `AccountStatus`, `LoanStatus`, `TransferStatus`, `UserRole`.
- Falta: `AccountType`, `UserStatus`, `Currency`, `LoanType`, `ProductCategory`.
- 4 de 9 enums esperados presentes.

### Criterio 5 - Reglas de negocio criticas (2/5)
- Los servicios en `application/` pueden tener reglas, pero al estar fuera de domain no se evaluan aqui.
- En el dominio mismo no hay servicios con reglas de negocio.
- Penalizacion aplicada por tener la logica fuera de domain.

### Criterio 6 - Bitacora y trazabilidad (1/5)
- `OperationLog` se encuentra en `infraestructure/logging/` — totalmente fuera del dominio.
- No hay entidad de bitacora ni puerto de bitacora en el dominio.

### Criterio 7 - Estructura interna de dominio (2/5)
- `domain/enums/`, `domain/model/`, `domain/repository/` presentes.
- Falta: `domain/services/` (servicios estan en application).
- `OperationLog` fuera del dominio en infraestructura.

### Criterio 8 - Calidad tecnica (3/5)
- Nomenclatura en ingles consistente.
- Estructura parcialmente coherente.
- La separacion entre `domain/repository/` y `application/service/` muestra intencion arquitectonica aunque mal ubicada.

---

## Recomendaciones
1. Mover `LoanService` y `TransferService` a `domain/services/` o crear servicios de dominio dedicados.
2. Mover `OperationLog` al dominio y crear `BitacoraPort` con metodo `append`.
3. Renombrar `domain/repository/` a `domain/ports/` y las interfaces a `*Port`.
4. Agregar enums faltantes: `AccountType`, `UserStatus`, `Currency`, `LoanType`.
5. Crear `ProductoBancario` como entidad de dominio con catalogo de productos.
6. Incluir integrantes en el `README.md`.
