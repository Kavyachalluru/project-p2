package com.revshop.userservice.aspect;

import java.time.LocalDateTime;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.revshop.userservice.entity.Buyer;

@Aspect
@Component
public class AuditAspect {

	// Define the logger
	private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);

	@After("execution(* com.revshop.userservice.service.BuyerService.updateUser(..))")
	public void logUpdateAudit(JoinPoint joinPoint) {
		Buyer buyer = (Buyer) joinPoint.getArgs()[0];
		logger.info("Audit Log: Buyer with email {} was updated at {}", buyer.getEmail(), LocalDateTime.now());
		// Save audit record to the database if needed
	}
}
