# Spring Boot Backend Architecture

Version: 1.0

---

# Table of Contents

1. Backend Philosophy
2. Why Spring Boot?
3. Layered Architecture
4. Package Structure
5. Request Lifecycle
6. Controller Layer
7. Service Layer
8. Repository Layer
9. DTO Layer
10. Validation Layer
11. Security Layer
12. JWT Authentication
13. Exception Handling
14. Response Standardization
15. File Upload Architecture
16. FastAPI Integration
17. Gemini Integration
18. PDF Generation
19. Logging
20. Configuration
21. Future Improvements

---

# 1. Backend Philosophy

Spring Boot is the heart of the application.

It is responsible for every business rule.

The backend intentionally contains **no deep learning code**.

Machine learning belongs exclusively to the FastAPI service.

This separation allows:

- independent deployment
- easier testing
- independent scaling
- language specialization

---

# 2. Responsibilities

Spring Boot is responsible for

✓ Authentication

✓ Authorization

✓ JWT

✓ Validation

✓ Report Management

✓ Doctor Verification

✓ Patient Management

✓ Database

✓ Calling FastAPI

✓ Calling Gemini

✓ PDF Generation

Spring Boot is NOT responsible for

✗ CNN inference

✗ Image preprocessing

✗ GradCAM generation

---

# 3. Layered Architecture

```

Controller

↓

Service

↓

Repository

↓

Database

```

Each layer has one responsibility.

---

# 4. Package Structure

```
com.rohit.diagnostic_system

│

├── config

├── controller

├── dto

│     ├── patient
│     ├── doctor
│     ├── report
│     ├── auth
│     └── common

├── entity

├── enums

├── exception

├── mapper

├── repository

├── security

├── service

│      ├── impl

├── util

├── validation

└── DiagnosticSystemApplication
```

---

# Why this package structure?

Large projects become difficult to maintain when everything is placed inside one folder.

Every package should have one clear responsibility.

---

# 5. Request Lifecycle

Example

Patient uploads MRI.

```

Client

↓

Controller

↓

Service

↓

FastAPI

↓

Prediction

↓

Gemini

↓

Report

↓

Repository

↓

Database

↓

Response

```

No controller talks directly to Repository.

No repository talks to FastAPI.

---

# 6. Controllers

Controllers expose REST endpoints.

Controllers never contain business logic.

Controllers should

✓ Validate DTO

✓ Call Service

✓ Return Response

Controllers should NOT

✗ Save database

✗ Hash passwords

✗ Call Gemini

✗ Generate PDF

---

Controllers planned

PatientController

DoctorController

AuthenticationController

PredictionController

ReportController

AdminController

---

# PatientController

Responsibilities

Patient registration

Patient profile

Patient history

Patient details

Endpoints

POST /api/patients/register

POST /api/patients/login

GET /api/patients/profile

PUT /api/patients/profile

DELETE /api/patients/profile

---

# DoctorController

Responsibilities

Doctor registration

Doctor profile

Doctor information

Endpoints

POST /api/doctors/register

POST /api/doctors/login

GET /api/doctors/profile

PUT /api/doctors/profile

---

# PredictionController

Responsibilities

Image upload

Prediction

GradCAM

Gemini integration

Endpoints

POST /api/predict

POST /api/upload

GET /api/reports/{id}

---

# ReportController

Responsibilities

Prediction history

Download PDF

Get report

Endpoints

GET /api/reports

GET /api/reports/{id}

GET /api/reports/download/{id}

---

# AdminController

Responsibilities

Approve doctor

Reject doctor

List doctors

Endpoints

GET /api/admin/doctors

PUT /api/admin/approve/{id}

PUT /api/admin/reject/{id}

---

# 7. Service Layer

Business logic lives here.

Every controller delegates work to a service.

---

Services

PatientService

DoctorService

AuthenticationService

PredictionService

ReportService

GeminiService

PDFService

AdminService

FastAPIService

---

PatientService

Responsibilities

Register Patient

Update Profile

Delete Patient

Find Patient

Validate Email

---

DoctorService

Responsibilities

Register Doctor

Approve Doctor

Reject Doctor

Update Doctor

---

PredictionService

Responsibilities

Receive image

Validate image

