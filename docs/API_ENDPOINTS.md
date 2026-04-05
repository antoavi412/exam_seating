# API Endpoints Documentation

## Base URL
```
http://localhost:8080/api
```

## Authentication
Currently no authentication required (can be added with Spring Security)

---

## 📊 Dashboard

### Get Dashboard Statistics
```http
GET /dashboard/stats
```

**Response:**
```json
{
  "totalStudents": 200,
  "totalHalls": 5,
  "totalInvigilators": 15,
  "totalExams": 3,
  "scheduledExams": 2,
  "completedExams": 1,
  "totalHallCapacity": 410
}
```

---

## 👨‍🎓 Students

### Get All Students
```http
GET /students
```

**Response:**
```json
[
  {
    "id": 1,
    "rollNumber": "CSE2021001",
    "name": "Aarav Sharma",
    "departmentId": 1,
    "departmentCode": "CSE",
    "departmentName": "Computer Science and Engineering",
    "year": 3,
    "email": "aarav.sharma@university.edu"
  }
]
```

### Get Student by ID
```http
GET /students/{id}
```

### Create Student
```http
POST /students
Content-Type: application/json

{
  "rollNumber": "CSE2024001",
  "name": "John Doe",
  "departmentId": 1,
  "year": 3,
  "email": "john.doe@university.edu"
}
```

**Validation Rules:**
- `rollNumber`: Required, unique
- `name`: Required
- `departmentId`: Required, must exist
- `year`: Required, integer
- `email`: Optional, valid email format

### Update Student
```http
PUT /students/{id}
Content-Type: application/json

{
  "rollNumber": "CSE2024001",
  "name": "John Doe Updated",
  "departmentId": 1,
  "year": 4,
  "email": "john.updated@university.edu"
}
```

### Delete Student
```http
DELETE /students/{id}
```

**Response:** 204 No Content

### Get Students by Department
```http
GET /students/department/{departmentId}
```

---

## 🏛️ Halls

### Get All Halls
```http
GET /halls
```

**Response:**
```json
[
  {
    "id": 1,
    "hallCode": "H1",
    "name": "Main Examination Hall 1",
    "capacity": 100,
    "rows": 10,
    "columns": 10,
    "building": "Academic Block A",
    "floor": 1,
    "isActive": true
  }
]
```

### Get Active Halls Only
```http
GET /halls/active
```

### Get Hall by ID
```http
GET /halls/{id}
```

### Create Hall
```http
POST /halls
Content-Type: application/json

{
  "hallCode": "H6",
  "name": "New Examination Hall",
  "capacity": 60,
  "rows": 10,
  "columns": 6,
  "building": "Science Block",
  "floor": 3,
  "isActive": true
}
```

**Validation Rules:**
- `hallCode`: Required, unique
- `name`: Required
- `capacity`: Required, must equal rows × columns
- `rows`: Required, minimum 1
- `columns`: Required, minimum 1
- `building`: Optional
- `floor`: Optional
- `isActive`: Optional, defaults to true

### Update Hall
```http
PUT /halls/{id}
Content-Type: application/json
```

### Delete Hall
```http
DELETE /halls/{id}
```

---

## 📝 Exams

### Get All Exams
```http
GET /exams
```

**Response:**
```json
[
  {
    "id": 1,
    "examCode": "EX2024001",
    "subject": "Data Structures and Algorithms",
    "examDate": "2024-05-15",
    "startTime": "09:00:00",
    "endTime": "12:00:00",
    "duration": 180,
    "totalStudents": 65,
    "status": "SCHEDULED",
    "studentIds": [1, 2, 3, ...]
  }
]
```

### Get Exam by ID
```http
GET /exams/{id}
```

### Create Exam
```http
POST /exams
Content-Type: application/json

{
  "examCode": "EX2024004",
  "subject": "Operating Systems",
  "examDate": "2024-06-01",
  "startTime": "09:00:00",
  "endTime": "12:00:00",
  "duration": 180,
  "studentIds": [1, 2, 3, 4, 5]
}
```

**Validation Rules:**
- `examCode`: Required, unique
- `subject`: Required
- `examDate`: Required, future date
- `startTime`: Required
- `endTime`: Required, must be after startTime
- `duration`: Required, in minutes
- `studentIds`: Optional array of student IDs

