package br.com.example.springoauthauthserver.oauth;

import br.com.example.springoauthauthserver.service.JpaClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JpaClientDetailsServiceBuilder extends ClientDetailsServiceBuilder<JpaClientDetailsServiceBuilder> {

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private JpaClientDetailsService clientDetailsService;

    @Override
    protected ClientDetailsService performBuild() {
        return clientDetailsService;
    }
}
