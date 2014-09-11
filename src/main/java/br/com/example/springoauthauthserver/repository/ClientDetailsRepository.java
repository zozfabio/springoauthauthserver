package br.com.example.springoauthauthserver.repository;

import br.com.example.springoauthauthserver.model.ClientDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDetailsRepository extends JpaRepository<ClientDetailsModel, String> {
}
