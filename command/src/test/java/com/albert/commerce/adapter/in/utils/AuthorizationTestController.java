package com.albert.commerce.adapter.in.utils;

import com.albert.commerce.adapter.in.web.security.SessionStorage;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.RSAKey.Builder;
import java.net.URI;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthorizationTestController {

    @Autowired
    JwtGenerator jwtGenerator;
    @Autowired
    SessionStorage sessionStorage;

    @PostMapping("/test-client/token")
    public ResponseEntity<?> token(@RequestParam(required = false) TokenRequest tokenRequest) {
        Map<String, String> tokenResponse = new HashMap<>();
        tokenResponse.put("access_token", jwtGenerator.generateToken("dummyAccessToken"));
        tokenResponse.put("refresh_token", jwtGenerator.generateToken("refreshToken"));
        if (tokenRequest == null) {
            return ResponseEntity.ok(tokenResponse);
        }
        tokenResponse.put("token_type", "bearer");
        tokenResponse.put("expires_in", "3600");
        tokenResponse.put("scope", "read write");
        String code = tokenRequest.code();
        String nonce = sessionStorage.retrieve(code);
        String idToken = jwtGenerator.generateToken("id_token", nonce);
        tokenResponse.put("id_token", idToken);
        sessionStorage.delete(code);
        return ResponseEntity.ok(tokenResponse);
    }


    @GetMapping("/test-client/user")
    public ResponseEntity<AuthProfileResponse> user(@RequestHeader("Authorization") String authorization) {
        String accessToken = authorization.split(" ")[1];
        GithubResponses githubResponse = GithubResponses.findByToken(accessToken);
        AuthProfileResponse response = new AuthProfileResponse(githubResponse.getEmail(), githubResponse.getAge());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test-client/authorization")
    public ResponseEntity<?> authorize(@RequestParam("response_type") String responseType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("scope") String scope,
            @RequestParam("state") String state,
            @RequestParam(value = "nonce", required = false) String nonce) {

        String code = "dummyAuthorizationCode";
        System.out.println(nonce);
        sessionStorage.save(code, nonce);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUri + "?code=" + code + "&state=" + state));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }


    @GetMapping("/test-client/jwks")
    public ResponseEntity<?> jwks() {
        RSAPublicKey publicKey = jwtGenerator.getPublicKey();

        RSAKey rsaKey = new Builder(publicKey)
                .keyID("refreshToken")
                .build();

        JWKSet jwkSet = new JWKSet(rsaKey);

        return ResponseEntity.ok(jwkSet.toJSONObject(true));
    }
}
