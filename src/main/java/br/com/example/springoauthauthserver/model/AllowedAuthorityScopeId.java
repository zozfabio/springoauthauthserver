package br.com.example.springoauthauthserver.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AllowedAuthorityScopeId implements Serializable {

    private static final long serialVersionUID = 1L;

    private String role;

    @Column(name = "client_id")
    private String clientId;

    private String scope;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AllowedAuthorityScopeId id = (AllowedAuthorityScopeId) o;

        if (clientId != null ? !clientId.equals(id.clientId) : id.clientId != null) return false;
        if (role != null ? !role.equals(id.role) : id.role != null) return false;
        if (scope != null ? !scope.equals(id.scope) : id.scope != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = role != null ? role.hashCode() : 0;
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (scope != null ? scope.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Id{");
        sb.append("role='").append(role).append('\'');
        sb.append(", clientId='").append(clientId).append('\'');
        sb.append(", scope='").append(scope).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
