package com.revshop.userservice.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BuyerServiceAspect {

	private static final Logger logger = LoggerFactory.getLogger(BuyerServiceAspect.class);

	// Log method entry
	@Before("execution(* com.revshop.userservice.service.BuyerService.*(..))")
	public void logMethodEntry(JoinPoint joinPoint) {
		logger.info("Entering method: {}", joinPoint.getSignature().getName());
	}

	// Log method exit
	@After("execution(* com.revshop.userservice.service.BuyerService.*(..))")
	public void logMethodExit(JoinPoint joinPoint) {
		logger.info("Exiting method: {}", joinPoint.getSignature().getName());
	}

	// Log exceptions
	@AfterThrowing(value = "execution(* com.revshop.userservice.service.BuyerService.*(..))", throwing = "exception")
	public void logException(JoinPoint joinPoint, Throwable exception) {
		logger.error("Exception in method: {} with message: {}", joinPoint.getSignature().getName(),
				exception.getMessage());
	}
}
