package com.hqyj.onePlusPlus.modules.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 14:07
 * version 1.0
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    /*
     * 127.0.0.1:667/common/dashboard   ---- get
     * */
    @GetMapping("/dashboard")
    public String dashboardPage(){
        return "index";
    }

    /*
     * 127.0.0.1:667/common/403   ---- get
     * */
    @GetMapping("/403")
    public String errorPageFor403(){
        return "index";
    }

}
