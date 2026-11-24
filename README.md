# Banking Management System

A RESTful Banking Management System developed using Java and Spring Boot. This project simulates core banking operations, including customer management, account management, money transfers, transaction history, and currency conversion.

## Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- REST API
- BigDecimal for precise monetary calculations

## Features

### Customer Management
- Register new customers
- Retrieve customer information
- Manage customer accounts

### Account Management
- Create and manage bank accounts
- Support multiple accounts per customer
- Current and savings account types
- Freeze and close accounts
- Check account balances

### Banking Operations
- Deposit money
- Withdraw money
- Transfer funds between accounts
- Validate sufficient account balance
- Prevent invalid transactions

### Transaction Management
- Store transaction history
- Track transaction types, amounts, dates, and account balances
- Generate account statements for specific date ranges
- Preserve transaction records

### Currency Conversion
- Support multiple currencies
- Store exchange rates in the database
- Convert money between different currencies
- Update account balances after conversion

### Transaction Monitoring
- Detect potentially suspicious transactions
- Identify unusually large transactions
- Monitor frequent transfers
- Track high daily transaction volumes

## Database

PostgreSQL is used as the database management system.

The application uses Spring Data JPA and Hibernate for database operations and entity relationship management.

## Project Architecture

The application follows a layered architecture:

- **Controller:** Handles HTTP requests and responses.
- **Service:** Contains business logic.
- **Repository:** Handles database operations.
- **Entity:** Represents database tables.
- **DTO:** Transfers data between application layers.
- **Exception:** Handles application-specific errors.

## Running the Application

1. Clone the repository:

   ```bash
   git clone https://github.com/Asiman13/Bankingmanagement.git
   ```

2. Open the project in IntelliJ IDEA.

3. Configure your PostgreSQL database and environment variables.

4. Run the application using Maven or IntelliJ IDEA.

## Future Improvements

- Automated savings interest calculation
- Spring Security and JWT authentication
- Advanced transaction monitoring
- Comprehensive unit and integration testing

## Author

**Asiman**

Java Backend Developer | Computer Engineering Student

GitHub: [Asiman13](https://github.com/Asiman13)git add 