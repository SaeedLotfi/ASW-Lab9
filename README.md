# Lab 9 – JWT Security for ADS Dental Surgery API

This repo upgrades your Lab 7 API with **token-based authentication** (JWT) and **role-based authorization** using Spring Security 6 (Boot 3.x).

## What's included
- `/api/v1/auth/register` – create a user (defaults to role `ROLE_RECEPTION` unless `role` provided as `MANAGER`/`RECEPTION`).
- `/api/v1/auth/login` – obtain a JWT for existing users.
- A `JwtAuthenticationFilter` validates the `Authorization: Bearer <token>` header.
- **Role rules** (see `SecurityConfig`):
  - `GET /api/v1/**` → `ROLE_RECEPTION` or `ROLE_MANAGER`
  - `POST /api/v1/**` → `ROLE_RECEPTION` or `ROLE_MANAGER`
  - `PUT /api/v1/**`, `DELETE /api/v1/**` → `ROLE_MANAGER` only
  - `/api/v1/auth/**` and `/h2-console/**` → open

Two default users are seeded (see `SecuritySeeder`):
- **manager / password** → `ROLE_MANAGER`
- **reception / password** → `ROLE_RECEPTION`

## Run
```bash
./mvnw spring-boot:run
```

H2 console at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:adsdb`).

## How to test quickly
1. **Login** to get a token:
   ```bash
   curl -X POST http://localhost:8080/api/v1/auth/login      -H "Content-Type: application/json"      -d '{"username":"manager","password":"password"}'
   ```
   Response:
   ```json
   { "token": "eyJhbGciOi..." }
   ```

2. **Call a protected endpoint** with the token:
   ```bash
   TOKEN=eyJhbGciOi...   # paste from step 1
   curl http://localhost:8080/api/v1/patients -H "Authorization: Bearer $TOKEN"
   ```

3. **Try a forbidden action** with `reception` (e.g., `DELETE`):
   ```bash
   # login as reception, then:
   curl -X DELETE http://localhost:8080/api/v1/patients/1 -H "Authorization: Bearer $TOKEN"
   # HTTP 403
   ```

## Notes
- Update `security.jwt.secret` in `application.yml` for your project.
- If your existing controllers use create/update/delete, the HTTP method–based access rules will guard them. For finer-grained control, add `@PreAuthorize` on methods (requires `@EnableMethodSecurity`).

---
**Submission**: push this project to GitHub and submit the repo URL.
