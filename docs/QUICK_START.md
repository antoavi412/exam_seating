# ⚡ Quick Start Guide

Get the Exam Seating Arrangement System running in 10 minutes!

## Prerequisites Check

Run these commands to verify you have everything installed:

```bash
# Check Java (need 17+)
java -version

# Check Maven
mvn -version

# Check Node.js (need 16+)
node -v

# Check npm
npm -v

# Check MySQL
mysql --version
```

If any are missing, install them first!

## 🚀 Quick Installation (Arch Linux)

### 1. Install Missing Tools
```bash
# Install Java 17 and Maven
sudo pacman -S jdk17-openjdk maven

# Install MySQL
sudo pacman -S mysql

# Initialize MySQL (first time only)
sudo mysql_install_db --user=mysql --basedir=/usr --datadir=/var/lib/mysql

# Start MySQL
sudo systemctl start mysql
sudo systemctl enable mysql

# Secure MySQL installation
sudo mysql_secure_installation
```

### 2. Setup Database
```bash
# Login to MySQL
sudo mysql -u root -p

# In MySQL shell, create user and grant privileges
CREATE USER 'examuser'@'localhost' IDENTIFIED BY 'exampass123';
GRANT ALL PRIVILEGES ON *.* TO 'examuser'@'localhost';
FLUSH PRIVILEGES;
exit;

# Load database schema
cd /home/arch/Desktop/exam_seatting
mysql -u root -p < database/schema.sql

# Load sample data (RECOMMENDED for demo)
mysql -u root -p < database/sample_data.sql
```

### 3. Configure Backend
```bash
cd backend

# Edit application.properties with your MySQL password
nano src/main/resources/application.properties

# Change these lines:
# spring.datasource.username=root
# spring.datasource.password=YOUR_PASSWORD_HERE
```

### 4. Start Backend
```bash
# From backend directory
mvn clean install
mvn spring-boot:run

# Wait for: "Started ExamSeatingSystemApplication in X seconds"
# Backend is now running on http://localhost:8080
```

**Leave this terminal open!**

### 5. Start Frontend (New Terminal)
```bash
# Open new terminal
cd /home/arch/Desktop/exam_seatting/frontend

# Install dependencies (first time only)
npm install

# Start development server
npm run dev

# Frontend is now running on http://localhost:5173
```

**Leave this terminal open too!**

### 6. Open Application
Open your browser and go to:
```
http://localhost:5173
```

## ✅ Verify Installation

### Check Backend
```bash
curl http://localhost:8080/api/dashboard/stats
```

You should see JSON with statistics.

### Check Frontend
- Dashboard should show statistics
- Navigation should work
- Click "Students" - should see list of students

### Test Seating Allocation
1. Go to "Exams"
2. Find an exam (e.g., "Data Structures and Algorithms")
3. Click "Allocate Seating"
4. Wait for success message
5. Go to "Seating Plan"
6. Select the exam
7. See the seating arrangement!

## 🐛 Quick Troubleshooting

### Backend won't start

**Error: "Port 8080 already in use"**
```bash
# Find and kill process on port 8080
sudo lsof -i :8080
sudo kill -9 <PID>
```

**Error: "Cannot connect to MySQL"**
```bash
# Check MySQL is running
sudo systemctl status mysql

# Start if not running
sudo systemctl start mysql

# Check credentials in application.properties
```

**Error: "Table doesn't exist"**
```bash
# Reload database schema
cd /home/arch/Desktop/exam_seatting
mysql -u root -p < database/schema.sql
mysql -u root -p < database/sample_data.sql
```

### Frontend won't start

**Error: "Cannot find module"**
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
```

**Error: "Port 5173 already in use"**
```bash
# Kill process on port 5173
sudo lsof -i :5173
sudo kill -9 <PID>
```

### API calls not working

**CORS errors in browser console:**
- Make sure backend is running on port 8080
- Check CorsConfig.java allows localhost:5173

**404 errors:**
- Verify backend URL in `frontend/src/services/api.js`
- Should be: `http://localhost:8080/api`

## 📊 Sample Data Overview

The `sample_data.sql` includes:
- **200 students** across 8 departments
- **5 examination halls** with different capacities
- **15 invigilators**
- **3 sample exams** already configured

### Departments
1. CSE - Computer Science (40 students)
2. ECE - Electronics (35 students)
3. ME - Mechanical (30 students)
4. CE - Civil (25 students)
5. EEE - Electrical (25 students)
6. IT - Information Technology (25 students)
7. CHEM - Chemical (10 students)
8. BT - Biotechnology (10 students)

### Halls
- H1: 100 seats (10×10)
- H2: 80 seats (10×8)
- H3: 60 seats (10×6)
- H4: 50 seats (10×5)
- H5: 120 seats (12×10)

**Total Capacity: 410 seats**

## 🎯 Next Steps

1. **Explore the Dashboard** - See system statistics
2. **Browse Students** - View all registered students
3. **Check Halls** - Review examination halls
4. **Create an Exam** - Add a new exam
5. **Allocate Seating** - Run the algorithm
6. **View Seating Plan** - See the results
7. **Assign Invigilators** - Fair distribution

## 🎓 For Presentation Demo

### Preparation (5 minutes before)
```bash
# Terminal 1: Start backend
cd /home/arch/Desktop/exam_seatting/backend
mvn spring-boot:run

# Terminal 2: Start frontend
cd /home/arch/Desktop/exam_seatting/frontend
npm run dev

# Browser: Open http://localhost:5173
```

### Demo Flow (Recommended)
1. **Show Dashboard** (30 sec)
   - "Here we can see we have 200 students, 5 halls, and 15 invigilators"

2. **Show Students** (30 sec)
   - "Students are organized by department - CSE, ECE, ME, etc."
   - Scroll through the list

3. **Show Halls** (30 sec)
   - "We have 5 halls with different capacities"
   - Point out the grid layout (rows × columns)

4. **Show Exams** (1 min)
   - "Here's an exam for Data Structures - 65 students registered"
   - **Click "Allocate Seating"**
   - Explain: "The algorithm groups by department and ensures no same-department students sit together"

5. **Show Seating Plan** (2 min)
   - Select the exam
   - "See how students are distributed across halls"
   - Point out the seat numbers (A-1, A-2, etc.)
   - Show different departments are mixed

6. **Click "Assign Invigilators"** (30 sec)
   - "System automatically assigns 2 invigilators per hall with fair workload distribution"

### Backup Plan
If live demo fails, have screenshots ready or a video recording!

## 💾 Backup & Restore

### Backup Database
```bash
mysqldump -u root -p exam_seating_db > backup.sql
```

### Restore Database
```bash
mysql -u root -p exam_seating_db < backup.sql
```

## 🎨 Customization

### Change Color Theme
Edit `frontend/src/App.jsx`:
```javascript
// Change from indigo to blue
className="bg-indigo-600" → className="bg-blue-600"
```

### Change Port Numbers

**Backend (from 8080):**
Edit `backend/src/main/resources/application.properties`:
```properties
server.port=9090
```

**Frontend (from 5173):**
Edit `frontend/vite.config.js`:
```javascript
export default {
  server: {
    port: 3000
  }
}
```

Don't forget to update API URL in `frontend/src/services/api.js`!

## 📞 Need Help?

1. Check the main README.md
2. Check API_ENDPOINTS.md
3. Look at error messages in terminal
4. Check browser console (F12)
5. Verify MySQL is running
6. Verify correct ports

## ✨ You're Ready!

The system is now running. Enjoy exploring the Exam Seating Arrangement System!

**Pro tip:** Keep both terminals open while using the application. Watch for error messages if something doesn't work.
