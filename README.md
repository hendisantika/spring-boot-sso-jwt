# Spring Boot SSO with JWT

[![Java CI with Maven](https://github.com/hendisantika/spring-boot-sso-jwt/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/spring-boot-sso-jwt/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.2-6DB33F?logo=springboot)
![jjwt](https://img.shields.io/badge/jjwt-0.13.0-blue)

A simple Single Sign-On (SSO) demo built with Spring Boot and JSON Web Tokens (JWT).
An **auth service** issues a signed JWT stored in an HTTP-only cookie, and a
**resource service** checks that cookie before serving protected pages.

## Tech Stack

| Component    | Version                                                |
|--------------|--------------------------------------------------------|
| Java         | 25 (Temurin)                                           |
| Spring Boot  | 4.1.2 (Spring MVC, Jakarta Servlet, Tomcat 11)         |
| JWT          | [jjwt](https://github.com/jwtk/jjwt) 0.13.0 (HS256)    |
| Templates    | FreeMarker                                             |
| Build        | Maven (multi-module)                                   |
| CI           | GitHub Actions                                         |
| Dependencies | Dependabot (Maven + GitHub Actions)                    |

## Project Structure

```
spring-boot-sso-jwt
├── pom.xml                 # parent / aggregator pom (Spring Boot parent, Java 25, jjwt version)
├── sso-jwt-auth            # Auth service (login page, issues JWT)        → port 8080
└── sso-jwt-resource        # Resource service (protected page, JWT filter) → port 8081
```

## How It Works

```
Browser                    sso-jwt-resource (8081)            sso-jwt-auth (8080)
   │  GET /protected-resource      │                                 │
   │──────────────────────────────▶│  no JWT-TOKEN cookie            │
   │◀────── 302 /login?redirect=… ─│                                 │
   │  GET /login?redirect=…                                          │
   │────────────────────────────────────────────────────────────────▶│
   │  POST /login (username, password)                               │
   │────────────────────────────────────────────────────────────────▶│ validate credentials,
   │◀──────── 302 redirect + Set-Cookie: JWT-TOKEN (HttpOnly) ───────│ sign JWT (HS256)
   │  GET /protected-resource (cookie)                               │
   │──────────────────────────────▶│  JwtFilter verifies signature   │
   │◀────────── 200 "Hello, <user>"│                                 │
```

1. `JwtFilter` in the resource service guards `/protected-resource`. Without a valid
   `JWT-TOKEN` cookie the user is redirected to the auth service.
2. `LoginController` in the auth service checks the credentials, signs a JWT whose
   subject is the username, and stores it in an HTTP-only cookie for `localhost`.
3. Both services share the same signing key, so the resource service can verify the token.
4. `/logout` on the resource service clears the cookie.

## Requirements

- JDK 25
- Maven 3.9+

## Build & Test

```bash
mvn clean verify
```

## Run

Start both services in separate terminals:

```bash
# Auth service → http://localhost:8080
mvn -pl sso-jwt-auth spring-boot:run

# Resource service → http://localhost:8081
mvn -pl sso-jwt-resource spring-boot:run
```

Or run the packaged JARs:

```bash
java -jar sso-jwt-auth/target/sso-jwt-auth-0.0.1-SNAPSHOT.jar
java -jar sso-jwt-resource/target/sso-jwt-resource-0.0.1-SNAPSHOT.jar
```

Then open <http://localhost:8081/protected-resource> and log in with one of the demo accounts:

| Username     | Password     |
|--------------|--------------|
| `hellosso`   | `hellosso`   |
| `hellokoding`| `hellokoding`|

## Configuration

| Module             | Property        | Default                         | Description                       |
|--------------------|-----------------|---------------------------------|-----------------------------------|
| `sso-jwt-auth`     | `server.port`   | `8080`                          | Auth service port                 |
| `sso-jwt-resource` | `server.port`   | `8081`                          | Resource service port             |
| `sso-jwt-resource` | `services.auth` | `http://localhost:8080/login`   | Login URL to redirect to          |

Example with custom ports:

```bash
java -jar sso-jwt-auth/target/sso-jwt-auth-0.0.1-SNAPSHOT.jar --server.port=18080
java -jar sso-jwt-resource/target/sso-jwt-resource-0.0.1-SNAPSHOT.jar \
  --server.port=18081 --services.auth=http://localhost:18080/login
```

> **Note:** This is a demo. Credentials are in memory and the HS256 signing key is
> hard-coded (it must be at least 256 bits). Use a proper user store, an externalized
> secret and `Secure` cookies over HTTPS for anything real.

## CI & Dependency Updates

- **GitHub Actions** (`.github/workflows/maven.yml`) builds and tests the whole project
  on JDK 25 for every push / pull request to `master` and uploads the JARs as artifacts.
- **Dependabot** (`.github/dependabot.yml`) checks Maven dependencies daily for the root
  pom and both modules (grouped: Spring Boot, jjwt, other minor/patch) and GitHub Actions
  weekly.

## Author

**Hendi Santika**

- Email: hendisantika@gmail.com
- Telegram: [@hendisantika34](https://t.me/hendisantika34)
- Link: [s.id/hendisantika](https://s.id/hendisantika)
