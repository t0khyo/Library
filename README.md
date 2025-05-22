# 📚 Library Management System API

A **Library Management System** built with **Spring Boot** featuring secure JWT-based authentication. This system allows librarians to manage books, patrons, and borrowing records through well-structured RESTful API endpoints.

---

## 📖 Project Overview

This API backend manages a library's collection of books and patrons, and records borrowing and return transactions. It includes secure user authentication with JWT tokens, input validation, exception handling, and transaction management.

---

## 📦 Features

✅ Manage books: add, update, delete, and retrieve book records
✅ Manage patrons: add, update, delete, and retrieve patron records
✅ Record borrowing and returning of books
✅ JWT-based authentication system (login/signup)
✅ Input validation and exception handling
✅ Declarative transaction management
✅ Unit and integration testing

---

## 🛠️ Tech Stack

* **Java 21**
* **Spring Boot 3**
* **Spring Data JPA**
* **PostgreSQL** (or H2 for testing)
* **Spring Security with JWT**
* **JUnit 5** / **Mockito**

---

## 📑 Entities

* **Book**: ID, title, author, publication year, ISBN
* **Patron**: ID, name, contact details
* **BorrowingRecord**: Tracks borrowing and return dates for books by patrons
* **User**: Username, password, roles (for authentication)

---

## 🔐 Authentication

Implemented **JWT-based authentication** to secure API endpoints:

### Endpoints:

* `POST /auth/login` — User login, returns JWT token
* `POST /auth/signup` — User signup, returns created user info

**Note**: JWT tokens must be included in the `Authorization` header when accessing secured endpoints.

---

📊 Centralized Logging (AOP)

The system uses Aspect-Oriented Programming (AOP) with @Aspect and @Around advices to log method calls for both services and controllers.
📖 What gets logged:

    Class name and method name

    Incoming method arguments

    Returned result values

📂 AOP Pointcuts:

    com.t0khyo.library.service.impl.* — logs service method calls

    com.t0khyo.library.controller.* — logs controller method calls

Sample log format:

>> BookServiceImpl.getAll() - []
<< BookServiceImpl.getAll() - [BookResponse(id=1, title="1984", author="George Orwell"), ...]

---

## 🌐 API Endpoints

### 📚 Book Management (`/api/books`)

| Method   | Endpoint          | Description                        |
| :------- | :---------------- | :--------------------------------- |
| `GET`    | `/api/books`      | Retrieve all books                 |
| `GET`    | `/api/books/page` | Retrieve paginated books (10/page) |
| `GET`    | `/api/books/{id}` | Retrieve a book by its ID          |
| `POST`   | `/api/books`      | Add a new book                     |
| `PUT`    | `/api/books/{id}` | Update book information            |
| `DELETE` | `/api/books/{id}` | Delete a book                      |

---

### 👤 Patron Management (`/api/patrons`)

| Method   | Endpoint            | Description             |
| :------- | :------------------ | :---------------------- |
| `GET`    | `/api/patrons`      | Retrieve all patrons    |
| `GET`    | `/api/patrons/{id}` | Retrieve a patron by ID |
| `POST`   | `/api/patrons`      | Add a new patron        |
| `PUT`    | `/api/patrons/{id}` | Update patron details   |
| `DELETE` | `/api/patrons/{id}` | Delete a patron         |

---

### 🔄 Borrowing Operations (`/api`)

| Method | Endpoint                                 | Description            |
| :----- | :--------------------------------------- | :--------------------- |
| `POST` | `/api/borrow/{bookId}/patron/{patronId}` | Borrow a book          |
| `PUT`  | `/api/return/{bookId}/patron/{patronId}` | Return a borrowed book |

---

## ✅ Validation & Error Handling

* Field-level validation on API requests (required fields, format checks)
* Global exception handling with custom error messages and appropriate HTTP status codes

---

## 📊 Transaction Management

Operations like borrowing and returning books are executed within **@Transactional** methods to guarantee data consistency.

---

## 🧪 Testing

* Unit tests for services and controllers using **JUnit 5** and **Mockito**
* Integration tests using **@SpringBootTest**

---

## 🚀 Getting Started

### 📥 Clone the repository

```bash
git clone https://github.com/t0khyo/library.git
cd library
```

### ⚙️ Configure the application

* Update `application.properties` with your PostgreSQL credentials or use H2 in-memory database for local testing.

### ▶️ Run the application

```bash
./mvnw spring-boot:run
```

### 🔑 Access API endpoints

Use Postman or any REST client with your JWT token in the `Authorization` header as:

```
Authorization: Bearer <your-jwt-token>
```

