# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**故事森林 (Story Forest)** - 面向 3-10 岁儿童的互动式故事阅读小程序

- **目标用户**: 3-6 岁（听故事）、7-10 岁（自主阅读）、家长（控制/记录）
- **核心功能**: 故事库、互动答题、角色扮演配音、家长控制、成长体系
- **开发阶段**: v0.8 - 前端功能基本完成，待后端 API 对接
- **代码仓库**: 前后端分离，前端为 uni-app 多端应用，后端为 Spring Boot REST API

## Quick Start

### Frontend Development
```bash
cd frontend

# Install dependencies
npm install

# Development mode - WeChat Mini Program
npm run dev:mp-weixin

# Development mode - H5 (Material Design)
npm run dev:h5

# Build production - WeChat Mini Program
npm run build:mp-weixin

# Build production - H5
npm run build:h5
```

### Backend Development
```bash
cd backend

# Start with Docker Compose (MySQL + Redis + App)
docker-compose up -d

# Local development with Maven
mvn spring-boot:run

# Run tests
mvn test

# Build JAR
mvn clean package
```

## Tech Stack

### Frontend
- **Framework**: uni-app 3.x (Vue 3 Composition API)
- **UI Library**: uView Plus (小程序/App), Custom Material Design 3 (H5)
- **State Management**: Pinia 2.0.33
- **Build Tool**: Vite 4.5 with uni-app plugin
- **Preprocessor**: Sass 1.62 with Material 3 design tokens
- **Target Platforms**: 微信小程序 / H5 / App (uni-app multi-platform)

### Backend
- **Java**: JDK 17 (Spring Boot 3.2.0)
- **API Framework**: Spring Boot Web, Spring Security, Spring Data JPA
- **Database**: MySQL 8.0 (JPA), Redis 7.0 (cache/session)
- **Authentication**: JWT with Spring Security
- **API Documentation**: SpringDoc OpenAPI 3.0 (Swagger UI)
- **Build Tool**: Maven 3.6+
- **Container**: Docker with docker-compose

### Planned AI Service
- **Framework**: FastAPI (Python + LangChain)
- **Integration**: REST API calls from backend service

## Architecture

### High-Level System Architecture
```
┌─────────────────────────────────────────────────────────┐
│  前端层：微信小程序 / H5 / App (uni-app)                 │
│  • Vue 3 Composition API + Pinia                        │
│  • Platform-specific UI (uView Plus / MD3)              │
│  • API calls to backend via axios wrapper               │
└─────────────────────────────────────────────────────────┘
                            │ HTTP/HTTPS + WebSocket
                            ▼
┌─────────────────────────────────────────────────────────┐
│  网关层：Nginx + WebSocket Proxy (production)           │
│  • Request routing & load balancing                     │
│  • WebSocket connections for real-time features         │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│  服务层：Spring Boot REST API                           │
│  • Controllers → Services → Repositories pattern        │
│  • JWT authentication & Spring Security                 │
│  • MySQL (JPA) + Redis (caching/session)                │
│  • OpenAPI documentation at /api/swagger-ui.html        │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│  数据层：MySQL 8.0 / Redis 7.0 / 腾讯云 COS              │
│  • MySQL: User data, stories, progress, etc.            │
│  • Redis: Session cache, rate limiting                  │
│  • COS: Media storage (audio, images)                   │
└─────────────────────────────────────────────────────────┘
```

### Frontend Architecture
- **Component Structure**: Custom MD3 components (`components/md3-*/`) + business components
- **State Management**: Pinia stores (`store/`) for user, story, and audio state
- **Routing**: uni-app pages auto-registered via `pages.json`
- **Styling**: SCSS with Material 3 design tokens (`styles/md3-theme.scss`)
- **API Layer**: Axios wrapper (`utils/request.js`) with JWT interception
- **Platform Adaptation**: Conditional compilation (`#ifdef MP-WEIXIN`) for platform-specific code

### Backend Architecture
- **Layered Architecture**: Controllers → Services → Repositories → Entities
- **Package Structure**: `com.storyforest.{config,controller,dto,entity,exception,repository,security,service,util}`
- **Security**: Spring Security with JWT authentication filter
- **Data Access**: Spring Data JPA repositories with MySQL
- **Caching**: Redis integration via Spring Data Redis
- **API Design**: RESTful endpoints following OpenAPI 3.0 specification

