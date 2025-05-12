package org.example.appliancestore.aspect;


import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggingServices {

    private static final Logger LOGGER = Logger.getLogger(LoggingServices.class);

    @Pointcut("@annotation(org.example.appliancestore.aspect.Loggable)")
    public void executeLogging() {
    }


    @Around("executeLogging()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object result;
        Object[] args = joinPoint.getArgs();
        List<Object> argsList = new ArrayList<>();

        for (Object arg : args) {
            if (!(arg instanceof BindingResult)) {
                argsList.add(arg);
            }
            if (arg instanceof BindingResult bindingResult && bindingResult.hasErrors()) {
                String errors = formatBindingResultArgsForError(bindingResult);
                LOGGER.warn(String.format("Validation errors in %s.%s: %s", className, methodName, errors));
                return joinPoint.proceed();
            }
        }
        LOGGER.info(String.format("Executing %s.%s with args: %s", className, methodName, argsList.toString()));
        result = joinPoint.proceed();
        LOGGER.info("Execution successful in: " + className + "." + methodName);
        return result;
    }

    private String formatBindingResultArgsForError(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(fieldError -> String.format("Field '%s' rejected value '%s': %s",
                        fieldError.getField(),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage()))
                .collect(Collectors.joining("; "));
    }
}
