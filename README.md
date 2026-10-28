# RetailMax CRM

A simple full-stack CRM for a retail sales team. Track contacts and leads, move them through a sales pipeline, get automatic follow-up flags, and see basic sales stats.

Built as a basic-to-mid level project: one user role, one database, no external integrations.

<!-- Add 1-2 screenshots here, e.g. ![Contacts page](docs/contacts.png) and ![Reports page](docs/reports.png) -->

## Features

- **Contact / lead management**: create, view, edit and delete contacts (name, email, phone, company, deal value)
- **Sales pipeline**: each contact has a status: `New → Contacted → Negotiation → Closed`
- **Automation rules**
  - Changing a contact's status records a status-change timestamp
  - Contacts that need attention are flagged for follow-up
- **Search and sort** on the contacts list
- **CSV export** of contacts
- **Reporting page**: summary cards, contacts per pipeline status (chart), top companies, and revenue stats based on deal value
- **JWT authentication**: login required to use the API and UI

## Tech Stack

| Layer     | Technology                                      |
|-----------|-------------------------------------------------|
| Backend   | Java 21+, Spring Boot 4.1, Spring Data JPA, Spring Security (JWT) |
| Frontend  | Angular 21 (standalone components), TypeScript  |
| Database  | H2 for local development; Oracle Database Free is optional |
| Build     | Maven (backend), npm / Angular CLI (frontend)   |
| Testing   | JUnit 5 + Spring Boot Test, Vitest (Angular)    |

## Project Structure

```
RetailMax-CRM/
├── crm-backend/     # Spring Boot REST API
│   └── src/main/java/com/shivam/crm/
│       ├── controller/   # HTTP endpoints
│       ├── service/      # business logic and automation rules
│       ├── repository/   # database access (Spring Data JPA)
│       ├── model/        # JPA entities
│       └── config/       # security and CORS config
└── crm-frontend/    # Angular app
    └── src/app/
        ├── components/   # UI components
        └── services/     # HTTP services that call the API
```

## Prerequisites

- Java 21 or newer (JDK)
- Node.js 20.19+, 22.12+ or 24+, and npm
- Docker only if you choose to use Oracle instead of the default local H2 database

## Getting Started

The default setup needs no Docker or database installation. H2 stores data in `crm-backend/data/retailmax` and creates the tables automatically.

Before starting the backend, create your local credentials file:

```powershell
cd crm-backend
Copy-Item secrets.properties.example secrets.properties
```

Edit `secrets.properties` and replace the JWT secret with a private random value of at least 32 characters and the password with a private password. This file is ignored by Git; never commit it or share it.

### 1. Run the backend (Windows PowerShell, from `crm-backend`)

```powershell
.\mvnw.cmd spring-boot:run
```

The API runs at `http://localhost:8080`. Java 21+ is supported; the Maven wrapper downloads Maven if it is not installed.

### 2. Run the frontend (in a second PowerShell window)

```powershell
cd crm-frontend
npm install
npm start
```

Open `http://localhost:4200`.

### 3. Log in

The local-development username is **shivam**. Use the private password you set in `crm-backend/secrets.properties`. You can override the credentials using `CRM_DEFAULT_USERNAME`, `CRM_AUTH_DEFAULT_PASSWORD`, and `CRM_JWT_SECRET` environment variables. Do not expose a local-development instance to an untrusted network.

### Optional: use Oracle instead of H2

Start an Oracle Free container, then start the backend with the `oracle` Spring profile:

```powershell
docker run -d --name oracle-free -p 1521:1521 -e ORACLE_PASSWORD=YourPassword123 gvenzl/oracle-free:slim
$env:SPRING_PROFILES_ACTIVE = "oracle"
$env:SPRING_DATASOURCE_PASSWORD = "YourPassword123"
.\mvnw.cmd spring-boot:run
```

Clear the profile in that PowerShell window to return to H2: `$env:SPRING_PROFILES_ACTIVE = ""`.

## API Overview

All endpoints except login require the header `Authorization: Bearer <token>`.

| Method | Endpoint               | Description                  |
|--------|------------------------|------------------------------|
| POST   | `/api/auth/login`      | Log in and receive a JWT     |
| GET    | `/api/contacts`        | List contacts                |
| GET    | `/api/contacts/{id}`   | Get one contact              |
| POST   | `/api/contacts`        | Create a contact             |
| PUT    | `/api/contacts/{id}`   | Update a contact             |
| DELETE | `/api/contacts/{id}`   | Delete a contact             |

<!-- Add your reports and CSV export endpoints to this table, e.g. GET /api/reports/... and GET /api/contacts/export -->

## Running Tests

```bash
# Backend (uses the local H2 database)
cd crm-backend
.\mvnw.cmd test

# Frontend
cd crm-frontend
npm test -- --watch=false
```

## Notes and Limitations

- Single user role; no multi-tenancy.
- CORS only allows `http://localhost:4200`; update the allowed origin in `SecurityConfig.java` if you serve the frontend elsewhere.
- Built for local development; not set up for production deployment.

## Possible Future Improvements

- Pagination on the contacts list
- Email reminders for follow-ups
- More report types (conversion rate, monthly trends)