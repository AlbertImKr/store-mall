package com.albert.commerce.adapter.in.utils;


public class AuthProfileResponse {

    private String email;
    private Integer age;

    public AuthProfileResponse() {
    }

    public AuthProfileResponse(String email, Integer age) {
        this.email = email;
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return email;
    }

    public Integer getAge() {
        return age;
    }
}
