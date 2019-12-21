package com.scdq.manager.controller;

import com.scdq.manager.model.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scdq.manager.service.SystemService;

@RestController
@RequestMapping("/sys")
public class SystemController {
	
	@Autowired
	private SystemService systemService;
		
	@RequestMapping("/login")
	public ResponseData login(String account, String password) {
		return systemService.login(account, password);
	}

	@RequestMapping("/logout")
	public ResponseData logout() {
		return systemService.logout();
	}
	
	@RequestMapping("/modifyPassword")
	public ResponseData modifyPassword(String original, String now) {
		return systemService.modifyPassword(original, now);
	}
}
