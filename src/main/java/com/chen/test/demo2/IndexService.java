package com.chen.test.demo2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2020/5/22
 **/
@Component
@Scope("prototype")
public class IndexService {

    @Autowired
    private UserService userService;

    public IndexService(){
        System.out.println("invoke IndexService constructor");
    }

}
