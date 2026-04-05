#!/bin/bash
# Quick Run Script (if already set up)

echo "🚀 Starting Exam Seating System..."
echo ""

cd /home/arch/Desktop/exam_seatting

# Start backend
echo "Starting Backend..."
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=h2 > backend.log 2>&1 &
BACKEND_PID=$!
cd ..

echo "Backend PID: $BACKEND_PID"
echo "Waiting for backend to start..."
sleep 15

# Start frontend
echo "Starting Frontend..."
cd frontend
npm run dev > frontend.log 2>&1 &
FRONTEND_PID=$!
cd ..

echo "Frontend PID: $FRONTEND_PID"
echo ""
echo "✓ Backend: http://localhost:8080/api (PID: $BACKEND_PID)"
echo "✓ Frontend: http://localhost:5173 (PID: $FRONTEND_PID)"
echo ""
echo "Opening browser..."
sleep 3
xdg-open http://localhost:5173 2>/dev/null || firefox http://localhost:5173 2>/dev/null &

echo ""
echo "To stop: kill $BACKEND_PID $FRONTEND_PID"
echo "Or press Ctrl+C and run: pkill -f spring-boot && pkill -f vite"
echo ""

# Keep script running
wait
