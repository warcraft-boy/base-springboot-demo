package com.chen.aop.el;

import java.lang.annotation.*;

/**
 * @author chenjianwen
 * @title: AuditLog
 * @description: TODO
 * @date 2023/2/111:07
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AuditLog {

    /**
     * 业务操作类型
     * {@link AuditLogTypeEnum}
     *
     * @return 业务操作类型
     */
    AuditLogTypeEnum bizType() default AuditLogTypeEnum.DEFAULT;

    /**
     * 业务备注信息：添加卖家银行卡，卡号：xxx，持卡人：xxx
     *
     * @return 业务备注信息
     */
    String remark() default "";

    /**
     * 记录业务相关的业务ID：作用根据每个bizType自定义
     *
     * @return 业务ID
     */
    String bizId() default "";
}
