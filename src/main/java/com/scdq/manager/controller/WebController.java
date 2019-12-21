package com.scdq.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class WebController {
	
	@RequestMapping("/")
	public String toIndex() {
		return "home";
	}
	
	@RequestMapping("/login")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping("/home")
	public String toHome() {
		return "home";
	}
	
	@RequestMapping("/home/{path}")
	public String toHome(@PathVariable String path) {
		return path;
	}

	@RequestMapping("/home/{path1}/{path2}")
	public String toHome(@PathVariable String path1, @PathVariable String path2) {
		return path1 + "/" + path2;
	}
}
