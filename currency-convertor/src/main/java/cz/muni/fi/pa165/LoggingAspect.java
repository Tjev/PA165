package cz.muni.fi.pa165;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

import javax.inject.Named;

@Aspect
//@Named
public class LoggingAspect {

    @Around("execution(public * *(..))")
    public void log(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        pjp.proceed();
        stopWatch.stop();
        System.err.println(String.format("A call to method %s took %s.", pjp.getSignature().getName(), stopWatch.shortSummary()));
    }
}
