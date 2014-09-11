package br.com.example.springoauthauthserver.oauth;

import br.com.example.springoauthauthserver.model.AllowedAuthorityScope;
import br.com.example.springoauthauthserver.model.AuthorityModel;
import br.com.example.springoauthauthserver.repository.AllowedAuthoritiesScopesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
class AllowedUserScopesTokenEnhancer implements TokenEnhancer {

    @Autowired
    private AllowedAuthoritiesScopesRepository repository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken tokenEnhancers, OAuth2Authentication authentication) {
        Collection<GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities == null || authorities.isEmpty()) {
            return tokenEnhancers;
        }

        DefaultOAuth2AccessToken newToken = new DefaultOAuth2AccessToken(tokenEnhancers);
        Set<String> scopes = new HashSet<String>();

        for (GrantedAuthority authority : authorities) {
            if (!(authority instanceof AuthorityModel)) {
                continue;
            }

            AuthorityModel authorityModel = (AuthorityModel) authority;

            List<AllowedAuthorityScope> allowedScopes = repository.findById_Role(authorityModel.getRole());

            if (allowedScopes == null || allowedScopes.isEmpty()) {
                continue;
            }

            for (AllowedAuthorityScope allowedScope : allowedScopes) {
                scopes.add(allowedScope.getScope());
            }
        }

        newToken.setScope(scopes);

        return newToken;
    }
}
