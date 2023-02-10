package com.chen.aop.el;

import org.springframework.web.bind.annotation.*;

/**
 * @ClassName AspectController
 * @description:
 * @author: chenjianwen
 * @create: 2023/02/01
 **/
@RestController
@RequestMapping("/aspect")
public class AspectController {


    @GetMapping("/test1")
    @AuditLog(bizType = AuditLogTypeEnum.MOD_REGISTER_PHONE, remark = "修改注册手机号")
    public String test1(){
        return "end";
    }

    @GetMapping("/test2/{mobile}/{password}")
    @AuditLog(bizType = AuditLogTypeEnum.MOD_PASSWORD, remark = "'修改密码，用户名: ' + #mobile + '; 密码: ' + #password")
    public String test2(@PathVariable("mobile") String mobile, @PathVariable("password") String password){
        return "end";
    }

    @PostMapping("/test3")
    @AuditLog(bizType = AuditLogTypeEnum.SUBMIT_REAL_NAME_AUTH, remark = "'提交实名认证，用户名: '+ #user.userName +';身份证号：'+ #user.idCard")
    public String test3(@RequestBody AspectUser user){
        return "end";
    }
}