Call FastAPI

Store prediction

Generate report

---

ReportService

Responsibilities

Create Report

View Report

History

Download PDF

---

GeminiService

Responsibilities

Prompt creation

API communication

Response parsing

---

FastAPIService

Responsibilities

HTTP communication

Upload image

Receive prediction

Receive GradCAM

---

PDFService

Responsibilities

Generate report

Generate printable PDF

Future digital signatures

---

# 8. Repository Layer

Repositories only interact with PostgreSQL.

Repositories contain no business logic.

Repositories

PatientRepository

DoctorRepository

ReportRepository

---

Repository methods

findByEmail()

existsByEmail()

findById()

save()

delete()

findAll()

---

# 9. DTO Layer

Entities should never be exposed directly.

DTOs protect

database

security

future compatibility

Patient DTOs

PatientRegistrationRequest

PatientLoginRequest

PatientResponse

Doctor DTOs

DoctorRegistrationRequest

DoctorLoginRequest

DoctorResponse

Prediction DTOs

PredictionRequest

PredictionResponse

Gemini DTOs

SymptomRequest

GeminiResponse

Report DTOs

ReportResponse

PDFResponse

---

# 10. Validation

Validation should occur before business logic.

Examples

Email

@NotBlank

@Email

Password

@NotBlank

@Size(min=8)

Phone

@Pattern

Image

Only

jpg

jpeg

png

Maximum size

10MB

---

# 11. Security

Spring Security

↓

JWT Filter

↓

Authentication

↓

Authorization

↓

Controller

---

Passwords

BCrypt

Never plaintext.

JWT expiration

Example

24 hours

Refresh token support

Future version.

---

# 12. Exception Handling

Use

@ControllerAdvice

Global exceptions

PatientNotFoundException

DoctorNotFoundException

ReportNotFoundException

PredictionFailedException

UnauthorizedException

ValidationException

ImageUploadException

---

Example response

```
{
  "timestamp": "...",
  "status":404,
  "message":"Report not found"
}
```

---

# 13. Response Format

Every API should follow one response format.

```
{
   "success": true,
   "message":"Prediction generated.",
   "data": {}
}
```

Errors

```
{
   "success":false,
   "message":"Invalid JWT."
}
```

Consistency simplifies frontend development.

---

# 14. File Upload

Frontend uploads image.

↓

Spring validates.

↓

Spring stores temporarily.

↓

FastAPI receives image.

↓

Prediction returned.

↓

Temporary file removed.

Future

Images stored in MinIO.

---

# 15. FastAPI Integration

Communication protocol

REST

Content Type

multipart/form-data

Expected Response

```
{
 "disease":"Brain Tumor",
 "confidence":97.8,
 "gradcam":"..."
}
```

---

# 16. Gemini Integration

Input

Symptoms

↓

Prompt Builder

↓

Gemini API

↓

Response Parser

↓

Database

Prompt construction should be centralized inside GeminiService.

Never construct prompts inside controllers.

---

# 17. PDF Generation

Report Entity

↓

Template Builder

↓

PDF Generator

↓

Download

PDFs should contain

Patient

Prediction

Confidence

Symptoms

Gemini Analysis

GradCAM

Disclaimer

---

# 18. Logging

Log

Authentication

Prediction

Doctor approval

Errors

Warnings

Do NOT log

Passwords

JWT tokens

Medical images

---

# 19. Configuration

Configuration package

JWT Secret

Gemini API Key

FastAPI URL

Upload directory

Swagger

CORS

Multipart configuration

Environment variables should be used for sensitive values.

---

# 20. Future Backend Improvements

Redis

Prediction cache

JWT blacklist

Gemini cache

Kafka

Background PDF generation

Notifications

Email queue

WebSocket

Doctor consultation

Live notifications

Cloud

AWS S3

Kubernetes

Load Balancer

CI/CD

GitHub Actions

Docker Registry

---

# Backend Principles Summary

1. Controllers contain no business logic.

2. Services contain all business logic.

3. Repositories only access the database.

4. DTOs isolate entities.

5. FastAPI performs AI.

6. Gemini performs language reasoning.

7. Spring Boot coordinates the entire workflow.

8. Every responsibility belongs to exactly one layer.