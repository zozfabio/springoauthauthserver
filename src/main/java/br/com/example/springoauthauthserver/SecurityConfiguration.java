package br.com.example.springoauthauthserver;

import br.com.example.springoauthauthserver.oauth.RedirectAfterLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedirectAfterLogoutSuccessHandler logoutHandler;

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
            .csrf()
                .disable()
            .authorizeRequests()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/oauth/authorize").fullyAuthenticated()
            .and()
            .formLogin()
                .loginPage("/login").permitAll()
                    .usernameParameter("login")
                    .passwordParameter("password")
                .failureUrl("/login?invalidUser")
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutHandler)
                .invalidateHttpSession(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService);
    }

    @Bean
    public RedirectStrategy defaultRedirectStrategy() {
        return new DefaultRedirectStrategy();
    }
}