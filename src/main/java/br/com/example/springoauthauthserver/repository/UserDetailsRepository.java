package br.com.example.springoauthauthserver.repository;

import br.com.example.springoauthauthserver.model.UserDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetailsModel, String> {

    public UserDetailsModel findByUsername(String username);
}
