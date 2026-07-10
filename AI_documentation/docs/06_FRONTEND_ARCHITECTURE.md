# Frontend Architecture

Version: 1.0

---

# Table of Contents

1. Frontend Philosophy
2. Why React?
3. Responsibilities
4. Project Structure
5. Application Flow
6. Pages
7. Components
8. Authentication Flow
9. API Communication
10. Route Protection
11. UI Design
12. Future Improvements

---

# 1. Frontend Philosophy

The frontend is responsible for providing a clean and responsive user interface.

It should never contain business logic.

All business logic belongs to Spring Boot.

The frontend only

- Collects user input
- Displays data
- Calls backend APIs
- Displays AI reports

---

# 2. Why React?

React was chosen because

- Component-based architecture
- Fast rendering
- Easy state management
- Excellent REST API support
- Large ecosystem
- Easy integration with Spring Boot

---

# 3. Frontend Responsibilities

The frontend is responsible for

- User Registration
- User Login
- Doctor Registration
- Doctor Login
- Image Upload
- Symptom Input
- Viewing Reports
- Downloading PDF Reports
- Viewing Prediction History
- Profile Management

The frontend is NOT responsible for

- Authentication logic
- AI inference
- Database operations
- JWT generation

---

# 4. Project Structure

```

frontend/

│

├── public/

├── src/

│

├── assets/

├── components/

├── pages/

├── layouts/

├── services/

├── hooks/

├── utils/

├── routes/

├── context/

├── styles/

├── App.jsx

└── main.jsx

```

---

# Components Folder

Reusable UI components.

Examples

Navbar

Sidebar

Footer

ImageUploader

PredictionCard

DoctorCard

ReportCard

LoadingSpinner

ProtectedRoute

---

# Pages Folder

Contains complete application pages.

Patient

Doctor

Admin

Authentication

Dashboard

---

# Services Folder

Responsible for communicating with Spring Boot.

Examples

AuthService

PatientService

DoctorService

PredictionService

ReportService

---

# Context Folder

Stores global application state.

Example

Authentication state

Logged-in user

JWT token

Theme (future)

---

# 5. Application Flow

Application starts

↓

Login Screen

↓

JWT Authentication

↓

Dashboard

↓

Upload Image

↓

Prediction

↓

Report

↓

Download PDF

---

# 6. Pages

Landing Page

Purpose

Project introduction.

---

Patient Login

Patient authentication.

---

Patient Registration

Create patient account.

---

Doctor Login

Doctor authentication.

---

Doctor Registration

Register doctor account.

---

Patient Dashboard

Patient home page.

Displays

Prediction History

Quick Actions

Profile

---

Doctor Dashboard

Displays

Doctor Information

Approval Status

Reviewed Reports (future)

---

Admin Dashboard

Displays

Registered Doctors

Approve Doctor

Reject Doctor

---

Prediction Page

Patient uploads

Medical image

Symptoms

Receives prediction

---

Report Page

Displays

Prediction

Confidence

Grad-CAM

Gemini Analysis

Download PDF

---

Profile Page

Update

Name

Phone

Profile Picture

Password

---

# 7. Components

Navbar

Displays

Logo

Navigation

Logout

---

Sidebar

Dashboard navigation.

---

ImageUploader

Allows patient to upload image.

Shows preview before upload.

---

PredictionCard

Displays

Disease

Confidence

Model Name

---

GradCAMViewer

Displays generated heatmap.

---

ReportCard

Displays report summary.

---

LoadingSpinner

Displayed while AI prediction is running.

---

ProtectedRoute

Blocks unauthorized users.

---

# 8. Authentication Flow

Patient Login

↓

Spring Boot

↓

JWT Returned

↓

Store JWT

↓

Protected Dashboard

Doctor Login

↓

Doctor Approved?

↓

If Approved

↓

JWT

↓

Dashboard

If not approved

↓

Display message

"Your account is awaiting verification."

---

# 9. API Communication

Frontend communicates only with Spring Boot.

React

↓

Axios

↓

Spring Boot

↓

Response

FastAPI is never called directly.

---

Example APIs

POST

/api/patients/login

POST

/api/doctors/login

POST

/api/predict

GET

/api/reports

GET

/api/profile

---

# 10. Route Protection

Public Routes

/

Patient Login

Doctor Login

Patient Register

Doctor Register

Protected Routes

Patient Dashboard

Prediction

Reports

Profile

Doctor Dashboard

Admin Dashboard

If JWT expires

↓

Redirect to Login

---

# 11. UI Design

The application should maintain a clean medical dashboard design.

Recommended colors

Primary

Blue

Secondary

White

Accent

Light Gray

Success

Green

Warning

Orange

Error

Red

Design Principles

Simple

Minimal

Professional

Responsive

Accessible

Avoid unnecessary animations.

Focus on readability.

---

# 12. Future Improvements

Dark Mode

Notifications

Charts

Analytics

Live Chat

Appointment Booking

Real-time Updates

Mobile Application

Offline Support

---

# Summary

The frontend is responsible only for user interaction.

It should remain lightweight, responsive, and independent of business logic.

All important operations are delegated to Spring Boot through REST APIs.

This separation makes the frontend easy to maintain and allows future replacement with mobile or desktop clients without changing the backend.