## Project Structure

```
app/
├── frontend/                    # uni-app frontend
│   ├── src/
│   │   ├── pages/              # 18 pages (auto-registered via pages.json)
│   │   ├── components/         # Reusable components
│   │   │   ├── md3-button/     # Material 3 button
│   │   │   ├── md3-input/      # Material 3 input
│   │   │   ├── md3-card/       # Material 3 card
│   │   │   ├── StoryCard.vue   # Story card component
│   │   │   ├── ChapterItem.vue # Chapter item component
│   │   │   └── AudioPlayer.vue # Audio player component
│   │   ├── styles/             # Global styles
│   │   │   ├── md3-theme.scss  # Material 3 design tokens
│   │   │   └── md3-global.scss # Material 3 global styles
│   │   ├── store/              # Pinia stores
│   │   │   ├── user.js         # User authentication state
│   │   │   ├── story.js        # Story data and favorites
│   │   │   └── audio.js        # Audio playback state
│   │   ├── api/                # API service modules
│   │   │   └── auth.js         # Authentication API
│   │   ├── utils/              # Utilities
│   │   │   ├── request.js      # HTTP request wrapper (axios)
│   │   │   └── storage.js      # Local storage utilities
│   │   ├── static/             # Static assets
│   │   ├── App.vue             # Root component
│   │   ├── main.js             # Application entry
│   │   ├── pages.json          # Page routing configuration
│   │   ├── manifest.json       # App configuration
│   │   └── uni.scss            # Global uni-app styles
│   ├── vite.config.js          # Vite configuration with uni-app plugin
│   ├── package.json            # npm dependencies and scripts
│   └── index.html              # HTML entry point
├── backend/                    # Spring Boot backend
│   ├── src/main/java/com/storyforest/
│   │   ├── config/             # Configuration classes
│   │   ├── controller/         # REST controllers
│   │   ├── dto/                # Data transfer objects
│   │   ├── entity/             # JPA entities
│   │   ├── exception/          # Custom exceptions
│   │   ├── repository/         # JPA repositories
│   │   ├── security/           # Security configuration
│   │   ├── service/            # Business logic services
│   │   └── util/               # Utility classes
│   ├── src/main/resources/
│   │   ├── application.yml     # Spring configuration
│   │   └── db/init.sql         # Database initialization
│   ├── src/test/java/          # Unit tests (JUnit 5)
│   ├── pom.xml                 # Maven configuration
│   ├── Dockerfile              # Docker image definition
│   ├── docker-compose.yml      # Local development with MySQL+Redis
│   └── openapi.yaml            # OpenAPI specification
└── docs/                       # Product documentation
    ├── 原型设计.md             # Wireframes and interaction specs
    ├── 用户流程.md             # User journey and state machine
    └── UI 组件库规范.md        # UI component library specs
```

## Common Development Tasks

### Frontend Tasks
```bash
# Install/update dependencies
cd frontend && npm install

# Start H5 development server (http://localhost:5173)
npm run dev:h5

# Start WeChat Mini Program dev (requires微信开发者工具)
npm run dev:mp-weixin

# Build for production
npm run build:h5           # H5 build in dist/build/h5
npm run build:mp-weixin    # Mini program build in dist/build/mp-weixin

# Check package.json for other platform targets (app, etc.)
```

### Backend Tasks
```bash
# Start with Docker Compose (MySQL + Redis + App)
docker-compose up -d
# Access: http://localhost:8080/api/swagger-ui.html

# Start locally (requires MySQL & Redis running)
mvn spring-boot:run

# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=AuthServiceTest

# Build executable JAR
mvn clean package
# Output: target/story-forest-backend-1.0.0-SNAPSHOT.jar

# Run JAR
java -jar target/story-forest-backend-1.0.0-SNAPSHOT.jar

# Build Docker image
docker build -t story-forest-backend .
```

### Database Management
```bash
# Initialize database (from backend directory)
mysql -u root -p < src/main/resources/db/init.sql

# Start Redis locally
redis-server
```

## Key Configuration Files

