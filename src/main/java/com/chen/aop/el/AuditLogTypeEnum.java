package com.chen.aop.el;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chenjianwen
 * @title: AuditLogTypeEnum
 * @description: TODO
 * @date 2023/2/111:07
 */
@Getter
@AllArgsConstructor
public enum AuditLogTypeEnum {

    /* 未分类 */
    DEFAULT(0, "未分类", AuditLogClass.DEFAULT),

    /* 用户操作日志 */
    MOD_REGISTER_PHONE(1, "修改注册手机号", AuditLogClass.USER_OPERATION_LOG),
    MOD_PASSWORD(2, "修改密码", AuditLogClass.USER_OPERATION_LOG),
    SUBMIT_REAL_NAME_AUTH(3, "提交实名认证", AuditLogClass.USER_OPERATION_LOG),
    ;

    private final Integer code;
    private final String value;
    private final AuditLogClass auditLogClass;

    @Getter
    @AllArgsConstructor
    @SuppressWarnings("all")
    enum AuditLogClass {
        DEFAULT(0, "未分类"),
        USER_OPERATION_LOG(1, "用户操作日志");
        private final Integer code;
        private final String value;
    }
}
