package com.admin1.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {
	private String showIdFrom;
	private String showIdTo;

	@RequestMapping("/")
	public String loginAction(Model model) {
		model.addAttribute("userLogin", new UserLogin());

		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(UserLogin userLogin, BindingResult result, Model model) {

		if (userLogin.getUserName().equals("admin") && userLogin.getPassword().equals("admin")) {
			
			return "search";
		} else {

			model.addAttribute("errMsg", "Login Error see System Administator");
			return "index";
		}
		

	
	}

	public String getShowIdFrom() {
		return showIdFrom;
	}

	public void setShowIdFrom(String showIdFrom) {
		this.showIdFrom = showIdFrom;
	}

	public String getShowIdTo() {
		return showIdTo;
	}

	public void setShowIdTo(String showIdTo) {
		this.showIdTo = showIdTo;
	}

}
