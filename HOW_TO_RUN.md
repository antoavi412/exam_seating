# 🚀 HOW TO RUN THE PROJECT

## Quick Start (Automated)

Just run this command in terminal:

```bash
cd /home/arch/Desktop/exam_seatting
./setup_and_run.sh
```

This script will:
1. ✅ Install Java 17 (if needed)
2. ✅ Install Maven (if needed)
3. ✅ Install Node.js (if needed)
4. ✅ Build the backend
5. ✅ Install frontend dependencies
6. ✅ Start both applications
7. ✅ Open your browser automatically

**That's it!** The system will be running.

---

## Manual Setup (Step by Step)

If you prefer to do it manually:

### 1. Install Dependencies
```bash
# Install Java 17
sudo pacman -S jdk17-openjdk maven

# Install Node.js
sudo pacman -S nodejs npm
```

### 2. Start Backend
```bash
cd /home/arch/Desktop/exam_seatting/backend

# Build and run
mvn clean install
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

Keep this terminal open. Backend runs on **http://localhost:8080/api**

### 3. Start Frontend (New Terminal)
```bash
cd /home/arch/Desktop/exam_seatting/frontend

# Install dependencies (first time only)
npm install

# Start dev server
npm run dev
```

Keep this terminal open. Frontend runs on **http://localhost:5173**

### 4. Open Application
Open your browser and go to:
```
http://localhost:5173
```

---

## Using H2 Database (In-Memory)

The project is configured to use H2 database (no MySQL installation needed) for quick demo.

**Note:** Data will be lost when you stop the backend. For production, use MySQL.

---

## Verify It's Working

### Check Backend
```bash
curl http://localhost:8080/api/dashboard/stats
```

Should return JSON with statistics.

### Check Frontend
- Dashboard should load with statistics
- Navigation should work
- Click on different pages

---

## Stop the Application

**Method 1:** Close the terminal windows

**Method 2:** Press `Ctrl+C` in both terminals

**Method 3:** Kill processes
```bash
pkill -f spring-boot:run
pkill -f vite
```

---

## Troubleshooting

### Backend won't start
- **Check Java**: `java -version` (should be 17+)
- **Check Maven**: `mvn -version`
- **Check port 8080**: `lsof -i :8080` (kill if occupied)

### Frontend won't start
- **Check Node**: `node -v` (should be 16+)
- **Install deps**: `cd frontend && npm install`
- **Check port 5173**: `lsof -i :5173` (kill if occupied)

### Can't see data
- Backend uses H2 in-memory database
- Data is created automatically on startup
- Check H2 console: http://localhost:8080/api/h2-console
  - JDBC URL: `jdbc:h2:mem:exam_seating_db`
  - Username: `sa`
  - Password: (leave empty)

---

## Next Steps

Once running:
1. **Explore Dashboard** - See system statistics
2. **View Students** - 200 pre-loaded students
3. **Check Halls** - 5 examination halls
4. **Go to Exams** - Sample exams ready
5. **Click "Allocate Seating"** - See the algorithm in action!
6. **View Seating Plan** - See the results

---

## For Presentation

Run the automated setup script at least 30 minutes before your presentation:
```bash
./setup_and_run.sh
```

Have both terminals visible during demo.

**Good luck! 🎓**
