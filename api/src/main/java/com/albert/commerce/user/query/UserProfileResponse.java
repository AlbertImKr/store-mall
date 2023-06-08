package com.albert.commerce.user.query;

import com.albert.commerce.user.command.domain.UserId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class UserProfileResponse {

    @EmbeddedId
    private UserId id;
    private String nickname;
    private String email;

    public UserProfileResponse() {
    }

}
