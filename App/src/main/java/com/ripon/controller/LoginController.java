/**This is login controller. All the routing for user login process is done here.
 * @author Ripon Oza
 * @since 2021
 * @version 1.0
 */
package com.ripon.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ripon.entity.Login;
import com.ripon.service.UserService;

@Controller
//@SessionAttributes("userid")
public class LoginController {

	@Autowired
	UserService userService;
	@Autowired
	Login loginEntity;

	/**
	 * This method renders the login page. If user is already logged in, then it
	 * will show directly dashboard. If the user is not logged in, it will render
	 * the login page. Before rendering login page it creates a model attribute
	 * named 'loginDetail'
	 * 
	 * @param model
	 * @param session
	 * @return login page if session is not available, else returns dashboard for
	 *         the logged in user.
	 */
	@RequestMapping({ "/", "/login" })
	public String renderLoginPage(Model model, HttpSession session) {
		if (session.getAttribute("userid") != null) {
			return "dashboard";
		} else {
			// creating a loginDetail model attribute for login process
			model.addAttribute("loginDetail", loginEntity);
			return "loginpage";
		}

	}

	/**
	 * This handler handles the user login process. It holds user login detail in
	 * 'loginDetail' model object. If any error occurs it returns the login page
	 * with error message. If everything goes well then it saves user id and user
	 * name in two session attributes 'userid' and 'username' for 20min interval and
	 * then returns the dashboard.
	 * 
	 * @param loginDetail It is a model object which holds the login detail of the
	 *                    user in Login entity class.
	 * @param result
	 * @param session
	 * @param model
	 * @return login page with error message if any error occures . Else returns
	 *         dashboard.
	 */
	@PostMapping("/userlogin")
	public String loginUser(@Valid @ModelAttribute("loginDetail") Login loginDetail, BindingResult result,
			HttpSession session, Model model) {

		if (result.hasErrors()) {
			return "loginpage";
		}
		boolean status = userService.loginUser(loginDetail);
		if (status) {
			String userid = userService.getUserid(loginDetail.getEmail(), loginDetail.getPassword());
			String userName = userService.getUserName(loginDetail.getEmail(), loginDetail.getPassword());
			// save user id and user name in session
			session.setAttribute("userid", userid);
			session.setAttribute("username", userName);
			session.setMaxInactiveInterval(20 * 60);
			System.out.println(userid + " " + userName);
			return "dashboard";
		} else {
			model.addAttribute("status", "bad credentials !");
			return "loginpage";
		}
	}

	/**
	 * This handler is used in logout process. It invalidate the current session if
	 * any session is available. Then redirects the login page
	 * 
	 * @param model
	 * @param session
	 * @return login page when user loges out.
	 */
	@RequestMapping("/logout")
	public String logout(Model model, HttpSession session) {
		String userid = session.getAttribute("userid").toString();
		if (userid != null) {
			session.invalidate();
			userid = null;
			// model.addAttribute("status", "successfully logged out");
		}
		return "redirect:/login";
	}
}
