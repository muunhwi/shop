package com.pofol.shop.service;

import com.pofol.shop.domain.Member;
import com.pofol.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository MemberRepository;
    private final BCryptPasswordEncoder encoder;

    public Long save(Member customer) {
        Member saveCustomer = MemberRepository.save(customer);
        return saveCustomer.getId();
    }

    public Optional<Member> findById(Long id) {
        return MemberRepository.findById(id);
    }

    public Optional<Member> findByEmail(String email) {
        return MemberRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("email = {}", email);
        return MemberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
    }
}
