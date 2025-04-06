# h-o-m-e--api

The API for the H.O.M.E. application.

This is an API implemented in Java using Spring Boot. It provides both RESTful and WebSocket (STOMP) endpoints and communicates with a MySQL database.

## Features

- REST API for home and task management
- WebSocket (STOMP) for real-time communication
- MySQL database integration using Spring Data JPA

## Technologies Used

- Java 21
- Spring Boot 3+
- Spring Web (REST API)
- Spring WebSocket (STOMP)
- JDBC (java.sql)
- MySQL

## Setup Instructions

### Prerequisites

- Java 21+
- MySQL Server
- Maven

### Configuration

1. Clone the repository:

   ```bash
   git clone git@github.com:Elias-Chairi/h-o-m-e--api.git
   cd h-o-m-e--api
   ```

2. Create the MySQL Server

   Note that the API uses MySQL triggers to manage data integrity. To ensure proper trigger functionality, the following are required:

   1. **MySQL Variable**: Set the `log_bin_trust_function_creators` variable to `1`:

   ```mysql
   SET GLOBAL log_bin_trust_function_creators = 1;
   ```

   2. **User Permissions**: The MySQL user must have the `CREATE TRIGGER` permission along with the basic: `CREATE`, `INSERT`, `SELECT`, `UPDATE`, `DELETE` and `REFERENCES`.

3. Set the environment variable `AZURE_MYSQL_CONNECTIONSTRING`:

   ```bash
   export AZURE_MYSQL_CONNECTIONSTRING='jdbc:mysql://localhost:3306/DATABASE?serverTimezone=UTC&sslmode=required&user=USERNAME&password=PASSWORD'
   ```

4. Build and run the application:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## API Endpoints

### REST Endpoints

- `POST /api/homes` - Create home
- `GET /api/homes/{homeId}` - Get home
- `POST /api/homes/{homeId}/residents` - Create a resident in home
- `POST /api/residents/{residentId}/tasks` - Create a task with resident as creator

### WebSocket Endpoints

- Connection endpoint: `ws://localhost:8080/api/websocket`
- Subscribe to home users: `/topic/home-users/{homeID}`
- Subscribe to home tasks: `/topic/home-tasks/{homeID}`

## Authors

Elias Pettersen Chairi - [GitHub](https://github.com/Elias-Chairi)

---
