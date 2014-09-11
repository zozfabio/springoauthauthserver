package br.com.example.springoauthauthserver.repository;

import br.com.example.springoauthauthserver.model.RefreshTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenModel, String> {
}
