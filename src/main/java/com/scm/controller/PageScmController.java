package com.scm.controller;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.forms.UserForms;
import com.scm.helpers.MessageSender;
import com.scm.helpers.MessageType;
import com.scm.model.Users;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageScmController {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PageScmController.class);

	private UserService userservice;

	public PageScmController(UserService userservice) {
		this.userservice = userservice;
	}

	@RequestMapping("/")
	public String index(Model model) {
		return "redirect:/home";
	}

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

	@GetMapping("/register")
	public String registerPage(Model model) {
		UserForms userform = new UserForms();
		model.addAttribute("userform", userform);
		return "register";
	}
	@PostMapping("/register-form")
	public String registerProcessFrom(@Valid @ModelAttribute("userform") UserForms userform, BindingResult result, HttpSession session) {
		try {
			if(result.hasErrors())
			{
				return"register";
			}
            // Create a new user object and populate fields
            Users user1 = new Users();
            user1.setUserName(userform.getUserName());
            user1.setUserEmail(userform.getUserEmail());
            user1.setPassword(userform.getPassword());
            user1.setAbout(userform.getAbout());
            user1.setPhoneNumber(userform.getPhoneNumber());
            user1.setProfilePic("default-profile-pic.png"); // Default profile picture

            // Save user to database
            userservice.saveUser(user1);

            // On success, set a success message
            MessageSender message = MessageSender.builder()
                    .content("Registration Successful!")
                    .type(MessageType.green) // Success: green
                    .build();
            session.setAttribute("message", message);

        } catch (Exception e) {
            // On failure, set a failure message
            MessageSender message = MessageSender.builder()
                    .content("Registration Failed! Please try again.")
                    .type(MessageType.red) // Failure: red
                    .build();
            session.setAttribute("message", message);
        }

        // Redirect back to the registration page
        return "redirect:/register";
    }
}
