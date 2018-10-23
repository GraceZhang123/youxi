package com.zq.youxi.controller;

import com.zq.youxi.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    private TestService service;

    @RequestMapping("/helloWorld")
    @ResponseBody
    public String helloWorld() {
        String s = service.sayHello();
        return s;
    }

}
