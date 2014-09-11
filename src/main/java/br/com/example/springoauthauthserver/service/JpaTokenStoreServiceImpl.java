package br.com.example.springoauthauthserver.service;

import br.com.example.springoauthauthserver.model.AccessTokenModel;
import br.com.example.springoauthauthserver.model.RefreshTokenModel;
import br.com.example.springoauthauthserver.repository.AccessTokenRepository;
import br.com.example.springoauthauthserver.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
class JpaTokenStoreServiceImpl implements JpaTokenStoreService {

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private AccessTokenRepository atRepository;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private RefreshTokenRepository rtRepository;

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    private String extractTokenKey(String value) {
        if (value == null) {
            return null;
        }

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available. Fatal (should be in the JDK).");
        }

        try {
            byte[] bytes = digest.digest(value.getBytes("UTF-8"));

            return String.format("%032x", new BigInteger(1, bytes));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 encoding not available. Fatal (should be in the JDK).");
        }
    }

    @Transactional
    @Override
    public OAuth2Authentication readAuthentication(String tokenValue) {
        AccessTokenModel model = atRepository.findOne(extractTokenKey(tokenValue));

        if (model == null) {
            return null;
        }

        return SerializationUtils.<OAuth2Authentication>deserialize(model.getAuthentication());
    }

    @Transactional
    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        String tokenValue = token.getValue();

        return readAuthentication(tokenValue);
    }

    @Transactional
    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        AccessTokenModel model = new AccessTokenModel();

        model.setTokenId(extractTokenKey(token.getValue()));
        model.setToken(SerializationUtils.serialize(token));
        model.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
        model.setAuthentication(SerializationUtils.serialize(authentication));
        model.setUserName(authentication.isClientOnly() ? null : authentication.getName());
        model.setRefreshToken(extractTokenKey(token.getRefreshToken() != null ? token.getRefreshToken().getValue() : null));
        model.setClientId(authentication.getOAuth2Request().getClientId());

        atRepository.save(model);
    }

    @Transactional
    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        AccessTokenModel model = atRepository.findOne(extractTokenKey(tokenValue));

        if (model == null) {
            return null;
        }

        OAuth2AccessToken toke = SerializationUtils.<OAuth2AccessToken>deserialize(model.getToken());

        return toke;
    }

    @Transactional
    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        atRepository.delete(extractTokenKey(token.getValue()));
    }

    @Transactional
    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        RefreshTokenModel model = new RefreshTokenModel();

        model.setTokenId(extractTokenKey(refreshToken.getValue()));
        model.setToken(SerializationUtils.serialize(refreshToken));
        model.setAuthentication(SerializationUtils.serialize(authentication));

        rtRepository.save(model);
    }

    @Transactional
    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        RefreshTokenModel model = rtRepository.findOne(extractTokenKey(tokenValue));

        if (model == null) {
            return null;
        }

        return SerializationUtils.<OAuth2RefreshToken>deserialize(model.getToken());
    }

    @Transactional
    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        RefreshTokenModel model = rtRepository.findOne(extractTokenKey(token.getValue()));

        if (model == null) {
            return null;
        }

        return SerializationUtils.<OAuth2Authentication>deserialize(model.getAuthentication());
    }

    @Transactional
    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        rtRepository.delete(extractTokenKey(token.getValue()));
    }

    @Transactional
    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        atRepository.deleteByRefreshToken(extractTokenKey(refreshToken.getValue()));
    }

    @Transactional
    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        String authenticationId = authenticationKeyGenerator.extractKey(authentication);
        AccessTokenModel model =  atRepository.findByAuthenticationId(authenticationId);

        if (model == null) {
            return null;
        }

        return SerializationUtils.<OAuth2AccessToken>deserialize(model.getToken());
    }

    @Transactional
    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        List<OAuth2AccessToken> accessTokens = new LinkedList<OAuth2AccessToken>();

        List<AccessTokenModel> list = atRepository.findByClientIdAndUserName(clientId, userName);

        for (AccessTokenModel model : list) {
            OAuth2AccessToken accessToken = SerializationUtils.<OAuth2AccessToken>deserialize(model.getToken());

            if (accessToken != null) {
                accessTokens.add(accessToken);
            }
        }

        return accessTokens;
    }

    @Transactional
    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        List<OAuth2AccessToken> accessTokens = new LinkedList<OAuth2AccessToken>();

        List<AccessTokenModel> list = atRepository.findByClientId(clientId);

        for (AccessTokenModel model : list) {
            OAuth2AccessToken accessToken = SerializationUtils.<OAuth2AccessToken>deserialize(model.getToken());

            if (accessToken != null) {
                accessTokens.add(accessToken);
            }
        }

        return accessTokens;
    }
}
