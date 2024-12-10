package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.helpers.AppComponent;
import com.scm.model.Providers;
import com.scm.model.Users;
import com.scm.repositries.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenicationSuccessHandler implements AuthenticationSuccessHandler {

    org.slf4j.Logger logger = LoggerFactory.getLogger(OAuthAuthenicationSuccessHandler.class);
    private final UserRepo userRepo;

    public OAuthAuthenicationSuccessHandler(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        logger.info("OAuthAuthenicationSuccessHandler");
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;       
        String authorizedClientRegistrationId = token.getAuthorizedClientRegistrationId();
        logger.info(authorizedClientRegistrationId);

        var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        oauthUser.getAttributes().forEach((key,value)->{
            logger.info("{},{}",key,value);
        });
        Users users = new Users();
        users.setUserId(UUID.randomUUID().toString());
        users.setRoleList(List.of(AppComponent.ROLE_USER));
        users.setEmailVerified(true);
        users.setEnabled(true);
        users.setPassword("SCMYT2.0");
        if(authorizedClientRegistrationId.equalsIgnoreCase("google"))
        {
            users.setUserEmail(oauthUser.getAttribute("email").toString());
            users.setProfilePic(oauthUser.getAttribute("picture").toString());
            users.setUserName(oauthUser.getAttribute("name").toString());
            users.setProviderUserId(oauthUser.getName());
            users.setProvider(Providers.GOOGLE);
            users.setAbout("This account is created using google..");
        }
        else if(authorizedClientRegistrationId.equalsIgnoreCase("github"))
        {
            String email = oauthUser.getAttribute("email")!=null ? oauthUser.getAttribute("email").toString() : oauthUser.getAttribute("login").toString()+"@gmail.com";
            String picture = oauthUser.getAttribute("avatar_url").toString();
            String name = oauthUser.getAttribute("login").toString();
            String providerUseerId = oauthUser.getName();

            users.setUserEmail(email);
            users.setProfilePic(picture);
            users.setUserName(name);
            users.setProviderUserId(providerUseerId);
            users.setProvider(Providers.GITHUB);
            users.setAbout("This account is created using github");
        }
        else
        {
            logger.info("OAuthAuthenicationSuccessHandler: Unknown provider");
        }
        /* 
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
        // logger.info(user.getName());
        // user.getAttributes().forEach((key,value)->{
        // logger.info("{}=>{}",key,value);
        // });
        // logger.info(user.getAttributes().toString());
        String email = user.getAttribute("email").toString();
        String name = user.getAttribute("name").toString();
        String picture = user.getAttribute("picture").toString();
        Users user1 = new Users();
        user1.setUserEmail(email);
        user1.setUserName(name);
        user1.setProfilePic(picture);
        user1.setPassword("password");
        user1.setUserId(UUID.randomUUID().toString());
        user1.setProvider(Providers.GOOGLE);
        user1.setEnabled(true);
        user1.setEmailVerified(true);
        user1.setProviderUserId(user.getName());
        user1.setRoleList(List.of(AppComponent.ROLE_USER));
        user1.setAbout("This account is created using google..");
         */
        Users user2 = userRepo.findByUserEmail(users.getUserEmail()).orElse(null);
        if(user2 == null){
            userRepo.save(users);
            logger.info("User Saved: {}",users.getUserEmail());
        }
               
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");

    }

}
