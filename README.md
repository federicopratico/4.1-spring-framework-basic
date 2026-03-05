# UserApi - Spring Boot REST API

**Description**: First REST API built with Spring Boot for user management. This project introduces key concepts like REST endpoints, HTTP methods, dependency injection, IoC, and layered architecture with Repository and Service patterns.

## 📌 Exercise Statement

Introduction to Spring Boot (S4.01). Build a minimal but functional REST API that allows receiving and returning data in JSON format, using HTTP methods and applying best practices from the start. The exercise progresses through three levels:

- **Level 1**: Basic health check endpoint, JSON responses, automated testing, and JAR execution
- **Level 2**: In-memory user management with CRUD operations and filtering
- **Level 3**: Refactoring to layered architecture (Controller → Service → Repository) with unit and integration tests

## ✨ Features

- Health check endpoint for application monitoring
- User CRUD operations (Create, Read, List)
- User filtering by name (case-insensitive)
- UUID-based unique user identification
- Email uniqueness validation
- Layered architecture with clear separation of concerns
- Comprehensive test coverage (unit and integration tests)

## 🛠 Technologies

**Backend:**
- Java 21
- Spring Boot (latest stable)
- Maven
- Spring Web
- Spring Boot DevTools
- Apache Tomcat (embedded server)

**Testing:**
- JUnit 5
- MockMvc
- Mockito

**Tools:**
- IntelliJ IDEA (development and execution)
- Postman (API manual testing)
- Git & GitHub (version control)

## 🚀 Installation and Execution

### 1. Clone the repository
```bash
git clone https://github.com/federicopratico/4.1-spring-framework-basic.git
git clone git@github.com:federicopratico/4.1-spring-framework-basic.git
cd 4.1-spring-framework-basic
```

### 2. Configure application properties
Ensure `src/main/resources/application.properties` contains:
```properties
server.port=9000
```

### 3. Run the application

**Option A: Using Maven**
```bash
mvn spring-boot:run
```

**Option B: Using IntelliJ IDEA**
- Open the project in IntelliJ IDEA
- Run the main class `UserApiApplication`

**Option C: Execute JAR file**
```bash
mvn clean package
java -jar target/userapi-0.0.1-SNAPSHOT.jar
```

### 4. Test the API

**Manual testing with Postman:**
- Health check: `GET http://localhost:9000/health`
- List users: `GET http://localhost:9000/users`
- Create user: `POST http://localhost:9000/users` with JSON body
- Get user by ID: `GET http://localhost:9000/users/{id}`
- Filter by name: `GET http://localhost:9000/users?name=ada`

**Automated tests:**
```bash
mvn test
```

**Run tests with coverage in IntelliJ IDEA:**
- Right-click on test class → Run with Coverage

## 🧩 Diagrams and Technical Decisions

### Layered Architecture

```
┌─────────────────────────────────────┐
│         UserController              │  ← HTTP Layer (REST endpoints)
│   (@RestController)                 │
└──────────────┬──────────────────────┘
               │ delegates to
               ▼
┌─────────────────────────────────────┐
│         UserService                 │  ← Business Logic Layer
│   (@Service)                        │     (validations, rules)
└──────────────┬──────────────────────┘
               │ uses
               ▼
┌─────────────────────────────────────┐
│      UserRepository                 │  ← Data Access Layer
│   (@Repository)                     │     (in-memory storage)
│   InMemoryUserRepository            │
└─────────────────────────────────────┘
```

### Key Design Patterns

- **Repository Pattern**: Abstracts data access logic behind an interface
- **Dependency Injection**: IoC container manages bean lifecycle and dependencies
- **Service Layer Pattern**: Centralizes business logic separate from HTTP and data layers

### Technical Decisions

- **UUID for user IDs**: Ensures uniqueness without database auto-increment
- **In-memory storage**: Simplifies initial learning without database complexity
- **Interface-based design**: Allows future implementations (e.g., database repositories) without changing service layer
- **TDD approach**: Tests written before implementation for email validation feature
- **Separation of concerns**: Clear boundaries between HTTP handling, business logic, and data access
