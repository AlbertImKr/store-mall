//package com.albert.commerce.user.query.application;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.BDDMockito.given;
//
//import com.albert.commerce.user.command.domain.Role;
//import com.albert.commerce.user.command.domain.User;
//import com.albert.commerce.user.command.domain.UserId;
//import com.albert.commerce.user.query.domain.UserQueryDao;
//import java.time.LocalDate;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//class UserQueryServiceTest {
//
//    @InjectMocks
//    private UserQueryDao userQueryService;
//
//    @Mock
//    private UserQueryDao userQueryDao;
//
//    @DisplayName("email를 userQueryDao 전달하고 user를 받아온다")
//    @Test
//    void findByEmail() {
//        String testEmail = "test@email.com";
//        User user = User.builder()
//                .id(UserId.from("testId"))
//                .email(testEmail)
//                .dateOfBirth(LocalDate.now())
//                .role(Role.USER)
//                .phoneNumber("1111111111")
//                .isActive(false)
//                .address("testAddress")
//                .build();
//        given(userQueryDao.findUserProfileByEmail(testEmail)).willReturn(user);
//
//        UserInfoResponse userInfoResponse = userQueryService.findByEmail(testEmail);
//
//        assertThat(userInfoResponse).usingRecursiveComparison()
//                .isEqualTo(UserInfoResponse.from(user));
//    }
//}
