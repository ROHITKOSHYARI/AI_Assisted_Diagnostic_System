# Security & JWT Architecture

Version: 1.0

---

# Table of Contents

1. Security Philosophy
2. Authentication Strategy
3. Authorization Strategy
4. JWT Authentication
5. Password Encryption
6. Spring Security Architecture
7. JWT Request Flow
8. Protected Resources
9. Public Endpoints
10. Security Best Practices
11. API Key Management
12. Environment Variables
13. Future Improvements

---

# 1. Security Philosophy

Security is one of the core pillars of this project.

Every request that accesses protected resources must be authenticated and authorized.

The application follows stateless authentication using JWT.

Passwords are never stored in plain text.

Sensitive information such as API keys and JWT secrets are never hardcoded.

---

# 2. Authentication Strategy

The system contains three user roles.

• Patient

• Doctor

• Admin

Patients and doctors authenticate independently.

Authentication Flow

User

↓

Login Request

↓

Spring Security

↓

Authentication Manager

↓

JWT Generated

↓

JWT Returned

↓

Frontend Stores Token

↓

Authenticated Requests

---

# 3. Authorization Strategy

Every authenticated user receives a role.

Roles

PATIENT

DOCTOR

ADMIN

Authorization is handled by Spring Security.

Example

PATIENT

Can

- Upload images
- View reports
- Update profile

Cannot

- Approve doctors

DOCTOR

Can

- Login
- View own profile
- View assigned reports (future)

Cannot

- Approve doctors

ADMIN

Can

- Approve doctors
- Reject doctors
- View all doctors

---

# 4. JWT Authentication

JWT is used because

- Stateless
- Lightweight
- Scalable
- Easy REST integration

JWT Payload Example

```json
{
    "sub":"patient@gmail.com",
    "userId":"uuid",
    "role":"PATIENT",
    "iat":123456789,
    "exp":123456999
}
```

JWT contains

- User ID
- Email
- Role
- Issue Time
- Expiration Time

Passwords are never included.

---

# 5. Password Encryption

Passwords are encrypted using BCrypt.

Workflow

Password

↓

BCrypt

↓

Hash Stored

During login

Password

↓

BCrypt Verify

↓

Authenticated

Plain text passwords are never stored or returned.

---

# 6. Spring Security Architecture

```
HTTP Request

↓

Security Filter Chain

↓

JWT Filter

↓

Authentication

↓

Authorization

↓

Controller

↓

Response
```

Every secured request passes through the JWT filter before reaching the controller.

---

# 7. JWT Request Flow

User Login

↓

Credentials Verified

↓

JWT Created

↓

JWT Returned

↓

Frontend Stores JWT

↓

Frontend Sends Authorization Header

↓

Spring Security Validates JWT

↓

Request Allowed

Authorization Header

```
Authorization: Bearer JWT_TOKEN
```

---

# 8. Protected Resources

JWT Required

Patient Profile

Doctor Profile

Prediction APIs

Report APIs

Admin APIs

Doctor Approval

JWT Not Required

Patient Registration

Patient Login

Doctor Registration

Doctor Login

---

# 9. Security Configuration

Spring Security should

- Disable Session Authentication
- Use Stateless Authentication
- Enable JWT Filter
- Enable CORS
- Disable CSRF for REST APIs

Authentication should always happen before authorization.

---

# 10. CORS

Frontend

```
http://localhost:5173
```

Backend

```
http://localhost:8080
```

Spring Boot should allow requests only from trusted frontend origins.

Future production URLs should replace localhost.

---

# 11. API Key Management

Sensitive values should never be committed to Git.

Examples

Gemini API Key

JWT Secret

Database Password

FastAPI URL

Store these values using environment variables.

---

# 12. Environment Variables

Example

```
JWT_SECRET=xxxxxxxx

JWT_EXPIRATION=86400000

DB_URL=...

DB_USERNAME=...

DB_PASSWORD=...

GEMINI_API_KEY=...

FASTAPI_URL=http://localhost:8000
```

The application reads these values during startup.

---

# 13. Token Expiration

JWT tokens should expire after a configured duration.

Example

24 Hours

If a token expires

↓

Return

401 Unauthorized

↓

Frontend redirects user to Login.

---

# 14. Security Best Practices

✓ Hash passwords

✓ Validate JWT

✓ Validate request data

✓ Restrict file uploads

✓ Store secrets in environment variables

✓ Use HTTPS in production

✓ Never expose internal exceptions

✓ Never expose passwords

✓ Never trust client input

---

# 15. Future Improvements

Version 2

Refresh Tokens

Redis Token Blacklist

Email Verification

Password Reset

Multi-Factor Authentication

OAuth2 Login

Account Locking

Rate Limiting

Audit Logs

---

# Security Summary

The application uses JWT-based stateless authentication with Spring Security.

Authentication, authorization, password encryption, and request validation are centralized within the backend.

This design keeps the system secure, scalable, and easy to integrate with React while maintaining a clear separation between authentication logic and business logic.