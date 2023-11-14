package com.postgresql.yaren_bookstore.business.concretes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.postgresql.yaren_bookstore.business.abstracts.UserService;
import com.postgresql.yaren_bookstore.business.requests.CreateUserRequest;
import com.postgresql.yaren_bookstore.dataAccess.abstracts.UserRepository;
import com.postgresql.yaren_bookstore.entities.concretes.Users;

import lombok.AllArgsConstructor;

@Service // Business
@AllArgsConstructor
public class UserManager implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Users getOneUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<String> create(CreateUserRequest registerRequest){
        Users user = new Users();
        user.setEmail(registerRequest.getEmail());
        user.setRole(registerRequest.getRole());
        user.setName(registerRequest.getName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);

        return new ResponseEntity<>("User successfully registered.", HttpStatus.CREATED);
    }

}
