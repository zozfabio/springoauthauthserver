package br.com.example.springoauthauthserver.service;

import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;

public interface JpaClientDetailsService extends ClientDetailsService, ClientRegistrationService {
}
