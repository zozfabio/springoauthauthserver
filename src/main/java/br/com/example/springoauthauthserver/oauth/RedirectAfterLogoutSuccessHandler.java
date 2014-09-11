package br.com.example.springoauthauthserver.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RedirectAfterLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private RedirectStrategy redirectStrategy;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirUrl = request.getParameter("redir");

        redirectStrategy.sendRedirect(request, response, UriComponentsBuilder
                .fromUriString(redirUrl)
                .build()
                .encode()
                .toString());
    }
}
