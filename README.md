# Toy Project

## 프로젝트 구성

- API Server
- Authorization Server

## 실행 방법

1. docker를 실행한다
    - `docker-compose -f docker.yaml up -d`
2. Authorization Server를 생행한다
3. http://localhost:9001/
   접근하여 [Keycloak 를 설정](https://github.com/AlbertImKr/spring-boot-commerce-project/wiki/KeycloakSettings)
   한다
4. api의 CommerceApiApplication 실행

