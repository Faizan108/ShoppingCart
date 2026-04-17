package com.practice.ShoppingCart.security.User;

import com.practice.ShoppingCart.exception.UserNotFound;
import com.practice.ShoppingCart.model.User;
import com.practice.ShoppingCart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(()->new UserNotFound("User Not Found!"));
        return ShopUserDetails.buildUserDetails(user);
    }
}
