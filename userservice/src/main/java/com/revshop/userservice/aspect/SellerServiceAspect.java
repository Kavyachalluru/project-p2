package com.revshop.userservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SellerServiceAspect {

	private static final Logger logger = LoggerFactory.getLogger(SellerServiceAspect.class);

	// This pointcut targets all methods in the SellerService class
	@Around("execution(* com.revshop.userservice.service.SellerService.*(..))")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		logger.info("Method {} is starting", joinPoint.getSignature());

		Object proceed = joinPoint.proceed(); // Proceed with the method call

		long timeTaken = System.currentTimeMillis() - startTime;
		logger.info("Method {} finished in {} ms", joinPoint.getSignature(), timeTaken);

		return proceed;
	}

}
