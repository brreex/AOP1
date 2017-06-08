package cs544.aop1;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

@Aspect
public class EmailApplicationAdvice {
	
	@After("execution(* cs544.aop1.EmailSender.sendEmail(..) ) && args(email,message)")
	public void traceEmailSending(JoinPoint joinPoint,String email,String message){
		System.out.println(new Date()+"  method= "+joinPoint.getSignature().getName());
		System.out.println("email= "+email);
		System.out.println("message= "+message);
		
		EmailSender emailSender = (EmailSender) joinPoint.getTarget();
		
		System.out.println("outgoing mail server= "+emailSender.getOutgoingMailServer());
	}
	
	@Around("execution(* cs544.aop1.*DAO*.*(..))")
	public Object invoke (ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		
		StopWatch stopWatch = new StopWatch();
		
		stopWatch.start(proceedingJoinPoint.getSignature().getName());
		Object object = proceedingJoinPoint.proceed();		
		stopWatch.stop();
		
		long totalTimeTaken = stopWatch.getLastTaskTimeMillis();
		System.out.println("time it took to save = "+totalTimeTaken);
		
		return object;
	}
}
