package org.beatengine.onlineshop.controller;

import org.beatengine.onlineshop.security.JwtAuthenticationFilter;
import org.beatengine.onlineshop.security.Token;
import org.beatengine.onlineshop.security.TokenStore;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainAppController {

    /**
     * Get the main index page
     * @return A Thymeleaf model for the index
     */
    @GetMapping("/")
    public ModelAndView indexPage(@CookieValue(name = JwtAuthenticationFilter.AUTH_COOKIE_NAME, required = false) String token) {
        final Token tok = TokenStore.getStore().findByToken(token);
        if(token == null || tok == null || tok.isExpired())
        {
            //Session not found --> login
            return new ModelAndView("redirect:/auth/login");
        }


        ModelAndView mav = new ModelAndView("index");
        mav.addObject("initTitle", "Online shop DEMO - Spring & VUE");
        mav.addObject("jsPath", "js/");
        return mav;
    }

}
