package com.revshop.userservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProductServiceAspect {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceAspect.class);

	// Around advice to log method execution time for all methods in ProductService
	@Around("execution(* com.revshop.userservice.service.ProductService.*(..))")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();

		logger.info("Method {} is starting", joinPoint.getSignature());

		Object result = joinPoint.proceed(); // Proceed with the method execution

		long timeTaken = System.currentTimeMillis() - startTime;
		logger.info("Method {} finished in {} ms", joinPoint.getSignature(), timeTaken);

		return result;
	}
}
