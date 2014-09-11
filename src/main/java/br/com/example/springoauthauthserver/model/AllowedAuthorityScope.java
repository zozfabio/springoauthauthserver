package br.com.example.springoauthauthserver.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "public", name = "allowed_authorities_scopes")
public class AllowedAuthorityScope implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AllowedAuthorityScopeId id;

    public AllowedAuthorityScopeId getId() {
        return id;
    }

    public void setId(AllowedAuthorityScopeId id) {
        this.id = id;
    }

    private AllowedAuthorityScopeId currentId() {
        return id == null ? new AllowedAuthorityScopeId() : id;
    }

    public String getRole() {
        return currentId().getRole();
    }

    public String getClientId() {
        return currentId().getClientId();
    }

    public String getScope() {
        return currentId().getScope();
    }

    @Override
    public int hashCode() {
        return currentId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof AllowedAuthorityScope)) {
            return false;
        }

        AllowedAuthorityScope other = (AllowedAuthorityScope) obj;

        return currentId().equals(other.currentId());
    }

    @Override
    public String toString() {
        return currentId().toString();
    }
}
