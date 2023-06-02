package com.albert.authorizationserver.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "`authorization`")
public class Authorization {

    @Id
    @Column
    private String id;
    private String registeredClientId;
    private String principalName;
    private String authorizationGrantType;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 1000)
    private String authorizedScopes;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 4000)
    private String attributes;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 500)
    private String state;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 4000)
    private String authorizationCodeValue;
    private Instant authorizationCodeIssuedAt;
    private Instant authorizationCodeExpiresAt;
    private String authorizationCodeMetadata;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 4000)
    private String accessTokenValue;
    private Instant accessTokenIssuedAt;
    private Instant accessTokenExpiresAt;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 2000)
    private String accessTokenMetadata;
    private String accessTokenType;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 1000)
    private String accessTokenScopes;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 4000)
    private String refreshTokenValue;
    private Instant refreshTokenIssuedAt;
    private Instant refreshTokenExpiresAt;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 2000)
    private String refreshTokenMetadata;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 4000)
    private String oidcIdTokenValue;
    private Instant oidcIdTokenIssuedAt;
    private Instant oidcIdTokenExpiresAt;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 2000)
    private String oidcIdTokenMetadata;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 2000)
    private String oidcIdTokenClaims;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 4000)
    private String userCodeValue;
    private Instant userCodeIssuedAt;
    private Instant userCodeExpiresAt;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 2000)
    private String userCodeMetadata;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 4000)
    private String deviceCodeValue;
    private Instant deviceCodeIssuedAt;
    private Instant deviceCodeExpiresAt;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 2000)
    private String deviceCodeMetadata;

}


