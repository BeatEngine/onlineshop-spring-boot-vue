package org.beatengine.onlineshop.controller.api;

import jakarta.servlet.http.HttpServletResponse;
import org.beatengine.onlineshop.entity.User;
import org.beatengine.onlineshop.entity.mapping.AccountDetail;
import org.beatengine.onlineshop.entity.mapping.AccountDetails;
import org.beatengine.onlineshop.repository.UserRepository;
import org.beatengine.onlineshop.security.JwtAuthenticationFilter;
import org.beatengine.onlineshop.security.Token;
import org.beatengine.onlineshop.security.TokenStore;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class UserController {

    private final UserRepository userRepository;

    public UserController(final UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }


    @GetMapping("/details")
    public AccountDetails getDetails(@CookieValue(name = JwtAuthenticationFilter.AUTH_COOKIE_NAME, required = true) final String token/*,
                                     final HttpServletResponse response*/)
    {
        final Token tok = TokenStore.getStore().findByToken(token);
        if(token == null || tok == null || tok.isExpired())
        {
            //Session not found --> login
            return AccountDetails.unauthenticated();
        }

        List<AccountDetail> accountDetails = userRepository.findAccountDetails(BigInteger.valueOf(tok.userId));
        if(accountDetails.isEmpty())
        {
            return AccountDetails.unauthenticated("User not found");
        }

        // Return summery of roles in single Object
        return new AccountDetails(accountDetails);
    }


}
