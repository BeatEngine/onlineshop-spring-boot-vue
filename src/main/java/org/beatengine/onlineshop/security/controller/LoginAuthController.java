package org.beatengine.onlineshop.security.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.beatengine.onlineshop.entity.User;
import org.beatengine.onlineshop.repository.UserRepository;
import org.beatengine.onlineshop.security.*;
import org.beatengine.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginAuthController {
    private final TokenStore tokenStore = TokenStore.getStore();

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public LoginAuthController(PasswordEncoder passwordEncoder,
                               JwtService jwtService, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    /**
     * Get the login page
     * @return A Thymeleaf model for the login-page
     */
    @GetMapping("/auth/login")
    public ModelAndView loginPage() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("jsPath", "js/");
        return mav;
    }

    /**
     * Save the Session-Token for the user in the TokenStore
     * @param user The usersId will be stored for the token
     * @param jwtToken
     */
    private void saveUserToken(final User user, final String jwtToken) {
        final Token token = new Token(jwtToken, user.getId());
        tokenStore.setToken(jwtToken, token);
    }

    /**
     * Get the register page
     * @return A Thymeleaf model for the register-page
     */
    @GetMapping("/auth/register")
    public ModelAndView registerPage() {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("jsPath", "js/");
        return mav;
    }

    /**
     * Try to register a new user or show the failure.
     * @return A Thymeleaf model for the register-page
     */
    @PostMapping("/auth/register")
    @ResponseBody
    public ModelAndView registerUser(@RequestParam String email,
                                     @RequestParam String password,
                                     @RequestParam String username) {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("jsPath", "js/");

        try {
            final User savedUser = userService.addUser(email.trim(), username, password);
            var jwtToken = jwtService.generateToken(savedUser);
            saveUserToken(savedUser, jwtToken);
        } catch (final Exception emailExists)
        {
            //User with this email does already exist --> redirect to register with error parameter.
            mav.addObject("registerError", emailExists.getMessage());
            return mav;
        }

        /* The user has successfully registered and is redirected to login page */
        ModelAndView redirect =  new ModelAndView("redirect:/auth/login");
        return redirect;
    }

    /**
     * Try to sign in the user or show the failure.
     * @return A Thymeleaf model for the login-page
     */
    @PostMapping("/auth/login")
    @ResponseBody
    public ModelAndView loginUser(@RequestParam String email,
                                     @RequestParam String password, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("jsPath", "js/");

        try {
            final User user = userRepository.findByEmail(email);
            // ########################## Authentication logic ##########################
            if(!SecurityConfig.passwordEncoder().matches(password, user.getSha256Pass())
               ||! user.getEmail().equals(email))
            {
                throw new BadCredentialsException("Bad credentials");
            }
            final String jwtToken = jwtService.generateToken(user);
            saveUserToken(user, jwtToken);
            // ################## Set the response Authorization token ##################
            response.addCookie(JwtAuthenticationFilter.getSessionCookie(jwtToken));
        } catch (final Exception emailExists)
        {
            //User with this email does already exist --> redirect to register with error parameter.
            mav.addObject("loginError", emailExists.getMessage());
            return mav;
        }

        /* The user has successfully registered and is redirected to main page */
        final ModelAndView redirect =  new ModelAndView("redirect:/");
        return redirect;
    }

}
