package cn.itcast.web.action.privilege;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Interceptor {
	@Pointcut("execution(java.lang.String cn.itcast.web.action..*.*())")
	private void actionMethod(){}
	
	@Around("actionMethod()")
	public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("拦截到了"+ pjp.getSignature().getName() + "方法");
		return pjp.proceed();
	}
}
