package com.albert.commerce.adapter.in.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenRequest(
        @JsonProperty(value = "grant_type") String grantType,
        String code,
        @JsonProperty(value = "redirect_uri") String redirectUri,
        @JsonProperty(value = "client_id") String clientId,
        @JsonProperty(value = "client_secret") String clientSecret) {

}
