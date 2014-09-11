package br.com.example.springoauthauthserver.model;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "public", name = "users")
public class UserDetailsModel implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    private String username;

    private String password;

    private Boolean enabled;

    @JoinTable(schema = "public", name = "users_authorities", joinColumns = { @JoinColumn(name = "username", referencedColumnName = "username", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "role", referencedColumnName = "role", nullable = false) })
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AuthorityModel> authorities;

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public List<AuthorityModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<AuthorityModel> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDetailsModel that = (UserDetailsModel) o;

        if (!username.equals(that.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDetailsModel{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", enabled=").append(enabled);
        sb.append(", authorities=").append(authorities);
        sb.append('}');
        return sb.toString();
    }
}
