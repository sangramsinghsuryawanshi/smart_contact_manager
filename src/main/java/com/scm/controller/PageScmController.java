package com.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageScmController {
	@RequestMapping("/home")
	public String home(Model model) {
		return "home";
	}

	@RequestMapping("/about")
	public String aboutPage() {
		return "about";
	}

	@RequestMapping("/service")
	public String servicePage() {
		return "service";
	}

	@RequestMapping("/contact")
	public String contactPage() {
		return "contact";
	}

	@RequestMapping("/login")
	public String loginPage() {
		return "login";
	}

	@RequestMapping("/register")
	public String registerPage() {
		return "register";
	}
}
