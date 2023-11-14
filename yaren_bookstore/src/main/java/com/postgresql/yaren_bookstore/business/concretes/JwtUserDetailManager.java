package com.postgresql.yaren_bookstore.business.concretes;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oredata.bookStore.dataAccess.abstracts.UserRepository;
import com.oredata.bookStore.entities.concretes.Users;
import com.oredata.bookStore.security.JwtUserDetails;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtUserDetailManager implements UserDetailsService {
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email);
        return JwtUserDetails.create(user);
    }

    public UserDetails loadUserById(Long id) {
        Users user = userRepository.findById(id).get();
        return JwtUserDetails.create(user);
    }

}
