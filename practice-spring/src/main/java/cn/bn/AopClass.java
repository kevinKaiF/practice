package cn.bn;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

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
