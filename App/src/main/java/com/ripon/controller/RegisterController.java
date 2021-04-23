package com.ripon.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ripon.entity.User;
import com.ripon.service.UserService;

@Controller
public class RegisterController {

	@Autowired
	UserService userService;
	@Autowired
	User user;

	// loads register page
	@RequestMapping("/register")
	public String renderRegisterPage(Model model, HttpSession session) {
		// if user is already signed in
		if (session.getAttribute("userid") != null) {
			return "dashboard";
		} else {
			// creating a model attribute user for registration process
			model.addAttribute("user", user);
			// if user is not signed in
			return "register";
		}
	}

	@PostMapping("/registeruser")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model,
			HttpSession session) {
		if (!(result.hasErrors()) && user.getPassword().equals(user.getConfirmPassword())) {
			user.setId(userService.generateId(user.getEmail()));
			boolean status = userService.registerUser(user);
			if (status) {
				// registration success
				// save user id and user name to the session object
				session.setAttribute("userid", user.getId());
				session.setAttribute("username", user.getFirstname());
				session.setAttribute("role", user.getFirstname());
				return "dashboard";
			} else {
				// registration failure
				// redirect to login page
				model.addAttribute("status", "opps. Something went wrong. Try again !");
				return "loginpage";
			}
		} else {
			return "register";
		}
	}

}