package com.qhgrain.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 欢迎信息控制器
 */
@RestController
public class IndexController {

    @RequestMapping("/")
    public String helloWorld(){
        return "Hello World!";
    }
}
