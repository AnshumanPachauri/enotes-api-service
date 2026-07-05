package com.ap.enotes_api_service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

	@Before("execution(* com.ap.enotes_api_service.controller..*(..))")
	public void beforeController(JoinPoint joinPoint) 
	{
		
		Signature signature = joinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		String methodName = signature.getName();
		log.info("Calling :: {} :: {}()", className, methodName);
	}
	
	@After("execution(* com.ap.enotes_api_service.controller..*(..))")
	public void afterController(JoinPoint joinPoint) 
	{
		
		Signature signature = joinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		String methodName = signature.getName();
		log.info("Ending :: {} :: {}()", className, methodName);
	}
	
	@Around("execution(* com.ap.enotes_api_service.service..*(..))")
	public Object joinPointService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable 
	{
		Signature signature = proceedingJoinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		String methodName = signature.getName();
		log.info("Calling :: {} :: {}()", className, methodName);
		long start = System.currentTimeMillis();
		Object result = proceedingJoinPoint.proceed();
		long duration = System.currentTimeMillis()-start;
		log.info("Ending :: {} :: {}() :: {} ms", className, methodName, duration);
		return result;
	}
	
	@Around("execution(* com.ap.enotes_api_service.service.impl..*(..))")
	public Object joinPointServiceImpl(ProceedingJoinPoint proceedingJoinPoint) throws Throwable 
	{
		Signature signature = proceedingJoinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		String methodName = signature.getName();
		log.info("Calling :: {} :: {}()", className, methodName);
		long start = System.currentTimeMillis();
		Object result = proceedingJoinPoint.proceed();
		long duration = System.currentTimeMillis()-start;
		log.info("Ending :: {} :: {}() :: {} ms", className, methodName, duration);
		return result;
	}
	
}
