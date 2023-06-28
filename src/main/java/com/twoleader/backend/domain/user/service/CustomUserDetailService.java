package com.twoleader.backend.domain.user.service;

import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.repository.UserRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository
        .findByEmail(email)
        .map(this::createUserDetails)
        .orElseThrow(() -> new UsernameNotFoundException(email + " 을 DB에서 찾을 수 없습니다"));
  }

  private UserDetails createUserDetails(User user) {
    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().toString());

    return new org.springframework.security.core.userdetails.User(
        String.valueOf(user.getUserId()), user.getPassword(), Collections.singleton(grantedAuthority));
  }
}
