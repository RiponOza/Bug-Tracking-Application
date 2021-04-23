package com.ripon.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ripon.dao.UserDao;
import com.ripon.entity.Login;
import com.ripon.service.EmailService;
import com.ripon.service.UserService;

@RestController
public class TestApi {
	
	@Autowired
	UserService service;
	
	

	@RequestMapping("/api/hello")
	public String helloFromApi(@RequestParam("key") String apiKey) {
		if (apiKey.equals("api2250")) {
			return "hello user. I am listing .....";
		} else {
			return "Bad request ..... ";
		}
	}

	@RequestMapping("/api/auth")
	public String authUser(@RequestParam("key") String apiKey, @RequestParam("email") String email, @RequestParam("pwd") String password) {
		if (!apiKey.equals("api2250")) {
			return "wrong api key.";
		} 
		Login login = new Login();
		login.setEmail(email);
		login.setPassword(password);
		boolean status = service.loginUser(login);
		return (status ? "log in success.." : "bad credentials");
	}
	
	@RequestMapping("/api/map")
	public Map showList() {
		Map<String, Integer> m = new HashMap<String, Integer>();
		m.put("Ripon Oza", 2001);
		m.put("Vanda Margraf", 2002);
		m.put("zaynep Turk", 2003);
		return m;
	}
	
	// email send test
	@Autowired
	private EmailService emailService;
	@RequestMapping("/email")
	public String sendEmail(@RequestParam("to") String to, @RequestParam("subject") String subject, @RequestParam("text") String text) {
		
		return (emailService.sendEmailWithAttachment(to, subject, text))?"Success":"OOps failed";
	}

	
}
