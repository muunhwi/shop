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

    public Member findById(Long id) {
        Optional<Member> findCustomer = MemberRepository.findById(id);
        return findCustomer.orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다"));
    }

    public Member findByEmail(String email) {
        Optional<Member> findCustomer = MemberRepository.findByEmail(email);
        return findCustomer.orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다"));
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.info("email = {}", email);

        if(email.equals("user")) {
            Member user = Member.builder()
                    .id(1L)
                    .accountNonLocked(true)
                    .username("user")
                    .enabled(true)
                    .email("user")
                    .password(encoder.encode("123"))
                    .role("USER")
                    .build();
            return user;
        }

        if(email.equals("admin")) {
            Member admin = Member.builder()
                    .id(1L)
                    .accountNonLocked(true)
                    .username("user")
                    .enabled(true)
                    .email("user")
                    .password(encoder.encode("123"))
                    .role("ROLE_ADMIN")
                    .build();
            return admin;
        }



        Optional<Member> byEmail = MemberRepository.findByEmail(email);
        return MemberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
    }
}
