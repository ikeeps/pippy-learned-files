package com.ikeeps.pippy.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikeeps.pippy.accountservice.TestRemoteConfig;

@RestController
@RequestMapping("/demo")
public class DemoController {
	
	private TestRemoteConfig config;
	
	@Autowired
	public void setConfig(TestRemoteConfig con) {
		this.config = con;
	}
	
	@RequestMapping("/refreshMsg")
	public String msg() {
		return config.getMsg();
	}
	
}
