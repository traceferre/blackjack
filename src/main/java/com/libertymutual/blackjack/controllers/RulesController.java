package com.libertymutual.blackjack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RulesController {
	
	@GetMapping("home/rules")
	public String Rules()
	{
		return "/home/rules";
	}

}
