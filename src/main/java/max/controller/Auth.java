package max.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Auth {

	@GetMapping("/")
	public String index(){
		return "auth/login";
	}

	@GetMapping("/login")
	public String loginPage(){
		return "auth/login";
	}

}