### Frontend Configuration
- `frontend/vite.config.js` - Vite + uni-app configuration with SCSS preprocessing
- `frontend/src/pages.json` - Page routing and uView Plus auto-import configuration
- `frontend/src/manifest.json` - App metadata and platform settings
- `frontend/src/styles/md3-theme.scss` - Material 3 design tokens (colors, typography)

### Backend Configuration
- `backend/src/main/resources/application.yml` - Spring Boot configuration (DB, Redis, JWT)
- `backend/pom.xml` - Maven dependencies and build configuration
- `backend/docker-compose.yml` - Local development environment (MySQL 8.0 + Redis 7.0)
- `backend/openapi.yaml` - API specification (OpenAPI 3.0)

## API Integration

### Frontend API Layer
- Base URL: Configured in `frontend/src/utils/request.js` (currently `https://api.example.com`)
- Authentication: JWT Bearer token automatically added to requests
- Response format: `{ "code": 0, "message": "success", "data": {} }`
- Error handling: Global interceptor in request.js

### Backend API Endpoints
- Swagger UI: http://localhost:8080/api/swagger-ui.html (when running)
- API Base Path: `/api` (configured in Spring Boot)
- Authentication: JWT via `Authorization: Bearer <token>` header
- Current modules: Auth, User, Story, Quiz, Dubbing, Parent controls

## Design System

### Material Design 3 Implementation
- **Design Tokens**: Defined in `frontend/src/styles/md3-theme.scss`
- **Color Palette**: Primary (#2D6A4F), Secondary (#FF8C42), Tertiary (#4ECDC4), Surface (#FFFBF0)
- **Components**: Custom MD3 Button, Input, Card with variants
- **Usage**: Import `md3-global.scss` in main.js for global styles

### Component Variants
- **Md3Button**: `variant` (filled|outlined|text|contained), `size` (small|medium|large)
- **Md3Input**: `variant` (outlined|filled), `label`, `error`, `counter`, `clearable`
- **Md3Card**: `variant` (outlined|elevated|filled|tonal), `clickable`, `media` slot, `actions` slot

## Development Notes

### uni-app Specific Considerations
- Pages are auto-registered via `pages.json` (no manual import needed)
- Use `uni.` prefix for platform APIs (e.g., `uni.navigateTo`, `uni.request`)
- Platform-specific code: `#ifdef MP-WEIXIN` / `#ifndef MP-WEIXIN`
- uView Plus components auto-imported via `pages.json` configuration
- H5 build uses custom MD3 components; mini-program uses uView Plus

### Code Style Guidelines
- Vue 3 Composition API preferred over Options API
- SCSS with Material 3 design tokens for styling
- Component names: PascalCase (e.g., `StoryCard.vue`)
- File naming: kebab-case for non-components
- Alias `@` resolves to `frontend/src` (configured in vite.config.js)

### State Management (Pinia)
- **useUserStore**: Authentication state, user info, token, age group
- **useStoryStore**: Current story, favorites, reading history
- **useAudioStore**: Playback state, current time, progress, timer

## Testing Strategy

### Backend Testing
- **Framework**: JUnit 5 + Spring Boot Test
- **Test Location**: `backend/src/test/java/`
- **Current Tests**: `JwtUtilTest`, `AuthServiceTest`
- **Run Tests**: `mvn test` or `mvn test -Dtest=ClassName`

### Frontend Testing
- Testing framework not yet configured (planned)
- Manual testing via development builds recommended

## Current Status & Next Steps

### Completed (v0.8)
- ✅ Frontend: 18 pages with complete UI
- ✅ Frontend: Pinia state management (3 stores)
- ✅ Frontend: Material Design 3 component library
- ✅ Frontend: Audio playback functionality
- ✅ Frontend: Login/Register flow
- ✅ Backend: Spring Boot REST API structure
- ✅ Backend: JWT authentication implementation
- ✅ Backend: MySQL + Redis integration
- ✅ Backend: Docker Compose development environment
- ✅ Backend: OpenAPI documentation

### Next Steps
- [ ] Backend: Complete remaining service implementations
- [ ] Integration: Update frontend `BASE_URL` to point to local backend
- [ ] Integration: Connect frontend API calls to backend endpoints
- [ ] WeChat: Implement WeChat login integration
- [ ] Media: Integrate Lottie animations for story reading
- [ ] Testing: Add comprehensive unit/integration tests
- [ ] Deployment: Production deployment configuration