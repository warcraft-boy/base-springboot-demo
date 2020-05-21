package com.chen.test;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2020/5/18
 **/
public class UserController {

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void bbq(String name){
        System.out.println(name + "eat bbq");
    }
}
