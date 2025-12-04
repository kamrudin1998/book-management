# Book Management System (Spring Boot + MongoDB + JWT)

## Features

- Java 17, Spring Boot 3
- MongoDB Atlas as database
- JWT-based authentication
- Roles: ADMIN, USER
  - USER: can view/search books
  - ADMIN: can create/update/delete books
- Pagination, sorting, search

## Setup

1. Install:
   - Java 17+
   - Maven
   - A MongoDB Atlas cluster (or local MongoDB)

2. Clone or extract this project.

3. Create `.env` file in project root (same level as `pom.xml`):

   Copy from `.env.example` and fill real values:

   ```env
   MONGODB_URI=your_mongodb_atlas_connection_string
   DATABASE_NAME=bookdb

   JWT_SECRET=some_long_random_secret_string_at_least_32_chars
   JWT_EXPIRATION_MS=86400000

   ADMIN_EMAIL=admin@books.com
   ADMIN_PASSWORD=Admin@123
   ADMIN_NAME=System Admin
   ```

4. Make sure your MongoDB Atlas network access allows your IP.

5. Run the project:

   ```bash
   mvn spring-boot:run
   ```

   or

   ```bash
   mvn clean package
   java -jar target/book-management-0.0.1-SNAPSHOT.jar
   ```

## API Usage

### 1. Auth

- `POST /api/auth/register`

  ```json
  {
    "name": "Test User",
    "email": "user@example.com",
    "password": "User@123"
  }
  ```

- `POST /api/auth/login`

  ```json
  {
    "email": "user@example.com",
    "password": "User@123"
  }
  ```

  Response:

  ```json
  {
    "token": "JWT_TOKEN_HERE",
    "role": "ROLE_USER"
  }
  ```

Use the token in all protected requests:

`Authorization: Bearer JWT_TOKEN_HERE`

### 2. Books

- `GET /api/books` (USER or ADMIN)
- `GET /api/books/{id}` (USER or ADMIN)
- `POST /api/books` (ADMIN only)
- `PUT /api/books/{id}` (ADMIN only)
- `DELETE /api/books/{id}` (ADMIN only)
- `GET /api/books/search/title?title=clean`
- `GET /api/books/search/author?author=martin`

