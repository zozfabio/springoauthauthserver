package br.com.example.springoauthauthserver.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(schema = "public", name = "auth_code")
public class CodeModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String code;

    private byte[] authentication;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        if (!(o instanceof CodeModel)) return false;

        CodeModel that = (CodeModel) o;

        if (!code.equals(that.code)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuthorizationCode{");
        sb.append("code='").append(code).append('\'');
        sb.append(", authentication=").append(Arrays.toString(authentication));
        sb.append('}');
        return sb.toString();
    }
}
