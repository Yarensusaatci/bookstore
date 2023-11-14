package com.postgresql.yaren_bookstore.webApi.controllers;

import com.postgresql.yaren_bookstore.business.concretes.UserManager;
import com.postgresql.yaren_bookstore.business.requests.CreateUserRequest;
import com.postgresql.yaren_bookstore.business.requests.LoginUserRequest;
import com.postgresql.yaren_bookstore.security.JwtTokenProvider;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    private UserManager userManager;

    @PostMapping("/login")
    public String login (@RequestBody LoginUserRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        return "Bearer " + jwtToken;
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> register(@RequestBody CreateUserRequest registerRequest){
        if(userManager.getOneUserByEmail(registerRequest.getEmail()) != null) {
            return new ResponseEntity<>("This e-mail address has been used before.", HttpStatus.BAD_REQUEST);
        }

        return userManager.create(registerRequest);
    }
}
