package br.com.example.springoauthauthserver.service;

import br.com.example.springoauthauthserver.model.AuthorityModel;
import br.com.example.springoauthauthserver.model.UserDetailsModel;
import br.com.example.springoauthauthserver.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsModel model = repository.findByUsername(username);

        if (model == null) {
            throw new UsernameNotFoundException("User " + username + " not found!");
        }

        // load authorities
        List<AuthorityModel> authorities = model.getAuthorities();
        if (authorities.size() > 0) {
            authorities.get(0).getAuthority();
        }

        return model;
    }
}
