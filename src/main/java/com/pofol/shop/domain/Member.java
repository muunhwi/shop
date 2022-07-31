package com.pofol.shop.domain;

import com.pofol.shop.domain.sub.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "MEMBER_PK_GENERATOR",
        sequenceName = "MEMBER_PK_SEQ",
        initialValue = 3,
        allocationSize = 50
)
public class Member implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_PK_GENERATOR")
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(length = 50)
    private String password;
    @Embedded
    private Address address;

    private String phoneNumber;
    @Column(length = 20)
    private String username;

    @Transient
    private String role;

    boolean accountNonLocked;
    boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}
