package com.pofol.shop;

import com.pofol.shop.domain.Member;
import com.pofol.shop.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@Profile("test")
@ContextConfiguration(classes = App.class)
public class AppTests {

	@Autowired
	private MemberService memberService;


	@Test
	void contextLoads() {
	}

}
