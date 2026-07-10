# Agent Instructions

This file is the primary instruction source for AI agents working on this project.

## Project Goal

Build an AI Assisted Diagnostic System with:

- React frontend
- Spring Boot backend
- FastAPI AI inference service
- PostgreSQL database
- CNN-based medical image prediction
- Gemini-based symptom explanation
- Report generation and history

The system is an educational and research project. It must not present AI output as a final clinical diagnosis.

## Core Architecture Rules

- React handles UI only.
- Spring Boot handles authentication, authorization, business logic, database access, report management, FastAPI calls, Gemini calls, and PDF generation.
- FastAPI handles only AI image inference, image preprocessing, model routing, prediction, and Grad-CAM generation.
- PostgreSQL stores business data only.
- Gemini handles only symptom explanation and natural language reasoning.
- Frontend must never call FastAPI directly.
- FastAPI must never access PostgreSQL.
- Controllers must not contain business logic.
- Services contain business logic.
- Repositories only access the database.
- Entities must not be returned directly from APIs.
- Use DTOs for all request and response bodies.

## Repository Areas

- `backend/`: Spring Boot backend.
- `frontend/`: React frontend.
- `mlBackend/`: Python/FastAPI AI backend.
- `AI_documentation/`: project planning and architecture documentation.

Do not edit folders unrelated to the user request.

## Backend Rules

Use the package:

```text
com.rohit.diagnostic_system
```

Current backend layers:

- `controller`
- `service`
- `repository`
- `entity`
- `DTO`
- `Enum`

Follow the existing package names unless the user explicitly asks for a refactor.

Backend implementation rules:

- Use constructor injection with Lombok `@RequiredArgsConstructor`.
- Prefer `@RestController` for API controllers.
- Do not expose password hashes in response DTOs.
- Do not return JPA entities directly from controllers.
- Use UUID IDs.
- Keep controllers thin.
- Put mapping logic in services unless mapper classes are introduced consistently.
- Use Spring Data JPA repositories.
- Use `ResponseEntity` from controllers.
- Add validation later when requested or when implementing production-ready APIs.

## Core Entities

Patient entity is currently named `User`.

Main entities:

- `User`: patient account and profile.
- `Doctor`: doctor account, verification status, availability.
- `Report`: AI prediction report linked to one patient and optionally one doctor.

Important relationships:

- One user can have many reports.
- One doctor can review many reports.
- Every report must belong to one user.
- A report may have no doctor initially.

## Important Enums

Use existing enums:

- `Gender`
- `DoctorStatus`
- `AvailabilityStatus`
- `ReportStatus`

Do not create duplicate enum types.

## API Direction

Preferred long-term API style:

```text
/api/patients
/api/doctors
/api/reports
/api/admin
/api/predict
```

If existing code uses different paths, preserve compatibility unless the user asks to normalize routes.

Standard future response format:

```json
{
  "success": true,
  "message": "Operation successful.",
  "data": {}
}
```

Do not force this response wrapper unless the user asks for response standardization.

## Security Direction

Planned security:

- JWT authentication
- Role-based authorization
- BCrypt password hashing
- Patient, doctor, and admin roles
- Doctor login blocked until approval
- Secrets from environment variables

Never hardcode:

- JWT secrets
- Gemini API keys
- database passwords
- production URLs

Never log:

- passwords
- JWT tokens
- medical images
- private medical report content

## AI Service Rules

FastAPI service responsibilities:

- Validate image format.
- Preprocess image.
- Route request to the correct CNN model.
- Run prediction.
- Generate Grad-CAM.
- Return structured JSON.

FastAPI response should include:

```json
{
  "disease": "Brain Tumor",
  "confidence": 97.84,
  "modelName": "BrainTumorCNN_v1",
  "gradCamImageUrl": "/gradcam/file.png"
}
```

FastAPI must not:

- authenticate users
- generate JWTs
- manage doctors
- store reports in PostgreSQL
- call Gemini unless explicitly redesigned

## Gemini Rules

Gemini is used for symptom analysis only.

Spring Boot should:

- build the prompt
- call Gemini
- parse the response
- store explanation in the report

Do not build Gemini prompts inside controllers.

## Report Rules

A report should store:

- patient
- optional doctor
- predicted disease
- confidence
- model name
- uploaded image URL/path
- Grad-CAM image URL/path
- symptoms
- Gemini analysis
- report status
- created timestamp

Reports are prediction records. They should not claim final medical certainty.

## Development Order

Preferred implementation order:

1. Database entities, repositories, and enums.
2. Patient, doctor, and report DTOs.
3. Basic CRUD/profile services and controllers.
4. Authentication and JWT.
5. Doctor verification workflow.
6. Report creation and history.
7. FastAPI prediction service.
8. Spring Boot to FastAPI integration.
9. Gemini integration.
10. PDF report generation.
11. Frontend integration.
12. Docker Compose.
13. Tests and cleanup.

When the user asks for a specific task, do only that task and any required direct dependencies.

## Coding Style

- Keep changes small and focused.
- Prefer clear names over clever abstractions.
- Do not introduce new architecture unless needed.
- Do not rename existing packages/classes unless required.
- Do not rewrite working code unnecessarily.
- Do not add unrelated features.
- Do not edit generated files or build output.
- Compile or run relevant tests after backend changes when feasible.

## Documentation Usage

The files in `AI_documentation/docs` explain the intended project flow.

Use them as guidance for:

- architecture decisions
- service responsibilities
- implementation order
- feature boundaries

Do not treat them as exact implemented code status.

Before implementing, inspect the actual source files in the relevant project folder.

## Current Practical Notes

- Backend is Spring Boot with Maven.
- Java version is 21.
- The backend currently uses Lombok.
- Patient is represented by the `User` entity.
- DTO package is currently named `DTO`, not `dto`.
- Enum package is currently named `Enum`, not `enums`.
- Preserve these names unless a package cleanup is explicitly requested.

## Medical Disclaimer Rule

Any user-facing report or AI output should include the idea that results are AI-assisted and should be reviewed by a qualified medical professional.

Do not present predictions as guaranteed diagnosis.
