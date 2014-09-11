package br.com.example.springoauthauthserver.repository;

import br.com.example.springoauthauthserver.model.AllowedAuthorityScope;
import br.com.example.springoauthauthserver.model.AllowedAuthorityScopeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllowedAuthoritiesScopesRepository extends JpaRepository<AllowedAuthorityScope, AllowedAuthorityScopeId> {

    public List<AllowedAuthorityScope> findById_Role(String role);
}
