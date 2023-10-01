package com.albert.commerce.adapter.in.web;

import com.albert.commerce.application.service.user.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static com.albert.commerce.adapter.in.web.AcceptanceFixture.TEST_EMAIL;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ContextConfiguration
public abstract class AcceptanceTest {

    @Autowired
    UserService userService;

    @Autowired
    WebApplicationContext webApplicationContext;


    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        createdTestUser();
    }

    private void createdTestUser() {
        userService.createByEmail(TEST_EMAIL);
    }
}
