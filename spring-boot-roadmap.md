# Spring Boot Project Architecture and Coding Standards

This document defines the coding standards and architectural structure of the project's backend. Artificial intelligence (LLM) development must adhere to these rules for all development of this project.

## 1. Architecture (Layered Architecture)
The project consists of the following layers based on the principle of separation of responsibilities:
- **Controller Layer:** Only handles requests and redirects them to the Service layer. It does not contain logic.
- **Service Layer:** This is the main layer containing business logic.
- **Repository Layer:** Executes database access operations (Spring Data JPA).
- **DTO (Data Transfer Object) Layer:** These are data models that are exposed to the outside world via API endpoints. Entities are never used directly in the Controller. Includes separate `RequestDTO` and `ResponseDTO` classes.
- **Entity Layer:** These are classes representing database tables.
- **Exception Layer:** Contains `@RestControllerAdvice` for global error handling.

## 2. Technology Stack
- **Language:** Java 17+
- **Framework:** Spring Boot 3.x
- **Database:** PostgreSQL (JPA/Hibernate 6.x)
- **Security:** Spring Security + JWT
- **Documentation:** Springdoc OpenAPI (Swagger)
- **Testing:** JUnit 5, Mockito
- **Tools:** Lombok, MapStruct (Mapping), Validation (Bean Validation)

## 3. Coding Rules & Best Practices
- **Naming Conventions:** Class names should be `PascalCase`, method and variable names should be `camelCase`. Database table names should be `snake_case`.
- **Service Injection:** **Constructor Injection** (via `@RequiredArgsConstructor` from Lombok) must be used instead of `@Autowired`.
- **Lombok:** To reduce boilerplate code, use `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, and `@Builder`. Avoid using `@Data` on Entities to prevent `hashCode()`/`equals()` infinite loop issues with relationships.
- **Validation:** Input data must be validated with `@Valid` in controllers and annotations from `jakarta.validation.constraints.*` in DTOs.
- **Return Types:** Controller methods must always return `ResponseEntity<T>`.
- **Pagination & Sorting:** For list operations (e.g., getting posts or comments), ALWAYS use `Pageable` and return `Page<ResponseDTO>` instead of `List`. Never fetch all records at once.
- **Base Entity & Auditing:** Use a `@MappedSuperclass` named `BaseEntity` containing `@CreatedDate`, `@LastModifiedDate`, and `isDeleted` fields. All domain entities should extend this.
- **Soft Deletion:** Implement Soft Delete mechanism using Hibernate's `@SQLDelete(sql = "UPDATE table_name SET is_deleted = true WHERE id=?")` and `@SQLRestriction("is_deleted=false")` (for Spring Boot 3 / Hibernate 6). Do not permanently delete posts or comments.
- **Exception Handling:** Use Spring Boot 3's `ProblemDetail` (RFC 7807) structure for returning standard API errors in the `@RestControllerAdvice`.
- **Avoid N+1 Problem:** Always use `JOIN FETCH` or `@EntityGraph` for relational data fetching to prevent `N+1` select issues. Performance profiling of SQL logs is mandatory during development.

## 4. Prompt Instructions for Artificial Intelligence
"When adding a new feature:
1. Create the Entity extending `BaseEntity` with Soft Delete annotations.
2. Prepare the Spring Data JPA Repository.
3. Create the necessary Request and Response DTOs.
4. Create the MapStruct mapper interface.
5. Write the business logic in the Service layer (Implement `Pageable` for lists).
6. Write Unit Tests (JUnit 5 + Mockito) for the Service layer.
7. Finally, create the REST Controller with proper OpenAPI `@Operation` annotations.
   Always use DTOs for data transfer between layers and never return Entities from Controllers."

---
*Project Aim: A social platform where users share, like, and comment on posts.*