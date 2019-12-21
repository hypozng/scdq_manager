package com.scdq.manager.sys.controller;

import com.scdq.manager.common.ResponseData;
import com.scdq.manager.sys.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scdq.manager.sys.service.SystemService;

import java.util.List;

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

    @RequestMapping("/getMenus")
    public ResponseData<List<Menu>> getMenus() {
        return systemService.getMenus();
    }
}
