package com.albert.commerce.user.query.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.albert.commerce.config.TestConfig;
import com.albert.commerce.user.command.domain.Role;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.command.domain.UserCommandDAO;
import com.albert.commerce.user.command.domain.UserId;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserQueryDaoTest {

    @Autowired
    UserQueryDao userQueryDao;

    @Autowired
    UserCommandDAO userCommandDAO;

    @Test
    void findUserProfileByEmail() {
        String testNickName = "testNickName";
        String testEmail = "user@email.com";
        LocalDate testDateOfBirth = LocalDate.now();
        Role testRole = Role.USER;
        String testPhoneNumber = "100-0000-0000";
        String testAddress = "testAddress";
        boolean testIsActive = false;
        UserId testId = UserId.from("testId");
        User user = User.builder()
                .id(testId)
                .nickname(testNickName)
                .email(testEmail)
                .role(testRole)
                .dateOfBirth(testDateOfBirth)
                .phoneNumber(testPhoneNumber)
                .address(testAddress)
                .isActive(testIsActive)
                .build();
        User savedUser = userCommandDAO.save(user);

        User findeduser = userQueryDao.findUserProfileByEmail(testEmail);

        assertThat(findeduser).usingRecursiveComparison().isEqualTo(savedUser);
    }
}
