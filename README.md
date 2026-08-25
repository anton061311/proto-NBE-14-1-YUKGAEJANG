# NBE-14-1-YUKGAEJANG
1st-project-backend-devcourse

# ☕ Grids & Circles — 카페 메뉴 관리 서비스

로컬 카페 'Grids & Circles'의 원두 패키지 온라인 주문을 처리하는 백엔드 API 서비스입니다.
Spring Boot 기반 REST API와 React 클라이언트(고객용/관리자용)로 구성되어 있으며,
매일 전날 14:00 ~ 당일 14:00 사이의 주문을 모아 다음 날 배송하는 배치형 주문 처리 방식을 핵심 로직으로 합니다.

---

## 목차

- [프로젝트 개요](#프로젝트-개요)
- [기술 스택](#기술-스택)
- [핵심 비즈니스 로직](#핵심-비즈니스-로직)
- [ERD](#erd)
- [API 명세](#api-명세)
- [프로젝트 구조](#프로젝트-구조)
- [브랜치 전략 및 커밋 컨벤션](#브랜치-전략-및-커밋-컨벤션)
- [로컬 실행 방법](#로컬-실행-방법)
- [팀원 및 역할](#팀원-및-역할)

---

## 프로젝트 개요

| 항목 | 내용 |
|---|---|
| 프로젝트명 | 카페 메뉴 관리 서비스 (NBE-14-1-YUKGAEJANG) |
| 기간 | 2026.08.24 (월) 09:00 ~ 2026.08.31 (월) 18:00 |
| 발표 | 2026.08.31 (월) 14:00 ~ 17:00 |
| 목표 | Spring Boot 기반 RESTful CRUD API 설계·구현, 팀 협업(역할 분담/브랜치 전략/코드 리뷰) 경험 |
| 팀 구성 | 5인 (Backend 3, Frontend 2 — 역할표는 하단 참고) |

### 서비스 시나리오

- 회원가입 없이 **이메일**로 고객을 식별합니다.
- 고객은 하루 중 여러 번 주문할 수 있으며, 같은 고객의 주문은 **하나의 주문으로 병합**되어 다음 날 배송됩니다.
- 마감 기준: **전날 14:00 ~ 당일 14:00**. 이 시간 이후 주문은 다음 날 배송으로 안내됩니다.

---

## 기술 스택

| 구분 | 기술 |
|---|---|
| Backend | Java 25, Spring Boot |
| Build Tool | Gradle (Kotlin DSL) |
| Packaging | Jar |
| Configuration | YAML (`application.yml`) |
| Database | MySQL (운영) / H2 (로컬 개발) |
| Frontend | React (Vite) |
| 협업 도구 | GitHub, Notion(API 명세/DTO 문서화), Postman |
| 문서화 | Swagger / Postman Collection |

### 주요 라이브러리 (Gradle Dependencies)

| 라이브러리 | 용도 |
|---|---|
| Spring Web | REST API 구현 |
| Spring Data JPA | ORM 기반 DB 연동 |
| MySQL Driver | 운영 DB 연결 |
| H2 Database | 로컬 개발/테스트용 인메모리 DB |
| Lombok | 보일러플레이트 코드(getter/builder 등) 축소 |
| Validation | 요청 값 검증 (`@Valid`) |

---

## 핵심 비즈니스 로직

### 1. 주문 병합 (Order Merge)

한 고객이 같은 마감 창(전날 14:00~당일 14:00) 안에서 여러 번 주문을 요청하면, 새 `Order`를 만들지 않고 **기존 Order에 상품을 추가**합니다.

```
POST /api/v1/orders 요청 시:
1. email + 마감 창(windowStart~windowEnd) 기준으로 기존 Order 조회
2. 존재하면 → 해당 Order에 OrderItem 추가 (동일 상품이면 quantity 합산)
3. 존재하지 않으면 → 신규 Order 생성 후 OrderItem 추가
```

> ⚠️ 동시에 같은 고객이 짧은 간격으로 두 번 요청할 경우 중복 Order가 생성될 수 있어, DB 유니크 제약 또는 락(`@Lock`) 적용을 고려합니다.

### 2. 주문 항목 삭제 제한

고객은 **마감 전까지만** 담은 상품 항목을 삭제할 수 있습니다. 마감 시각이 지난 요청은 예외로 처리하여 거부합니다.

### 3. 가격 정책

원두 가격은 프로젝트 기간 중 변동이 없다고 가정하여, 주문 시점 가격을 별도로 스냅샷 저장하지 않고 `Product.price`를 그대로 참조합니다.

---

## ERD

**Product** (상품)

| 필드 | 타입 | NOT NULL |
|---|---|---|
| id (PK) | BIGINT | ✅ |
| name | VARCHAR(255) | ✅ |
| price | INT | ✅ |
| image_url | VARCHAR(255) | ❌ |

**Orders** (주문 — 마감 창 단위 배치)

| 필드 | 타입 | NOT NULL |
|---|---|---|
| id (PK) | BIGINT | ✅ |
| email | VARCHAR(255) | ✅ |
| zip_code | VARCHAR(255) | ✅ |
| address | VARCHAR(255) | ✅ |
| order_date | DATETIME | ✅ |

**OrderItem** (주문 상품 — 주문 1건에 포함된 상품 한 줄)

| 필드 | 타입 | NOT NULL |
|---|---|---|
| id (PK) | BIGINT | ✅ |
| order_id (FK → Orders) | BIGINT | ✅ |
| product_id (FK → Product) | BIGINT | ✅ |
| quantity | INT | ✅ |

관계: `Orders 1 : N OrderItem N : 1 Product`

---

## API 명세

Base URL: `/api/v1`

### 상품 (Product)

| Method | URI | 설명 |
|---|---|---|
| POST | `/products` | 상품 등록 |
| GET | `/products` | 상품 목록 조회 (이름순/가격순 정렬, 페이지네이션) |
| GET | `/products/{id}` | 상품 단건 조회 |
| PUT | `/products/{id}` | 상품 수정 |
| DELETE | `/products/{id}` | 상품 삭제 |

### 주문 (Order)

| Method | URI | 설명 |
|---|---|---|
| POST | `/orders` | 주문 등록 / 당일 추가 주문 병합 |
| GET | `/orders` | 주문 목록 조회 (관리자, 페이지네이션) |
| DELETE | `/orders/{orderId}/items/{itemId}` | 주문 항목 삭제 (마감 전까지만 허용) |

> 상세 요청/응답 DTO 스펙은 [Notion API 명세서](https://app.notion.com/p/acb15a01205483acb54f0127f1fcaaa2)를 참고하세요.

---

## 프로젝트 구조

```
NBE-14-1-YUKGAEJANG/
├── backend/
│   └── src/main/java/com/yukgaejang/cafemenu/
│       ├── domain/
│       │   └── post/
│       │       ├── order/
│       │       │   ├── controller/
│       │       │   ├── service/
│       │       │   ├── repository/
│       │       │   ├── dto/
│       │       │   └── entity/
│       │       └── product/
│       │           ├── controller/
│       │           ├── service/
│       │           ├── repository/
│       │           ├── dto/
│       │           └── entity/
│       ├── global/
│       │   └── exceptionHandler/
│       │       ├── ApiException.java
│       │       └── GlobalExceptionHandler.java
│       └── BackendApplication.java
├── front/                  # React (고객용/관리자용)
└── README.md
```

---

## 브랜치 전략 및 커밋 컨벤션

```
main        배포용 (최종 완성본만 머지)
dev         통합 브랜치
feature/*   기능 단위 작업 브랜치 (예: feature/order-create)
```

작업 흐름: `feature/* → dev`로 PR 생성 → 팀원 코드 리뷰 → 머지. `main`은 발표 직전 `dev`를 머지하는 용도로만 사용합니다.

커밋 메시지는 Conventional Commits 스타일을 따릅니다.

| 접두어 | 용도 |
|---|---|
| `feat:` | 기능 추가 |
| `fix:` | 버그 수정 |
| `chore:` | 설정/빌드 관련 |
| `docs:` | 문서 작업 |
| `refactor:` | 리팩토링 |

---

## 로컬 실행 방법

```bash
# 1. 저장소 클론
git clone <repo-url>
cd NBE-14-1-YUKGAEJANG/backend

# 2. 빌드 및 실행
./gradlew bootRun
```

기본 프로필은 로컬 개발용 H2 DB를 사용합니다. MySQL 연동 시 `application.yml`의 `spring.datasource` 설정을 변경하세요.

```bash
# Frontend 실행
cd ../front
npm install
npm run dev
```

---

## 팀원 및 역할

| 담당  | 이슈 |
|-----|---|
| 한종연 | 상품 목록 조회 (정렬/페이지네이션) |
| 송혜민 | 주문 목록 조회 (페이지네이션) + 공통 인프라(예외처리 로직) |
| 이제혁 | 상품 등록 (+이미지) |
| 이태호 | 주문 등록 + 병합 로직 |
| 김영우 | 상품/주문 항목 삭제, 공통 예외 처리, 마감시각 계산 유틸 |

각 담당자는 자신이 맡은 도메인의 Controller–Service–Repository–DTO를 전 구간 책임집니다.
