package com.example.demo.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceMetricsAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceMetricsAspect.class);

    // Define a pointcut expression to match methods related to book additions and updates
    @Around("execution(* com.example.demo.service.BookService.addBook(..)) || " +
            "execution(* com.example.demo.service.BookService.updateBook(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // Proceed with the method execution
        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;

        // Log the execution time
        logger.info("Method {} executed in {} ms", joinPoint.getSignature(), executionTime);

        return proceed;
    }
}
