package com.pofol.shop.domain;

import com.pofol.shop.domain.sub.Address;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
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
@EqualsAndHashCode(of="id")
@DynamicInsert
public class Member implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_PK_GENERATOR")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Embedded
    @Column(nullable = false)
    private Address address;

    private String phoneNumber;

    @Column(unique = true)
    private String nickname;

    @Column(length = 6)
    private String sex;

    @Lob
    private String profileImg;

    private String introduce;

    @Column(columnDefinition = "varchar(10) default 'ROLE_USER'")
    private String role;
    @Column(columnDefinition = "boolean default true")
    boolean accountNonLocked;
    @Column(columnDefinition = "boolean default true")
    boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
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
