package com.yl.rocketmq.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {

	@GetMapping("/sayHello")
    public String sayHello(){

        return "hello";
    }
	
	@GetMapping("/testConn")
    public String testConn(){

        return "connection success!";
    }
}
