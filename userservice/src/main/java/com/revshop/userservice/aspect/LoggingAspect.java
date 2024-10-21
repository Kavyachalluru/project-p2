package com.revshop.userservice.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	// Pointcut to match all service methods in com.revshop.userservice.service
	// package
	@Pointcut("execution(* com.revshop.userservice.service.*.*(..))")
	public void allServiceMethods() {
	}

	// Log before method execution
	@Before("allServiceMethods()")
	public void logBeforeMethod() {
		logger.info("Method execution starts");
	}

	// Log after method returns successfully
	@AfterReturning(pointcut = "allServiceMethods()", returning = "result")
	public void logAfterReturning(Object result) {
		logger.info("Method executed successfully, Result: {}", result);
	}

	// Log after method throws an exception
	@AfterThrowing(pointcut = "allServiceMethods()", throwing = "exception")
	public void logAfterThrowing(Exception exception) {
		logger.error("Exception occurred: {}", exception.getMessage());
	}

	// Log around method execution (before and after execution)
	@Around("allServiceMethods()")
	public Object logAroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		logger.info("Method execution started: {}", joinPoint.getSignature().toShortString());

		try {
			Object result = joinPoint.proceed(); // Proceed to the method execution
			long timeTaken = System.currentTimeMillis() - startTime;
			logger.info("Method execution completed: {}, Time taken: {} ms", joinPoint.getSignature().toShortString(),
					timeTaken);
			return result;
		} catch (Throwable throwable) {
			logger.error("Error in method: {}", joinPoint.getSignature().toShortString());
			throw throwable; // Rethrow the exception
		}
	}
}
