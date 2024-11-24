package com.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PageScmController 
{
	@RequestMapping("/home")
	public String home(Model model){
		model.addAttribute("name", "Sangramsingh");
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
	
}
