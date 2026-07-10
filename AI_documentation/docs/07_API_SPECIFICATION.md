# REST API Specification

Version: 1.0

---

# Table of Contents

1. API Design Principles
2. Base URL
3. Authentication
4. Patient APIs
5. Doctor APIs
6. Admin APIs
7. Prediction APIs
8. Report APIs
9. HTTP Status Codes
10. Standard Response Format
11. Error Response Format
12. Validation Rules
13. Future APIs

---

# 1. API Design Principles

The backend follows RESTful API design principles.

Rules

- Resource-based URLs
- JSON request and response bodies
- Proper HTTP methods
- Proper HTTP status codes
- JWT authentication
- Stateless communication

---

# 2. Base URL

Development

```
http://localhost:8080/api
```

Future Production

```
https://api.medicalai.com/api
```

---

# 3. Authentication APIs

## Patient Registration

POST

```
/patients/register
```

Purpose

Register a new patient.

Request

```json
{
    "firstName":"Rohit",
    "lastName":"Koshyari",
    "email":"rohit@gmail.com",
    "password":"Password123",
    "phoneNumber":"9876543210",
    "gender":"MALE",
    "dateOfBirth":"2006-05-12"
}
```

Response

```json
{
    "success":true,
    "message":"Patient registered successfully."
}
```

---

## Patient Login

POST

```
/patients/login
```

Request

```json
{
    "email":"rohit@gmail.com",
    "password":"Password123"
}
```

Response

```json
{
    "token":"JWT_TOKEN"
}
```

---

## Doctor Registration

POST

```
/doctors/register
```

Request

```json
{
    "firstName":"Amit",
    "lastName":"Sharma",
    "email":"doctor@gmail.com",
    "password":"Password123",
    "qualification":"MBBS",
    "specialization":"Neurologist",
    "experience":8,
    "hospital":"AIIMS",
    "licenseNumber":"MED123456"
}
```

Response

```json
{
    "success":true,
    "message":"Doctor registered successfully. Waiting for admin approval."
}
```

---

## Doctor Login

POST

```
/doctors/login
```

Response

```json
{
    "token":"JWT_TOKEN"
}
```

Doctor login should fail if the doctor is not approved.

---

# 4. Patient APIs

## Get Profile

GET

```
/patients/profile
```

Authentication

Required

---

## Update Profile

PUT

```
/patients/profile
```

Authentication

Required

---

## Delete Profile

DELETE

```
/patients/profile
```

Authentication

Required

---

# 5. Doctor APIs

## Get Profile

GET

```
/doctors/profile
```

---

## Update Profile

PUT

```
/doctors/profile
```

---

## Upload Profile Picture (Future)

POST

```
/doctors/profile-picture
```

---

# 6. Admin APIs

## Get Doctors

GET

```
/admin/doctors
```

Returns

- Pending doctors
- Approved doctors
- Rejected doctors

---

## Approve Doctor

PUT

```
/admin/doctors/{doctorId}/approve
```

---

## Reject Doctor

PUT

```
/admin/doctors/{doctorId}/reject
```

Request

```json
{
    "reason":"License verification failed."
}
```

---

# 7. Prediction APIs

## Predict Disease

POST

```
/predict
```

Consumes

```
multipart/form-data
```

Parameters

Image

Symptoms

Example

```
image = MRI.jpg

symptoms =
Headache
Vomiting
Blurred Vision
```

Response

```json
{
    "disease":"Brain Tumor",
    "confidence":97.8,
    "modelName":"BrainTumorCNN_v1",
    "gradCamImageUrl":"..."
}
```

---

# 8. Report APIs

## Get All Reports

GET

```
/reports
```

Returns

Patient prediction history.

---

## Get Report

GET

```
/reports/{reportId}
```

---

## Download PDF

GET

```
/reports/{reportId}/download
```

Returns

PDF file.

---

# 9. HTTP Status Codes

200

Success

201

Resource Created

400

Bad Request

401

Unauthorized

403

Forbidden

404

Resource Not Found

409

Conflict

500

Internal Server Error

---

# 10. Standard Response Format

Success

```json
{
    "success":true,
    "message":"Operation successful.",
    "data":{}
}
```

Failure

```json
{
    "success":false,
    "message":"Operation failed."
}
```

---

# 11. Error Response

```json
{
    "timestamp":"2026-07-10T14:22:10",
    "status":404,
    "error":"Not Found",
    "message":"Report not found."
}
```

---

# 12. Validation Rules

Patient

- Email must be valid.
- Password minimum 8 characters.
- Phone number required.

Doctor

- License number required.
- Qualification required.
- Specialization required.

Prediction

- Image is mandatory.
- Only JPG, JPEG, PNG allowed.
- Maximum upload size configured in Spring Boot.

---

# 13. Security

JWT Required

Protected Endpoints

- Patient Profile
- Doctor Profile
- Prediction
- Reports
- Admin

Public Endpoints

- Login
- Registration

---

# 14. API Documentation

Swagger/OpenAPI should be enabled during development.

Purpose

- Test APIs
- Share API documentation
- Simplify frontend development

---

# 15. Future APIs

Appointment APIs

Notification APIs

Chat APIs

Analytics APIs

Admin Dashboard APIs

Redis Cache APIs

Kafka Event APIs

---

# API Summary

The REST API serves as the communication layer between the frontend and backend.

It follows REST principles, uses JWT authentication, returns standardized JSON responses, and exposes endpoints for authentication, prediction, reporting, and administration while remaining independent of the AI inference service.