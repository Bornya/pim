package com.bornya.pim.controller;

import com.bornya.pim.entity.User;
import com.bornya.pim.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/userinfo")
    public ModelAndView userInfo(String uuid) throws Exception {
        //获取用户信息
        User user = userInfoService.getUserInfo(uuid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userinfo");
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @GetMapping("/test")
    public ModelAndView test() throws Exception {
        //获取用户信息
//        User user = userInfoService.getUserInfo(uuid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("test");
        User user = new User();
        user.setEmail("asdadadda");
        user.setTatler_Digest_New(true);
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @PostMapping("/saveUserInfo")
    public String saveUserInfo(User user) throws IOException {
        System.out.println(user.getFirst_name());
        return userInfoService.updateUserInfo(user);
    }

    //设置回调接口用于获取登录之后的token
    @GetMapping("/adminLogin")
    public String adminLogin() throws IOException {
        String result = userInfoService.getCode();
        return result;
    }
    //回调接口，用户获取token
    @GetMapping("/getToken")
    public String getToken(String code) throws IOException {
        String result = userInfoService.getToken(code);
        return result;
    }

}
