package com.albert.commerce.adapter.in.utils;

public class AuthAccessTokenRequest {

    private String code;
    private String client_id;
    private String client_secret;

    public AuthAccessTokenRequest(String code, String client_id, String client_secret) {
        this.code = code;
        this.client_id = client_id;
        this.client_secret = client_secret;
    }

    public AuthAccessTokenRequest() {
    }

    public String getCode() {
        return code;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }
}
