# Tiny-Ecommerce

## Introduction
This is a backend service designed for USC CSCI 551 final project, Tiny-Ecommerce, aimed at providing backend distributed CRUD functionality.

## Technology Stack
The main technologies, frameworks, and libraries used to build the project.
- Spring Boot
- MySQL

## Quick Start
A series of steps on how to run the project locally.
1. **Clone the repository:**
   ```bash
   git clone https://github.com/nomosofa/tiny-ecommerce.git
   ```
2. **Configure MySQL databases:**
    - Ensure that MySQL is installed on your machine.
    - Create two databases, `ecommerce_db1` and `ecommerce_db2`.
    - Update the `application.yml` with the correct database URLs, usernames, and passwords:
        
        ```yaml
        spring:
          datasource:
            db1:
              url: jdbc:mysql://localhost:3306/ecommerce_db1
              username: yourUsername  # Replace 'yourUsername' with your MySQL username
              password: yourPassword  # Replace 'yourPassword' with your MySQL password
            db2:
              url: jdbc:mysql://localhost:3306/ecommerce_db2
              username: yourUsername  # Replace 'yourUsername' with your MySQL username
              password: yourPassword  # Replace 'yourPassword' with your MySQL password
        
        ```
        
    - Ensure that your MySQL user has the necessary permissions to create, delete, and manage databases.
3. **Build the project:**
    
    ```
    ./mvnw clean install
    ```
    
4. **Run the application:**
    
    ```
    ./mvnw spring-boot:run
    ```
    
    Alternatively, you can run the application directly from your IDE by executing the main class 
    `ECommerceApplication.java`.

## API Documentation
This project uses SwaggerUI to document its APIs. Once the project is running, you can access the API documentation at:
http://localhost:8080/swagger-ui/index.html#/
