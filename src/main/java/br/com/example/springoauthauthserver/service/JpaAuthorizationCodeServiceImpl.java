package br.com.example.springoauthauthserver.service;

import br.com.example.springoauthauthserver.model.CodeModel;
import br.com.example.springoauthauthserver.repository.AuthorizationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class JpaAuthorizationCodeServiceImpl extends RandomValueAuthorizationCodeServices implements JpaAuthorizationCodeService {

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private AuthorizationCodeRepository repository;

    @Transactional
    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        CodeModel model = new CodeModel();

        model.setCode(code);
        model.setAuthentication(SerializationUtils.serialize(authentication));

        repository.save(model);
    }

    @Transactional
    @Override
    protected OAuth2Authentication remove(String code) {
        CodeModel model = repository.findOne(code);

        if (model != null) {
            repository.delete(model);

            return SerializationUtils.<OAuth2Authentication>deserialize(model.getAuthentication());
        }

        return null;
    }
}
