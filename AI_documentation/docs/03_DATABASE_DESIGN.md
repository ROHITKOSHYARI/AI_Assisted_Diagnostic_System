# Database Design

Version: 1.0

---

# Table of Contents

1. Database Philosophy
2. Why PostgreSQL?
3. Entity Relationship Diagram
4. Database Naming Conventions
5. UUID Strategy
6. Patient Entity
7. Doctor Entity
8. Report Entity
9. Enums
10. Relationships
11. Constraints
12. Indexes
13. Validation Rules
14. Repository Layer
15. DTO Philosophy
16. Future Database Expansion

---

# 1. Database Philosophy

The database is designed using relational principles.

The goal is to

- minimize duplication
- maintain data integrity
- keep relationships simple
- allow future expansion

The application uses PostgreSQL as the primary persistent storage.

Only business data is stored inside PostgreSQL.

Large files such as

- uploaded images
- GradCAM images
- generated PDFs

are stored separately.

Only their metadata and storage paths are saved inside PostgreSQL.

---

# Database Principles

The database follows these principles.

• Every table has one responsibility.

• Every row represents one business object.

• Relationships are normalized.

• UUID is used as the primary key.

• Images are never stored directly inside PostgreSQL.

• Passwords are always hashed.

---

# 2. Why PostgreSQL?

PostgreSQL was selected because

- ACID compliant
- Excellent Spring Boot support
- Hibernate compatible
- UUID support
- JSON support
- Mature indexing
- Open source
- Production ready

Future versions may use PostgreSQL JSON columns for AI metadata.

---

# 3. Entity Relationship Diagram

```

                    Patient

                        │

             One Patient

                        │

                 Many Reports

                        │

                        ▼

                   Report

                        ▲

                        │

            Many Reports

                        │

                 One Doctor

                        │

                    Doctor

```

---

# Current Tables

Version 1 contains only three major tables.

Patient

Doctor

Report

Keeping Version 1 small allows faster development.

---

# Future Tables

Version 2 may introduce

Appointments

Notifications

Messages

AuditLogs

RefreshTokens

ImageMetadata

PredictionHistory

Settings

These tables intentionally do not exist in Version 1.

---

# 4. Database Naming Conventions

Table Names

Plural

Examples

patients

doctors

reports

Column Names

snake_case

Examples

created_at

doctor_id

patient_id

profile_picture

password_hash

Entity Names

PascalCase

Examples

Patient

Doctor

Report

Repository Names

PatientRepository

DoctorRepository

ReportRepository

---

# 5. UUID Strategy

Every entity uses UUID.

Example

```
0e51c8a7-08d5-4989-b4ab-5d734f547e0f
```

Reason

UUID allows

Distributed systems

Safer URLs

No predictable IDs

Better future scalability

Spring Boot

```
@GeneratedValue(strategy = GenerationType.UUID)
```

---

# 6. Patient Entity

Purpose

Represents every patient registered on the platform.

Patients authenticate using JWT.

Patients own medical reports.

Patients can upload images.

Patients cannot approve doctors.

---

## Patient Fields

id

UUID

Primary Key

Unique identifier.

---

first_name

String

Patient first name.

---

last_name

String

Patient last name.

---

email

String

Unique.

Used for login.

Indexed.

---

password_hash

String

BCrypt hashed password.

Never returned in DTOs.

---

phone_number

String

Contact number.

---

gender

Enum

MALE

FEMALE

OTHER

---

date_of_birth

LocalDate

Used for age calculation.

---

profile_picture

String

Stores image URL.

Not image itself.

---

created_at

LocalDateTime

Creation timestamp.

---

updated_at

LocalDateTime

Last modification timestamp.

---

Relationships

Patient

↓

OneToMany

↓

Reports

---

Business Rules

Email must be unique.

Password must never be stored as plaintext.

Patient may own many reports.

Patient may delete account in future.

---

# 7. Doctor Entity

Purpose

Represents verified medical professionals.

Doctors are completely separate from patients.

Doctors have different registration rules.

Doctors require approval.

---

Doctor Fields

id

UUID

Primary Key

---

first_name

Doctor first name.

---

last_name

Doctor last name.

---

email

Unique login email.

---

password_hash

Encrypted password.

---

specialization

Example

Neurologist

Radiologist

Dermatologist

General Physician

---

qualification

Example

MBBS

MD

MS

DM

---

experience

Years of experience.

---

hospital

