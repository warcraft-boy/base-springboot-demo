package com.chen.aop.el;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * @ClassName AuditLogAspect
 * @description:
 * @author: chenjianwen
 * @create: 2023/02/01
 **/
@Slf4j
@Aspect
@Component
public class AuditLogAspect {

    /* 用于SpEL表达式解析. */
    @SuppressWarnings("all")
    private static final SpelExpressionParser SPEL_EXPRESSION_PARSER = new SpelExpressionParser();
    /* 用于获取方法参数定义名字. */
    @SuppressWarnings("all")
    private static final DefaultParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();


    @Pointcut("@annotation(auditLog)")
    public void doAuth(AuditLog auditLog) {
    }

    @Before("doAuth(auditLog)")
    public void beforeMethod(JoinPoint joinPoint, AuditLog auditLog) {
        try {
            //获取@AuditLog注解里的remark操作内容
            String operateContent = parseExpression(auditLog.remark(), (MethodSignature) joinPoint.getSignature(), joinPoint.getArgs());
            //下面写业务代码，将操作内容，操作人记录入库
            log.info(operateContent);
            //业务代码结束
        } catch (Exception e) {
            log.error("audit log exception occur", e);
        }
    }

    public String parseExpression(String express, MethodSignature methodSignature, Object[] args) {
        if (StringUtils.isEmpty(express) || !express.contains("#")) {
            return express;
        }
        /* 获取方法形参名数组 */
        String[] paramNames = NAME_DISCOVERER.getParameterNames(methodSignature.getMethod());
        if (paramNames != null && paramNames.length > 0) {
            Expression expression = SPEL_EXPRESSION_PARSER.parseExpression(express);
            // spring的表达式上下文对象
            EvaluationContext context = new StandardEvaluationContext();
            // 给上下文赋值
            for (int i = 0; i < args.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
            Object value = expression.getValue(context);
            return value != null ? value.toString() : null;
        }
        return express;
    }
}
