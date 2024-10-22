package com.revshop.userservice.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

	// Define a logger
	private static final Logger logger = LoggerFactory.getLogger(SecurityAspect.class);

	@Before("execution(* com.revshop.userservice.service.BuyerService.updateUser(..))")
	public void checkPermission(JoinPoint joinPoint) {
		// Perform security checks here (e.g., check JWT token or session)
		logger.info("Checking permissions for method: {}", joinPoint.getSignature().getName());
	}
}
