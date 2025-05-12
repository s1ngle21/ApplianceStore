package org.example.appliancestore.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UsernameNotFoundException.class)
    public RedirectView usernameNotFoundExceptionErrorHandler(HttpServletRequest request, UsernameNotFoundException exception) {
        LOGGER.error("Global Exception Handler - UsernameNotFoundException for URL: " + request.getRequestURL());
        LOGGER.error("Reason: " + exception.getMessage());
        RedirectView rw = new RedirectView("/login");
        rw.addStaticAttribute("error", exception.getMessage());
        return rw;
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public RedirectView usernameAlreadyExistsExceptionErrorHandler(HttpServletRequest request, UsernameAlreadyExistsException exception) {
        LOGGER.error("Global Exception Handler - UsernameAlreadyExistsException for URL: " + request.getRequestURL());
        LOGGER.error("Reason: " + exception.getMessage());
        RedirectView rw = new RedirectView("/register");
        rw.addStaticAttribute("error", exception.getMessage());
        return rw;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public RedirectView badCredentialsExceptionErrorHandler(HttpServletRequest request, BadCredentialsException exception) {
        LOGGER.error("Global Exception Handler - BadCredentialsException for URL: " + request.getRequestURL());
        LOGGER.error("Reason: " + exception.getMessage());
        RedirectView rw = new RedirectView("/login");
        rw.addStaticAttribute("error", exception.getMessage());
        return rw;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value= HttpStatus.FORBIDDEN)
    public ModelAndView forbiddenErrorHandler(HttpServletRequest request, AccessDeniedException exception) {
        LOGGER.error("Global Exception Handler - Access Denied for User: [" + request.getUserPrincipal().getName() +
                "] URL: " + request.getRequestURL());
        LOGGER.error("Reason: " + exception.getMessage());
        return getModelAndView(request, HttpStatus.FORBIDDEN, exception);
    }


    @ExceptionHandler(NullEntityReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNullEntityReferenceException(HttpServletRequest request, NullEntityReferenceException exception) {
        LOGGER.error("Global Exception Handler - Null Entity for URL: " + request.getRequestURL());
        LOGGER.error("Reason: " + exception.getMessage());
        return getModelAndView(request, HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleEntityNotFoundException(HttpServletRequest request, EntityNotFoundException exception) {
        LOGGER.error("Global Exception Handler - Entity Not found for URL: " + request.getRequestURL());
        LOGGER.error("Reason: " + exception.getMessage());
        return getModelAndView(request, HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(PageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handlePageException(HttpServletRequest request, PageException exception) {
        LOGGER.error("Global Exception Handler - Page Error for URL: " + request.getRequestURL());
        LOGGER.error("Reason: " + exception.getMessage());
        return getModelAndView(request, HttpStatus.BAD_REQUEST, exception);
    }

    private ModelAndView getModelAndView(HttpServletRequest request, HttpStatus httpStatus, Exception exception) {
        LOGGER.error("Exception raised by User = {" + request.getUserPrincipal().getName() + "} :: {"
                + exception.getMessage() + "} :: URL = {" + request.getRequestURL() + "}");
        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.addObject("code", httpStatus.value() + " / " + httpStatus.getReasonPhrase());
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }
}

