package com.lvyongwenhouzi.architecture.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * <b>切面：日志</b>
 *
 * 通过{@link com.lvyongwenhouzi.server.java.TProxy}已经了解到动态代理的实现方式是可以做到方法调用的前、后、异常等等进行处理钩子；
 * 而Spring的AOP正好和动态代理的机制相似，所以可以肯定的推测AOP使用的动态代理技术。
 *
 * 具体看下Spring是如何基于注解形式把定义好的前置、后置、环绕等等织入到应用中：
 *
 *
 *
 *
 *
 * @author 11114396 lvyongwen
 * @date 2021-08-30 16:57
 * @since 1.0
 */
@Aspect
@Slf4j
@Component
public class LogAOP {

    /**
     * 切点：针对com.lvyongwenhouzi.architecture.spring目录下进行拦截
     */
    @Pointcut("execution(* com.lvyongwenhouzi.architecture.spring..*.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object log(ProceedingJoinPoint invocation) throws Throwable {

        log.info("Around before log....");

        Object proceed = invocation.proceed();

        log.info("Around after log....");

        return proceed;
    }


}
