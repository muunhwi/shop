package com.pofol.shop.config;

import com.pofol.shop.App;
import com.pofol.shop.domain.Member;
import com.pofol.shop.domain.sub.Address;
import com.pofol.shop.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = App.class)
public class h2dbTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("h2db 테스트")
    void h2dbTest() {
        Member customer = Member.builder()
                .email("asd@bse.com")
                .nickname("123")
                .password("123")
                .phoneNumber("010-1111-1234")
                .address(new Address("12345", "대구 달서구 상인동", "1421-8",""))
                .build();
        Long id = memberService.save(customer);
        Assertions.assertEquals(id, 1L);
    }
}
