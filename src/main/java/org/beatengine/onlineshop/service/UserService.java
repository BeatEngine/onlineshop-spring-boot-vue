package org.beatengine.onlineshop.service;

import org.beatengine.onlineshop.entity.User;
import org.beatengine.onlineshop.repository.UserRepository;
import org.beatengine.onlineshop.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
//@Import(UserRepository.class)
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllAuthUsers()
    {
        return userRepository.findAll();
    }

    public User addUser(String email, String username, String password) throws Exception
    {
        if(userRepository.countByEmail(email) > 0)
        {
            throw new Exception("This email already exists!");
        }

        // Save the new User
        final User addUser = new User();
        addUser.setEmail(email);
        addUser.setDisplayName(username);
        // Use the encoder to save the hash of the password
        addUser.setSha256Pass(SecurityConfig.passwordEncoder().encode(password));
        userRepository.save(addUser);
        return addUser;
    }



}
