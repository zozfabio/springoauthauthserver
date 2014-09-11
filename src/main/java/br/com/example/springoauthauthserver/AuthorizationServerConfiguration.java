package br.com.example.springoauthauthserver;

import br.com.example.springoauthauthserver.oauth.AuthorizedUserScopesApprovalHandler;
import br.com.example.springoauthauthserver.oauth.JpaClientDetailsServiceBuilder;
import br.com.example.springoauthauthserver.service.JpaAuthorizationCodeService;
import br.com.example.springoauthauthserver.service.JpaTokenStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.*;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private JpaTokenStoreService tokenStore;

    @Autowired
    private JpaAuthorizationCodeService authorizationCode;

    @Autowired
    private AccessTokenConverter accessTokenConverter;

    @Autowired
    private JpaClientDetailsServiceBuilder clientDetailsServiceBuilder;

    @Autowired
    private AuthorizedUserScopesApprovalHandler userApprovalHandler;

    @Autowired
    private List<TokenEnhancer> tokenEnhancers = Collections.<TokenEnhancer>emptyList();

    @Autowired
    private TokenEnhancerChain tokenEnhancerChain;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices tokenServices;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
                /*.withClient("client")
                .resourceIds("spring-resource-server")
                .authorizedGrantTypes("authorization_code", "implicit")
                .secret("123456")
                .scopes("list", "get")
                .redirectUris("http://client.example.com:38080")
                .authorities("ROLE_CLIENT");*/
        clients
            .setBuilder(clientDetailsServiceBuilder);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
            .tokenKeyAccess("hasRole('ROLE_CLIENT')")
            .checkTokenAccess("hasRole('ROLE_CLIENT')");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            .tokenStore(tokenStore)
            .tokenEnhancer(tokenEnhancerChain)
            .accessTokenConverter(accessTokenConverter)
            .userApprovalHandler(userApprovalHandler)
            .authorizationCodeServices(authorizationCode)
            .tokenServices(tokenServices)

            .approvalStoreDisabled();
    }

    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();

        tokenServices.setTokenStore(tokenStore);
        tokenServices.setTokenEnhancer(tokenEnhancerChain);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setAccessTokenValiditySeconds(30 * 60);
        tokenServices.setRefreshTokenValiditySeconds(30 * 60);

        return tokenServices;
    }

    @Bean
    public TokenEnhancerChain tokenEnhancerChain() {
        TokenEnhancerChain tokenEnhancer = new TokenEnhancerChain();

        tokenEnhancer.setTokenEnhancers(tokenEnhancers);

        return tokenEnhancer;
    }

    @Bean
    public OAuth2RequestFactory requestFactory() {
        return new DefaultOAuth2RequestFactory(clientDetailsService);
    }

    @Bean
    public AccessTokenConverter accessTokenConverter() {
        return new DefaultAccessTokenConverter();
    }
}
