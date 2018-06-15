package test.dynamic.datasource.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author wangbx
 * Created by wangbx on 2018/6/14.
 */
@Aspect
//@Component
public class DataSourceAspect {

//    @Pointcut(value = "execution(* test.dynamic.datasource.service.*.*(..))")
//    public void pointCut(){}
//
//    @Around(value = "pointCut()")
//    public Object around(ProceedingJoinPoint pjd){
//        pjd.proceed();
//    }
}
