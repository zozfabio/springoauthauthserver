package br.com.example.springoauthauthserver.repository;

import br.com.example.springoauthauthserver.model.AccessTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessTokenRepository extends JpaRepository<AccessTokenModel, String> {

    public List<AccessTokenModel> findByClientId(String clientId);

    public AccessTokenModel findByAuthenticationId(String authenticationId);

    public List<AccessTokenModel> findByClientIdAndUserName(String clientId, String userName);

    public void deleteByRefreshToken(String refreshToken);
}