### Update Exam
```http
PUT /exams/{id}
Content-Type: application/json
```

### Delete Exam
```http
DELETE /exams/{id}
```

### Allocate Seating (IMPORTANT)
```http
POST /exams/{id}/allocate-seating
```

**What it does:**
- Retrieves all students registered for the exam
- Groups students by department
- Distributes across available active halls
- Ensures no adjacent same-department students
- Saves seating allocations to database

**Response:**
```json
[
  {
    "id": 1,
    "examId": 1,
    "studentId": 5,
    "studentName": "Arjun Patel",
    "studentRollNumber": "CSE2021005",
    "departmentCode": "CSE",
    "hallId": 1,
    "hallCode": "H1",
    "hallName": "Main Examination Hall 1",
    "rowNumber": 1,
    "columnNumber": 1,
    "seatNumber": "A-1"
  }
]
```

**Error Cases:**
- 400: No students registered for exam
- 400: No active halls available
- 400: Insufficient capacity (more students than total hall capacity)

### Assign Invigilators (IMPORTANT)
```http
POST /exams/{id}/allocate-invigilators
```

**What it does:**
- Retrieves halls used in seating allocation
- Gets available invigilators
- Assigns 2 per hall (1 Chief, 1 Assistant)
- Balances workload using historical data
- Avoids repeated hall assignments

**Response:**
```json
[
  {
    "id": 1,
    "exam": {...},
    "invigilator": {...},
    "hall": {...},
    "role": "CHIEF",
    "assignmentCount": 1
  }
]
```

**Error Cases:**
- 400: No seating allocation found (must allocate seating first)
- 400: No invigilators available

### Get Seating Plan
```http
GET /exams/{id}/seating
```

**Response:** Same as allocate-seating response

---

## 🔄 Workflow Example

### Complete Exam Setup Flow

1. **Ensure students and halls exist**
```http
GET /students
GET /halls
```

2. **Create an exam**
```http
POST /exams
{
  "examCode": "EX2024005",
  "subject": "Database Management Systems",
  "examDate": "2024-06-10",
  "startTime": "14:00:00",
  "endTime": "17:00:00",
  "duration": 180,
  "studentIds": [1, 2, 3, ..., 50]
}
```

3. **Allocate seating**
```http
POST /exams/1/allocate-seating
```

4. **Assign invigilators**
```http
POST /exams/1/allocate-invigilators
```

5. **View seating plan**
```http
GET /exams/1/seating
```

---

## 🧪 Testing with cURL

### Create a Student
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "rollNumber": "TEST2024001",
    "name": "Test Student",
    "departmentId": 1,
    "year": 3,
    "email": "test@university.edu"
  }'
```

### Get Dashboard Stats
```bash
curl http://localhost:8080/api/dashboard/stats
```

### Allocate Seating for Exam ID 1
```bash
curl -X POST http://localhost:8080/api/exams/1/allocate-seating
```

---

## 📄 Response Format

### Success Response
```json
{
  "id": 1,
  "field1": "value1",
  "field2": "value2"
}
```

### Error Response
```json
{
  "error": "Student with roll number CSE2021001 already exists"
}
```

### Validation Error Response
```json
{
  "rollNumber": "Roll number is required",
  "year": "Year must be at least 1"
}
```

---

## 🛡️ HTTP Status Codes

- `200 OK` - Successful GET, PUT
- `201 Created` - Successful POST
- `204 No Content` - Successful DELETE
- `400 Bad Request` - Validation error or business logic error
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

---

## 🔐 Future: Authentication Endpoints (Not Implemented Yet)

```http
POST /auth/login
POST /auth/register
POST /auth/logout
GET /auth/profile
```

---

## 📝 Notes

- All timestamps use ISO 8601 format
- All IDs are Long integers
- Dates use `yyyy-MM-dd` format
- Times use `HH:mm:ss` format
- Boolean values are `true` or `false`

## 🎯 Rate Limiting (Future Enhancement)

Currently no rate limiting. Consider adding:
- 100 requests per minute per IP
- 1000 requests per hour per IP
