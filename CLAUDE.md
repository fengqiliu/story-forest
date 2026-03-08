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
npm install

# Development modes
npm run dev:mp-weixin  # WeChat Mini Program
npm run dev:h5         # H5 (Material Design)
npm run dev:app        # App

# Production builds
npm run build:mp-weixin
npm run build:h5
npm run build:app
```

### Backend Development

**Option 1: Quick local dev (H2 + Embedded Redis, no Docker required)**
```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=dev
# API: http://localhost:8080/api/swagger-ui.html
# H2 Console: http://localhost:8080/api/h2-console (JDBC URL: jdbc:h2:mem:story_forest)
```

**Option 2: Full stack with Docker Compose (MySQL + Redis)**
```bash
cd backend
docker-compose up -d
mvn spring-boot:run
```

**Testing & Building**
```bash
mvn test                          # Run all tests
mvn test -Dtest=ClassName        # Run specific test class
mvn clean package                 # Build JAR
```

## Tech Stack

| Layer | Technology |
|-------|------------|
| Frontend | uni-app 3.x (Vue 3), Pinia, uView Plus, Vite 4.5, Sass |
| Backend | Spring Boot 3.2.0, JDK 17, Spring Security, Spring Data JPA |
| Database | MySQL 8.0 (prod), H2 (dev), Redis 7.0 |
| Auth | JWT with Spring Security |
| Docs | SpringDoc OpenAPI 3.0 (Swagger UI) |
| Cloud | Tencent COS (media storage) |

## Architecture

```
Frontend (uni-app) ──HTTP/REST──► Backend (Spring Boot)
     │                                    │
     ├── pages/ (18 pages)               ├── controller/ (REST endpoints)
     ├── components/ (MD3 + business)    ├── service/ (business logic)
     ├── store/ (Pinia: user/story/audio)├── repository/ (Spring Data JPA)
     └── utils/request.js (API layer)    └── entity/ (JPA entities)
```

## Key Configuration Files

| File | Purpose |
|------|---------|
| `frontend/src/pages.json` | Page routing, uView Plus auto-import |
| `frontend/src/utils/request.js` | API base URL, JWT interception |
| `frontend/src/styles/md3-theme.scss` | Material 3 design tokens |
| `backend/src/main/resources/application.yml` | Production config (MySQL, Redis) |
| `backend/src/main/resources/application-dev.yml` | Dev config (H2, Embedded Redis) |
| `backend/docker-compose.yml` | Local MySQL + Redis stack |

## API Integration

### Frontend → Backend Connection
1. Update `frontend/src/utils/request.js` BASE_URL from `https://api.example.com` to `http://localhost:8080/api`
2. Backend CORS allows `localhost:5173` (H5 dev server) and `localhost:3000`

### Backend Endpoints
- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **H2 Console** (dev only): http://localhost:8080/api/h2-console
- **API Base Path**: `/api`
- **Auth Header**: `Authorization: Bearer <JWT_TOKEN>`
- **Response Format**: `{ "code": 0, "message": "success", "data": {} }`

## Design System

Material Design 3 implementation in `frontend/src/styles/`:
- **Primary**: #2D6A4F (森林绿)
- **Secondary**: #FF8C42 (暖橙色)
- **Tertiary**: #4ECDC4 (天空蓝)
- **Surface**: #FFFBF0 (奶油白)

**MD3 Components**: `md3-button`, `md3-input`, `md3-card` with variants (filled/outlined/text/elevated)

## Development Notes

### uni-app Specifics
- Pages auto-registered via `pages.json` (no manual imports)
- Use `uni.` prefix for platform APIs (`uni.navigateTo`, `uni.request`, etc.)
- Platform conditionals: `#ifdef MP-WEIXIN` / `#ifndef MP-WEIXIN`
- H5 uses MD3 components; mini-program uses uView Plus

### Pinia Stores
- **useUserStore**: Auth state, token, age group (`store/user.js`)
- **useStoryStore**: Current story, favorites, history (`store/story.js`)
- **useAudioStore**: Playback state, timer (`store/audio.js`)

### Code Style
- Vue 3 Composition API preferred
- SCSS with Material 3 tokens
- Component files: PascalCase (e.g., `StoryCard.vue`)
- Non-components: kebab-case

## Current Status (v0.8)

### Completed
- Frontend: 18 pages, Pinia stores, MD3 components, audio playback
- Backend: Spring Boot API, JWT auth, MySQL/Redis integration, OpenAPI docs

### Next Steps
- Complete backend service implementations
- Connect frontend API calls to backend
- WeChat login integration
- Lottie animations for story reading
