package com.scm.controller;

import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.model.Users;

@Controller
public class PageScmController {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PageScmController.class);
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
	@PostMapping("/register-form")
	public String registerProcessFrom(@ModelAttribute Users users) {
		logger.info(users.toString());
		return "";
	}
}
