package com.example.demo;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Controller {

	@GetMapping("/")
	public ModelAndView getIndex() {
		
		return new ModelAndView("index");
	}

	 @GetMapping("/logar")
	 public ModelAndView user(@AuthenticationPrincipal OAuth2User principal, Model model) {
		 
		 model.addAttribute("nome", principal.getAttribute("name"));
		 model.addAttribute("email", principal.getAttribute("email"));
		 return new ModelAndView("logado");
		 
	 }
}

