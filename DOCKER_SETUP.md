# Build Instructions

## Prerequisites
- Docker Desktop installed and running
- Git

## Quick Start (सब कुछ एक command में)

### 1. Clone the repository
```bash
git clone https://github.com/kamrudin1998/book-management
cd book-management
```

### 2. Start everything with Docker Compose
```bash
docker-compose up --build
```

यह command:
- MongoDB container build करेगा
- Spring Boot app container build करेगा
- दोनों को एक network में connect करेगा
- Automatically health checks run करेगा

### 3. Access the application
- **Frontend (User View)**: http://localhost:8080/book-management-ui.html
- **Admin Panel**: http://localhost:8080/admin-login.html
- **API Base URL**: http://localhost:8080/api

### 4. Default Admin Credentials
```
Email: admin@yopmail.com
Password: Admin@123
```

### 5. MongoDB Connection (if needed)
```bash
# Connect to MongoDB from host
mongosh "mongodb://admin:admin123@localhost:27017/bookdb"
```

---

## 🛑 Stop Everything
```bash
docker-compose down
```

## 🗑️ Clean Up (remove volumes)
```bash
docker-compose down -v
```

## 📊 View Logs
```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f app
docker-compose logs -f mongodb
```

## 🔄 Restart Services
```bash
docker-compose restart
```

## 📦 Build Only (without starting)
```bash
docker-compose build
```

---

## Architecture

```
┌─────────────────────────────────────────┐
│         Docker Network (bridge)         │
├─────────────────────────────────────────┤
│                                         │
│  ┌──────────────────────────────────┐  │
│  │   Spring Boot App (Port 8080)    │  │
│  │   - REST API                     │  │
│  │   - Static UI Files              │  │
│  │   - JWT Authentication           │  │
│  └──────────────────────────────────┘  │
│              ↓ connects to             │
│  ┌──────────────────────────────────┐  │
│  │  MongoDB (Port 27017)            │  │
│  │  - User Collection               │  │
│  │  - Book Collection               │  │
│  │  - Indexes                       │  │
│  └──────────────────────────────────┘  │
│                                         │
└─────────────────────────────────────────┘
```

---

## Environment Variables (docker-compose.yml में)

```env
MONGODB_URI=mongodb://admin:admin123@mongodb:27017/bookdb?authSource=admin
DATABASE_NAME=bookdb
JWT_SECRET=kamruD1n_ThisIsA_SUPER_Long_JWT_Secret_Key_987654321
JWT_EXPIRATION_MS=86400000
ADMIN_EMAIL=admin@yopmail.com
ADMIN_PASSWORD=Admin@123
ADMIN_NAME=System Admin
```

---

## Troubleshooting

### Port already in use?
```bash
# Find and kill process using port 8080
lsof -i :8080
kill -9 <PID>

# Or change port in docker-compose.yml
# Change "8080:8080" to "8081:8080"
```

### Container not starting?
```bash
# Check logs
docker-compose logs app

# Rebuild without cache
docker-compose build --no-cache
```

### Database connection issues?
```bash
# Verify MongoDB is running
docker-compose ps

# Check MongoDB logs
docker-compose logs mongodb
```

### Need to access MongoDB directly?
```bash
# Connect to running MongoDB container
docker exec -it book-management-mongodb mongosh -u admin -p admin123
```

---

## Volume Management

MongoDB data is stored in named volume `mongodb_data`. 

Data persists even after `docker-compose down` (सब data safe रहेगा).

To completely remove data:
```bash
docker-compose down -v
```

---

## Development Tips

### Hot Reload नहीं चाहिए तो:
- Dockerfile में spring-boot-devtools add करना पड़ेगा
- या `docker-compose up --build` फिर से चलाना पड़ेगा

### Custom Environment Variables:
Create `.env` file in project root:
```env
MONGODB_PASSWORD=your_password
JWT_SECRET=your_secret
```

Then update `docker-compose.yml`:
```yaml
environment:
  MONGODB_URI: ${MONGODB_URI}
```

---

## Production Deployment Tips

1. Change MongoDB credentials
2. Use strong JWT_SECRET
3. Set proper ADMIN credentials
4. Use external MongoDB Atlas (commented URI में वो है)
5. Add more health checks
6. Use Docker secrets for sensitive data
7. Set resource limits in docker-compose.yml

---

## API Testing

### Register User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "user@example.com",
    "password": "User@123"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@yopmail.com",
    "password": "Admin@123"
  }'
```

### Get Books (with token)
```bash
curl -X GET http://localhost:8080/api/books \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

---

**Happy Dockerizing! 🐳**
