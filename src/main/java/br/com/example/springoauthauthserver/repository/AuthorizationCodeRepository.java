package br.com.example.springoauthauthserver.repository;

import br.com.example.springoauthauthserver.model.CodeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationCodeRepository extends JpaRepository<CodeModel, String> {
}
