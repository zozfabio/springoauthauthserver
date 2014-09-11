package br.com.example.springoauthauthserver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(schema = "public", name = "access_token")
public class AccessTokenModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "token_id")
    private String tokenId;

    private byte[] token;

    @Column(name = "authentication_id")
    private String authenticationId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "client_id")
    private String clientId;

    private byte[] authentication;

    @Column(name = "refresh_token")
    private String refreshToken;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccessTokenModel)) return false;

        AccessTokenModel that = (AccessTokenModel) o;

        if (!tokenId.equals(that.tokenId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return tokenId.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccessToken{");
        sb.append("tokenId='").append(tokenId).append('\'');
        sb.append(", token=").append(Arrays.toString(token));
        sb.append(", authenticationId='").append(authenticationId).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", clientId='").append(clientId).append('\'');
        sb.append(", authentication=").append(Arrays.toString(authentication));
        sb.append(", refreshToken='").append(refreshToken).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
