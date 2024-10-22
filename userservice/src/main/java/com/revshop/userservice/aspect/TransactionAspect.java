package com.revshop.userservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionAspect {

	// Define the logger
	private static final Logger logger = LoggerFactory.getLogger(TransactionAspect.class);

	@Around("execution(* com.revshop.userservice.service..*(..))")
	public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
		// Begin transaction
		logger.info("Transaction started for method: {}", joinPoint.getSignature().getName());
		try {
			Object result = joinPoint.proceed();
			// Commit transaction
			logger.info("Transaction committed for method: {}", joinPoint.getSignature().getName());
			return result;
		} catch (Throwable ex) {
			// Rollback transaction
			logger.error("Transaction rolled back for method: {}", joinPoint.getSignature().getName());
			throw ex;
		}
	}
}
