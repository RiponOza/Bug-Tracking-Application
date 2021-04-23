package com.ripon.service.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ripon.entity.Login;
import com.ripon.service.UserService;

@SpringBootTest(classes = {UserService.class})
class UserServiceTest {

	@Autowired
	UserService us;
	@Autowired
	Login l;
	
	
	@Test
	void test01() {
		l.setEmail("riponoja9@gmail.com");
		l.setPassword("root");
		assertEquals(true, us.loginUser(l), "Values are not same !!");
	}
	
	@Test
	void test02() {
		assertEquals("Ripon", us.getUserName("riponoja9@gmail.com", "root"));
	}

}
