package com.albert.commerce.adapter.in.web;

import static com.albert.commerce.adapter.in.web.AcceptanceTestUtils.TEST_USER_EMAIL;

import com.albert.commerce.application.service.user.UserService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public abstract class AcceptanceTest {

    @Autowired
    UserService userService;
    String accessToken;
    @LocalServerPort
    private int port;

    private void createTestUser() {
        userService.createByEmail(TEST_USER_EMAIL);
    }

    private static String getAccessToken() {
        return RestAssured.given().log().all()
                .when().post("http://localhost:8080/test-client/token")
                .then().log().all()
                .extract().jsonPath().getString("access_token");
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        accessToken = getAccessToken();

        // 유저 저장
        createTestUser();
    }
}
