# Toy Project

[![Coverage Status](https://coveralls.io/repos/github/AlbertImKr/spring-boot-commerce-project/badge.svg)](https://coveralls.io/github/AlbertImKr/spring-boot-commerce-project)

## 프로젝트 구성

- API Server
- Authorization Server

## 실행 방법

1. docker를 실행한다
   - `docker-compose -f docker.yaml up -d`
2. Authorization Server를 실행한다
3. http://localhost:9001/
   접근하여 [Keycloak 를 설정](https://github.com/AlbertImKr/spring-boot-commerce-project/wiki/KeycloakSettings)
   한다
4. api의 CommerceApiApplication 실행

## 사용 기술

- <img src="https://spring.io/img/projects/spring-boot.svg" width="12" height="12" alt="SpringBoot"> Spring Boot
- <img src="https://spring.io/img/projects/spring-framework.svg?v=2" width="12" height="12" alt="SpringWeb"> Spring Web
- <img src="https://spring.io/img/projects/spring-framework.svg?v=2" width="12" height="12" alt="SpringValidation"> Spring Validation
- <img src="https://spring.io/img/projects/spring-security.svg" width="12" height="12" alt="SpringSecurity"> Spring Security
- <img src="https://spring.io/img/projects/spring-data.svg" width="12" height="12" alt="SpringDataJpa"> Spring Data Jpa
- <img src="https://spring.io/img/projects/spring-restdocs.png" width="12" height="12" alt="SpringRESTDOCS"> Spring REST Docs
- <img src="https://spring.io/img/projects/spring-hateoas.svg?v=2" width="12" height="12" alt="SpringHATEOAS"> Spring HATEOAS
- <img src="https://spring.io/img/projects/spring-security.svg" width="12" height="12" alt="SpringAuthorizationServer"> Spring Authorization Server
- <img src="https://raw.githubusercontent.com/querydsl/querydsl.github.io/master/ico/favicon.ico" width="12" height="12" alt="QueryDsl"> QueryDSL

## 도메인 관계

![erd](https://user-images.githubusercontent.com/99056666/240258445-fda99f99-7041-4ed2-a972-a1569a31c4cb.png)


## Cloud 구조 설계
![image](https://github.com/AlbertImKr/spring-boot-commerce-project/assets/99056666/f3e9727f-a386-454f-a7a6-993354db7d02)