Hospital name.

---

license_number

Government issued registration.

Must be unique.

---

profile_picture

Image URL.

---

consultation_fee

Double

Future use.

---

doctor_status

Enum

PENDING

UNDER_REVIEW

APPROVED

REJECTED

SUSPENDED

---

rejection_reason

Nullable.

Only filled when rejected.

---

availability_status

AVAILABLE

BUSY

OFFLINE

---

created_at

Timestamp.

---

Relationships

Doctor

↓

OneToMany

↓

Reports

---

Business Rules

Only APPROVED doctors may access doctor dashboard.

Rejected doctors cannot view reports.

Suspended doctors cannot login.

License number must be unique.

---

# 8. Report Entity

Purpose

Stores one AI generated medical analysis.

Every uploaded image creates one report.

Reports are immutable after generation except for doctor review status.

---

Report Fields

id

UUID

Primary Key

---

patient

ManyToOne

Owner of report.

---

doctor

ManyToOne

Nullable.

Filled when a doctor reviews report.

---

disease

String

Predicted disease.

---

confidence

Double

Prediction confidence.

---

model_name

Example

BrainTumorCNN_v1

EfficientNetB0

ResNet50

---

image_url

Uploaded image location.

---

gradcam_image_url

Heatmap location.

---

symptoms

Long Text

Original symptoms entered by patient.

---

gemini_analysis

Long Text

AI explanation returned from Gemini.

---

report_status

Enum

GENERATED

REVIEWED

---

created_at

Timestamp.

---

Relationships

Many Reports

↓

One Patient

Many Reports

↓

One Doctor

---

Business Rules

Every report belongs to exactly one patient.

Doctor is optional.

Reports are never deleted automatically.

Reports can generate PDFs at any time.

---

# 9. Enums

DoctorStatus

```
PENDING
UNDER_REVIEW
APPROVED
REJECTED
SUSPENDED
```

AvailabilityStatus

```
AVAILABLE
BUSY
OFFLINE
```

Gender

```
MALE
FEMALE
OTHER
```

ReportStatus

```
GENERATED
REVIEWED
```

---

# 10. Relationships

Patient

OneToMany

Reports

Reason

One patient may upload many images.

Every upload creates one report.

Doctor

OneToMany

Reports

Reason

One doctor may review many reports.

A report belongs to only one reviewing doctor.

---

# 11. Constraints

Patients

email UNIQUE

Doctors

email UNIQUE

license_number UNIQUE

Reports

patient_id NOT NULL

Doctor foreign key nullable.

Password fields NOT NULL.

Creation timestamps NOT NULL.

---

# 12. Index Recommendations

Create indexes on

patients.email

doctors.email

doctors.license_number

reports.patient_id

reports.doctor_id

reports.created_at

These fields will be queried frequently.

---

# 13. Validation Rules

Patient

Email valid.

Password minimum 8 characters.

Phone length validation.

Doctor

License required.

Qualification required.

Experience >= 0.

Consultation fee >= 0.

Report

Image required.

Disease not null.

Confidence between 0 and 100.

---

# 14. Repository Layer

PatientRepository

Responsibilities

Find by email.

Check email exists.

CRUD operations.

---

DoctorRepository

Find by email.

Find approved doctors.

Find by status.

CRUD operations.

---

ReportRepository

Find by patient.

Find by doctor.

Find by date.

Prediction history.

CRUD operations.

---

# 15. DTO Philosophy

Entities are never exposed directly.

Every API uses DTOs.

Reasons

Security

Loose coupling

Validation

Versioning

Patient DTOs

PatientRequestDTO

PatientResponseDTO

PatientLoginDTO

Doctor DTOs

DoctorRegistrationDTO

DoctorLoginDTO

DoctorResponseDTO

Report DTOs

ReportResponseDTO

PredictionResponseDTO

UploadRequestDTO

---

# 16. Future Expansion

The current schema intentionally leaves room for

Appointment entity

Chat entity

Notification entity

Redis caching

Kafka events

Audit logging

Refresh tokens

Medical history

Electronic health records

without changing existing relationships.

---

# Database Design Summary

The Version 1 database intentionally remains compact.

Only three core entities exist:

• Patient

• Doctor

• Report

This keeps development manageable while still supporting all core features:

- Authentication
- AI predictions
- Doctor verification
- Report generation
- PDF generation
- Prediction history

Future versions can extend the schema without redesigning the existing database.