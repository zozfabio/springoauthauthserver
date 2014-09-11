package br.com.example.springoauthauthserver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(schema = "public", name = "refresh_token")
public class RefreshTokenModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "token_id")
    private String tokenId;

    private byte[] token;

    private byte[] authentication;

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

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RefreshTokenModel)) return false;

        RefreshTokenModel that = (RefreshTokenModel) o;

        if (!tokenId.equals(that.tokenId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return tokenId.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RefreshToken{");
        sb.append("tokenId='").append(tokenId).append('\'');
        sb.append(", token=").append(Arrays.toString(token));
        sb.append(", authentication=").append(Arrays.toString(authentication));
        sb.append('}');
        return sb.toString();
    }
}
