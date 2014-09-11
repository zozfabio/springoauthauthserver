package br.com.example.springoauthauthserver.oauth;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.approval.DefaultUserApprovalHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthorizedUserScopesApprovalHandler extends DefaultUserApprovalHandler {

    @Override
    public AuthorizationRequest checkForPreApproval(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
        authorizationRequest.setApproved(true);

        return super.checkForPreApproval(authorizationRequest, userAuthentication);
    }
}
