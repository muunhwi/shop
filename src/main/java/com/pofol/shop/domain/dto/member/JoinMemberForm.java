package com.pofol.shop.domain.dto.member;

import com.pofol.shop.domain.sub.Address;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class JoinMemberForm {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}$")
    private String password;

    @NotBlank
    @Pattern(regexp = "(\\d{3})(\\d{3,4})(\\d{4})")
    private String phoneNumber;

    @NotBlank
    private String sex;

    @NotBlank
    private String zoneCode;
    @NotBlank
    private String baseAddress;
    @NotNull
    private String extraAddress;
    @NotBlank
    private String detailAddress;

}
