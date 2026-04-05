#!/bin/bash
# Exam Seating System - Complete Setup and Run Script
# This script installs all dependencies and runs the project

set -e  # Exit on error

echo "╔════════════════════════════════════════════════════════════╗"
echo "║   Exam Seating Arrangement System - Setup & Run           ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print colored messages
print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_info() {
    echo -e "${YELLOW}➜ $1${NC}"
}

# Check if running as root
if [ "$EUID" -eq 0 ]; then 
    print_error "Please do not run this script as root"
    exit 1
fi

echo ""
print_info "Step 1: Checking system requirements..."
echo ""

# Check for Java
if ! command -v java &> /dev/null; then
    print_info "Java not found. Installing OpenJDK 17..."
    sudo pacman -S --needed --noconfirm jdk17-openjdk
    print_success "Java installed"
else
    print_success "Java already installed"
    java -version
fi

# Check for Maven
if ! command -v mvn &> /dev/null; then
    print_info "Maven not found. Installing Maven..."
    sudo pacman -S --needed --noconfirm maven
    print_success "Maven installed"
else
    print_success "Maven already installed"
fi

# Check for Node.js
if ! command -v node &> /dev/null; then
    print_info "Node.js not found. Installing Node.js..."
    sudo pacman -S --needed --noconfirm nodejs npm
    print_success "Node.js installed"
else
    print_success "Node.js already installed"
    node -v
fi

echo ""
print_info "Step 2: Setting up Backend (Spring Boot)..."
echo ""

cd backend

# Add H2 database dependency
print_info "Adding H2 database dependency to pom.xml..."
if ! grep -q "h2" pom.xml; then
    sed -i '/<dependencies>/a\        <!-- H2 Database -->\n        <dependency>\n            <groupId>com.h2database</groupId>\n            <artifactId>h2</artifactId>\n            <scope>runtime</scope>\n        </dependency>' pom.xml
    print_success "H2 dependency added"
else
    print_success "H2 dependency already present"
fi

# Build backend
print_info "Building backend (this may take a few minutes)..."
mvn clean install -DskipTests

if [ $? -eq 0 ]; then
    print_success "Backend built successfully!"
else
    print_error "Backend build failed!"
    exit 1
fi

cd ..

echo ""
print_info "Step 3: Setting up Frontend (React)..."
echo ""

cd frontend

# Install frontend dependencies
if [ ! -d "node_modules" ]; then
    print_info "Installing frontend dependencies..."
    npm install
    print_success "Frontend dependencies installed"
else
    print_success "Frontend dependencies already installed"
fi

cd ..

echo ""
print_success "✓ All dependencies installed successfully!"
echo ""
echo "╔════════════════════════════════════════════════════════════╗"
echo "║                  STARTING APPLICATIONS                     ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# Create a script to run backend in background
cat > run_backend.sh << 'BACKEND_EOF'
#!/bin/bash
cd backend
echo "Starting Spring Boot backend on http://localhost:8080/api ..."
mvn spring-boot:run -Dspring-boot.run.profiles=h2
BACKEND_EOF
chmod +x run_backend.sh

# Create a script to run frontend in background
cat > run_frontend.sh << 'FRONTEND_EOF'
#!/bin/bash
cd frontend
echo "Starting React frontend on http://localhost:5173 ..."
npm run dev
FRONTEND_EOF
chmod +x run_frontend.sh

echo ""
print_info "Starting Backend..."
echo ""

# Start backend in background
gnome-terminal --title="Backend - Spring Boot" -- bash -c "./run_backend.sh; exec bash" 2>/dev/null || \
xterm -title "Backend - Spring Boot" -e "./run_backend.sh" 2>/dev/null || \
konsole --title "Backend - Spring Boot" -e "./run_backend.sh" 2>/dev/null || \
./run_backend.sh &

BACKEND_PID=$!
sleep 5

echo ""
print_info "Starting Frontend..."
echo ""

# Start frontend in background
gnome-terminal --title="Frontend - React" -- bash -c "./run_frontend.sh; exec bash" 2>/dev/null || \
xterm -title "Frontend - React" -e "./run_frontend.sh" 2>/dev/null || \
konsole --title "Frontend - React" -e "./run_frontend.sh" 2>/dev/null || \
./run_frontend.sh &

FRONTEND_PID=$!

echo ""
echo "╔════════════════════════════════════════════════════════════╗"
echo "║              APPLICATION STARTED SUCCESSFULLY!             ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""
print_success "Backend running at: http://localhost:8080/api"
print_success "Frontend running at: http://localhost:5173"
print_success "H2 Console at: http://localhost:8080/api/h2-console"
echo ""
print_info "Opening browser in 5 seconds..."
sleep 5

# Open browser
if command -v xdg-open &> /dev/null; then
    xdg-open http://localhost:5173
elif command -v firefox &> /dev/null; then
    firefox http://localhost:5173 &
elif command -v chromium &> /dev/null; then
    chromium http://localhost:5173 &
fi

echo ""
print_info "To stop the application:"
echo "  - Close the terminal windows, or"
echo "  - Press Ctrl+C in the backend/frontend terminals, or"
echo "  - Run: pkill -f 'spring-boot:run' && pkill -f 'vite'"
echo ""
print_success "Enjoy the Exam Seating System! 🎓"
echo ""

# Keep script running
wait
