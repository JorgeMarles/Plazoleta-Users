```markdown
# Plazoleta-Users Development Patterns

> Auto-generated skill from repository analysis

## Overview

This skill teaches you the core development patterns, coding conventions, and workflows used in the **Plazoleta-Users** Java codebase. The repository is structured around clean architecture principles, separating domain, application, and infrastructure layers. It uses conventional commit messages and enforces consistent file organization, naming, and code style. This guide will help you contribute new features, persistence logic, controllers, and tests effectively.

## Coding Conventions

- **File Naming:**  
  Use **PascalCase** for all Java files.  
  _Example:_ `UserModel.java`, `UserController.java`

- **Import Style:**  
  Use **relative imports** within the package structure.  
  _Example:_  
  ```java
  import com.jamarlesf.plazoletausers.domain.model.UserModel;
  ```

- **Export Style:**  
  Use **named exports** (standard for Java classes).  
  _Example:_  
  ```java
  public class UserModel {
      // ...
  }
  ```

- **Commit Messages:**  
  Use **conventional commits** with prefixes like `feat`, `fix`, `chore`.  
  _Example:_  
  ```
  feat: add user registration use case
  fix: correct user entity mapping
  chore: update dependencies
  ```

## Workflows

### Add New Domain Feature
**Trigger:** When you want to introduce a new business concept or capability in the domain layer.  
**Command:** `/add-domain-feature`

1. **Create or update domain models** in `domain/model/`.
   - _Example:_  
     ```java
     public class UserModel { ... }
     ```
2. **Add or update exceptions** in `domain/exception/`.
   - _Example:_  
     ```java
     public class UserNotFoundException extends RuntimeException { ... }
     ```
3. **Define service and/or persistence ports** in `domain/api/` and `domain/spi/`.
   - _Example:_  
     ```java
     public interface UserServicePort { ... }
     ```
4. **Implement use case logic** in `domain/usecase/`.
   - _Example:_  
     ```java
     public class RegisterUserUseCase implements UserServicePort { ... }
     ```

### Add Application Layer Support
**Trigger:** When you want to expose new or updated domain features to the application layer for use by controllers or external interfaces.  
**Command:** `/add-application-support`

1. **Create or update DTOs** in `application/dto/request/` and `application/dto/response/`.
   - _Example:_  
     ```java
     public class UserRequestDto { ... }
     public class UserResponseDto { ... }
     ```
2. **Create or update mappers** in `application/mapper/`.
   - _Example:_  
     ```java
     public class UserMapper { ... }
     ```
3. **Add or update handlers** in `application/handler/` and `application/handler/impl/`.
   - _Example:_  
     ```java
     public interface UserHandler { ... }
     public class UserHandlerImpl implements UserHandler { ... }
     ```

### Add Infrastructure Persistence Layer
**Trigger:** When you want to persist new or updated domain models in the database.  
**Command:** `/add-persistence-layer`

1. **Create or update JPA entities** in `infrastructure/out/jpa/entity/`.
   - _Example:_  
     ```java
     @Entity
     public class UserEntity { ... }
     ```
2. **Create or update repositories** in `infrastructure/out/jpa/repository/`.
   - _Example:_  
     ```java
     public interface UserRepository extends JpaRepository<UserEntity, Long> { ... }
     ```
3. **Create or update entity mappers** in `infrastructure/out/jpa/mapper/`.
   - _Example:_  
     ```java
     public class UserEntityMapper { ... }
     ```
4. **Implement or update adapters** in `infrastructure/out/jpa/adapter/`.
   - _Example:_  
     ```java
     public class UserJpaAdapter implements UserPersistencePort { ... }
     ```

### Add or Update Controller and Exception Handling
**Trigger:** When you want to expose new endpoints or improve error handling at the API layer.  
**Command:** `/add-controller`

1. **Create or update REST controllers** in `infrastructure/input/rest/`.
   - _Example:_  
     ```java
     @RestController
     public class UserController { ... }
     ```
2. **Create or update ControllerAdvisor** in `infrastructure/exceptionhandler/`.
   - _Example:_  
     ```java
     @ControllerAdvice
     public class GlobalExceptionHandler { ... }
     ```

### Add or Update Tests
**Trigger:** When you add new logic or fix bugs in the domain or application layers.  
**Command:** `/add-tests`

1. **Create or update test files** in `src/test/java/com/jamarlesf/plazoletausers/domain/`.
   - _Example:_  
     ```java
     public class RegisterUserUseCaseTest { ... }
     ```
2. **Optionally update `build.gradle`** for test configuration.

## Testing Patterns

- **Test File Location:**  
  Place tests in `src/test/java/com/jamarlesf/plazoletausers/domain/`.
- **Naming:**  
  Use PascalCase for test class names, typically ending with `Test`.  
  _Example:_ `UserServiceTest.java`
- **Framework:**  
  The specific testing framework is not detected, but standard Java testing (JUnit) is likely.

## Commands

| Command                | Purpose                                                           |
|------------------------|-------------------------------------------------------------------|
| /add-domain-feature    | Add a new domain model, exception, port, or use case              |
| /add-application-support | Add DTOs, mappers, or handlers to the application layer         |
| /add-persistence-layer | Implement persistence with JPA entities, repositories, adapters   |
| /add-controller        | Add or update REST controllers and exception handling             |
| /add-tests             | Add or update unit and use case tests                             |
```