package com.pofol.shop.domain;

import com.pofol.shop.domain.sub.Address;
import com.pofol.shop.domain.sub.BaseEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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
public class Member extends BaseEntity implements UserDetails  {

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
    private boolean accountNonLocked;
    @Column(columnDefinition = "boolean default true")
    private boolean enabled;

    @OneToMany(mappedBy = "member")
    private List<Coupon> coupons = new ArrayList<>();


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

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
