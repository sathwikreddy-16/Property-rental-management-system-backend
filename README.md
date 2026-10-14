# Property Rental Management API

Spring Boot REST API for the Haven rental application.

## Requirements

- Java 17 or later
- Maven 3.9 or later
- MySQL 8 or later

## Configure MySQL

The default database URL uses `property-rental-db`. Override `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` as needed. Hibernate creates and updates the application tables at startup. The optional `database/schema.sql` file creates the same database and tables.

In Windows Command Prompt:

```cmd
set "DB_URL=jdbc:mysql://localhost:3306/property-rental-db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
set "DB_USERNAME=root"
set "DB_PASSWORD=your_mysql_password"
set "ADMIN_SIGNUP_CODE=choose-an-admin-code"
mvn spring-boot:run
```

Tenant and landlord accounts can register directly. Administrator registration requires the configured signup code. Set `FRONTEND_URL` to the deployed frontend origin when it is not running at localhost or a Vercel subdomain.

## Main endpoints

- `POST /api/auth/signup` and `POST /api/auth/login`
- `POST /api/auth/logout`
- `GET /api/messages/conversations` and `POST /api/messages`
- `GET /api/properties` and role-protected property management endpoints
- `GET /api/applications`, `POST /api/applications`, and role-protected status updates

Sign-in tokens are kept in memory and are invalidated when the backend restarts. The payment route remains a demo endpoint.
