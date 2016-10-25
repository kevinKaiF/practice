package cn.bn;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: <a href="jiangsai@ebnew.com">jiangsai </a>
 * @version: 1.0
 * @changelog: 变更日志记录
 * author                   date               comment
 * jiangsai@ebnew.com      2016/10/24             创建类
 */
@Aspect
@Component
public class AopClass {
    @Pointcut("execution(* cn.bn.entity.DataBean.setData(..))")
    public void joinPoint() {
    }

    @Before("joinPoint()")
    public void before() {
        System.out.println("before");
    }

    @After("joinPoint()")
    public void after() {
        System.out.println("after");
    }

    @Around("joinPoint()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            Object proceed = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("around");
    }

    @AfterReturning("joinPoint()")
    public void afterReturning() {
        System.out.println("afterReturning");
    }
}
