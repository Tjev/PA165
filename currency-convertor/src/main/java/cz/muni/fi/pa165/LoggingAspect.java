package cz.muni.fi.pa165;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

import javax.inject.Named;

@Aspect
@Named
public class LoggingAspect {

    @Around("execution(public * *(..))")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        Object returnvalue = pjp.proceed();
        stopWatch.stop();

        System.err.printf("A call to method %s took %s ms.\n",
                pjp.getSignature().getName(), stopWatch.getLastTaskTimeMillis());

        return returnvalue;
    }
}
