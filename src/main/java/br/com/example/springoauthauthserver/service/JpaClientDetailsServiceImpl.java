package br.com.example.springoauthauthserver.service;

import br.com.example.springoauthauthserver.model.ClientDetailsModel;
import br.com.example.springoauthauthserver.repository.ClientDetailsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

@Service
class JpaClientDetailsServiceImpl implements JpaClientDetailsService {

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private ClientDetailsRepository repository;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private ObjectMapper objectMapper;

    private static Set<String> extractAutoApproveScopes(ClientDetails clientDetails) {
        Set<String> scopes = new HashSet<String>();

        if (clientDetails.isAutoApprove("true")) {
            scopes.add("true");

            return scopes;
        }

        for (String scope : clientDetails.getScope()) {
            if (clientDetails.isAutoApprove(scope)) {
                scopes.add(scope);
            }
        }

        return scopes;
    }

    private ClientDetails fromModelToClientDetails(ClientDetailsModel model) {
        BaseClientDetails clientDetails = new BaseClientDetails(
                model.getClientId(),
                model.getResourceIds(),
                null,
                null,
                model.getAuthorities(),
                model.getWebServerRedirectUri());

        clientDetails.setScope(model.getScopes());
        clientDetails.setAuthorizedGrantTypes(model.getGrantTypes());
        clientDetails.setClientSecret(model.getClientSecret());
        clientDetails.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(model.getAutoApprove()));
        clientDetails.setAccessTokenValiditySeconds(model.getAccessTokenValidity());
        clientDetails.setRefreshTokenValiditySeconds(model.getRefreshTokenValidity());

        try {
            clientDetails.setAdditionalInformation(objectMapper.<Map<String, Object>>readValue(model.getAdditionalInformation(), objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class)));
        } catch (IOException ex) {
        }

        return clientDetails;
    }

    private ClientDetailsModel fromClientDetailsToModel(ClientDetails clientDetails, ClientDetailsModel model) {
        model.setClientId(clientDetails.getClientId());
        model.setClientSecret(clientDetails.getClientSecret());
        model.setAccessTokenValidity(clientDetails.getAccessTokenValiditySeconds());
        model.setRefreshTokenValidity(clientDetails.getRefreshTokenValiditySeconds());
        model.setAuthorities(StringUtils.collectionToCommaDelimitedString(clientDetails.getAuthorities()));
        model.setGrantTypes(clientDetails.getAuthorizedGrantTypes());
        model.setAutoApprove(StringUtils.collectionToCommaDelimitedString(extractAutoApproveScopes(clientDetails)));
        model.setResourceIds(StringUtils.collectionToCommaDelimitedString(clientDetails.getResourceIds()));
        model.setScopes(clientDetails.getScope());
        model.setWebServerRedirectUri(StringUtils.collectionToCommaDelimitedString(clientDetails.getRegisteredRedirectUri()));

        try {
            model.setAdditionalInformation(objectMapper.writeValueAsString(clientDetails.getAdditionalInformation()));
        } catch (JsonProcessingException ex) {
        }

        return model;
    }

    private ClientDetailsModel fromClientDetailsToModel(ClientDetails clientDetails) {
        ClientDetailsModel model = new ClientDetailsModel();

        return fromClientDetailsToModel(clientDetails, model);
    }

    @Transactional
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetailsModel model = repository.findOne(clientId);

        if (model == null) {
            throw new ClientRegistrationException("Client with clientId: " + clientId + " already exists.");
        }

        return fromModelToClientDetails(model);
    }

    @Transactional
    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        String clientId = clientDetails.getClientId();

        ClientDetailsModel model = repository.findOne(clientId);
        if (model != null) {
            throw new ClientAlreadyExistsException("Client with clientId: " + clientId + " already exists.");
        }

        model = fromClientDetailsToModel(clientDetails);

        repository.save(model);
    }

    @Transactional
    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        String clientId = clientDetails.getClientId();

        ClientDetailsModel model = repository.findOne(clientId);

        if (model == null) {
            throw new NoSuchClientException("No such client for clientId: " + clientId + ".");
        }

        fromClientDetailsToModel(clientDetails, model);

        repository.save(model);
    }

    @Transactional
    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        ClientDetailsModel model = repository.findOne(clientId);

        if (model == null) {
            throw new NoSuchClientException("No such client for clientId: " + clientId + ".");
        }

        model.setClientSecret(secret);

        repository.save(model);
    }

    @Transactional
    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        repository.delete(clientId);
    }

    @Transactional
    @Override
    public List<ClientDetails> listClientDetails() {
        List<ClientDetails> res = new LinkedList<ClientDetails>();
        List<ClientDetailsModel> all = repository.findAll();

        for (ClientDetailsModel model : all) {
            res.add(fromModelToClientDetails(model));
        }

        return res;
    }
}
