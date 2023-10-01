package com.albert.commerce.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.albert.commerce.domain.DomainFixture;
import com.albert.commerce.domain.event.DomainEvent;
import com.albert.commerce.domain.event.Events;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @DisplayName("Test user create by email test")
    @Test
    void createByEmail() {
        // given
        UserId userId = DomainFixture.getUserId();
        String userEmail = DomainFixture.USER_EMAIL;
        LocalDateTime createdTime = DomainFixture.getCreatedTime();

        // when
        User user = User.createByEmail(userId, userEmail, createdTime);

        // then
        List<DomainEvent> events = Events.getEvents();
        DomainEvent domainEvent = events.get(0);
        assertAll(
                () -> assertThat(events).hasSize(1),
                () -> assertThat(domainEvent.getClass()).isEqualTo(UserRegisteredEvent.class),
                () -> assertThat((UserRegisteredEvent) domainEvent)
                        .extracting(UserRegisteredEvent::getUserId)
                        .isEqualTo(userId),
                () -> assertThat((UserRegisteredEvent) domainEvent)
                        .extracting(UserRegisteredEvent::getEmail)
                        .isEqualTo(userEmail),
                () -> assertThat((UserRegisteredEvent) domainEvent)
                        .extracting(UserRegisteredEvent::getCreatedTime)
                        .isEqualTo(createdTime),
                () -> assertThat((UserRegisteredEvent) domainEvent)
                        .extracting(UserRegisteredEvent::isActive)
                        .isEqualTo(false),
                () -> assertThat((UserRegisteredEvent) domainEvent)
                        .extracting(UserRegisteredEvent::getRole)
                        .isEqualTo(Role.USER),
                () -> assertThat((UserRegisteredEvent) domainEvent)
                        .extracting(UserRegisteredEvent::getNickname)
                        .isEqualTo(userEmail),
                () -> assertThat((UserRegisteredEvent) domainEvent)
                        .extracting(UserRegisteredEvent::getDateOfBirth)
                        .isNull(),
                () -> assertThat((UserRegisteredEvent) domainEvent)
                        .extracting(UserRegisteredEvent::getPhoneNumber)
                        .isNull(),
                () -> assertThat((UserRegisteredEvent) domainEvent)
                        .extracting(UserRegisteredEvent::getAddress)
                        .isNull(),
                () -> assertThat((UserRegisteredEvent) domainEvent)
                        .extracting(UserRegisteredEvent::getUpdatedTime)
                        .isNull()
        );
    }

    @DisplayName("Test update user test")
    @Test
    void update() {
        // given
        UserId userId = DomainFixture.getUserId();
        String userEmail = DomainFixture.USER_EMAIL;
        LocalDateTime createdTime = DomainFixture.getCreatedTime();
        User user = User.createByEmail(userId, userEmail, createdTime);
        Events.clear();

        String updatedAddress = DomainFixture.getUpdatedAddress();
        String updatedNickname = DomainFixture.getUpdatedNickname();
        LocalDate updatedDateOfBirth = DomainFixture.getUpdatedDateOfBirth();
        String updatedPhoneNumber = DomainFixture.getUpdatedPhoneNumber();
        LocalDateTime updatedTime = DomainFixture.getUpdatedTime();

        // when
        user.update(updatedAddress, updatedNickname, updatedDateOfBirth, updatedPhoneNumber, updatedTime);

        // then
        List<DomainEvent> events = Events.getEvents();
        DomainEvent domainEvent = events.get(0);
        assertAll(
                () -> assertThat(events).hasSize(1),
                () -> assertThat(domainEvent.getClass()).isEqualTo(UserUpdatedEvent.class),
                () -> assertThat((UserUpdatedEvent) domainEvent)
                        .extracting(UserUpdatedEvent::getAddress)
                        .isEqualTo(updatedAddress),
                () -> assertThat((UserUpdatedEvent) domainEvent)
                        .extracting(UserUpdatedEvent::getNickname)
                        .isEqualTo(updatedNickname),
                () -> assertThat((UserUpdatedEvent) domainEvent)
                        .extracting(UserUpdatedEvent::getDateOfBirth)
                        .isEqualTo(updatedDateOfBirth),
                () -> assertThat((UserUpdatedEvent) domainEvent)
                        .extracting(UserUpdatedEvent::getPhoneNumber)
                        .isEqualTo(updatedPhoneNumber),
                () -> assertThat((UserUpdatedEvent) domainEvent)
                        .extracting(UserUpdatedEvent::getUpdatedTime)
                        .isEqualTo(updatedTime),
                () -> assertThat((UserUpdatedEvent) domainEvent)
                        .extracting(UserUpdatedEvent::getUserId)
                        .isEqualTo(userId)
        );
    }
}
