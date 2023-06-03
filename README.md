# Toy Project

## 프로젝트 구성

- API Server
- Authorization Server

## 실행 방법

1. docker-compose -f docker.yaml up -d
2. Authorization Server를 생행한다
3. http://localhost:9001/ 접근하여 Keycloak 를 설정한다

````
   1. AdminId: admin
   2. AdminPassword: password
   3. Realm name를 kc-oauth2로 설정하고 create Realm 한다
   4. Create Client 
      1. kc-oauth2 -> Clients -> Create client
      2. Client ID=keycloak -> Next
      3. Clinet authentication -> On -> Next
      4. Valid redirect URIs -> http://localhost:8080/login/oauth2/code/keycloak -> Save
      5. Web origins -> http://127.0.0.1:8080/*
      6. Clients -> keycloak
      7. Credentials -> Client secret 복사 -> api/src/main/resources/application-oauth2.yml -> keycloak의 
         client-secret에 붙여 놓는다
      8. Realm settings -> Login
         - User registration -> On
         - Forget password -> On
         - Remember me -> On
         - Email as username -> On
         - Login with email -> On
         - Save
````

4. api의 CommerceApiApplication 실행

