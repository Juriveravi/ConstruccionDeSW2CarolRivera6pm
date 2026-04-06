# Bank System - Gestión de Información Bancaria

## Descripción del Proyecto

Este proyecto consiste en el desarrollo de una aplicación para la gestión de información de un banco, permitiendo administrar clientes, cuentas, préstamos y transferencias.

Se implementa siguiendo principios de arquitectura limpia (Clean Architecture), separando claramente las capas de dominio, aplicación e infraestructura.

---

## Objetivo

Desarrollar un sistema modular, escalable y mantenible que permita simular operaciones bancarias básicas como:

* Gestión de clientes
* Manejo de cuentas bancarias
* Registro de préstamos
* Transferencias entre cuentas
* Auditoría de operaciones

---

## Arquitectura del Proyecto

El proyecto está organizado en las siguientes capas:

### Domain (Dominio)

Contiene la lógica de negocio.

* model: Entidades (Client, Transfer, Loan, etc.)
* enums: Tipos y estados del sistema
* port: Interfaces de acceso a datos
* service: Lógica de negocio

### Application

Contiene los controladores y la orquestación de los casos de uso.

### Infrastructure

Contiene las implementaciones técnicas.

* persistence.adapter: Adaptadores que conectan el dominio con la base de datos
* persistence.jpa: Interfaces de acceso a datos con JPA

---

## Tecnologías Utilizadas

* Java
* Spring Boot
* Spring Data JPA
* Maven

---

## Ejecución del Proyecto

### Clonar repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
cd bank_system
```

### Ejecutar con Maven

```bash
./mvnw spring-boot:run
```

También se puede ejecutar desde un entorno de desarrollo como NetBeans o Visual Studio Code ejecutando la clase principal del proyecto.

---


## Estado del Proyecto

El proyecto compila correctamente y cumple con la estructura definida para la arquitectura.

---

## Integrantes

* Carol Rivera Villa


---



