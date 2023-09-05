package com.financialtransactions.exceptions;

import com.financialtransactions.dtos.ApiError;
import com.financialtransactions.enumerations.MessageCode;
import com.financialtransactions.helper.MessageHelper;
import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    private MessageHelper messageHelper;

    public RestExceptionHandler(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessException(BusinessException businessException) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.getReasonPhrase(), businessException.getMessage(), "");
        return getResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException() {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), this.messageHelper.getMessage(MessageCode.INTERNAL_SERVER_ERROR), "");
        return getResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(ResourceException.class)
    public ResponseEntity<ApiError> handleResourceException(ResourceException resourceException) {
        ApiError apiError = new ApiError(resourceException.getHttpStatus().getReasonPhrase(), resourceException.getMessage(), resourceException.getDetails());
        return getResponseEntity(apiError, resourceException.getHttpStatus());
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        constraintViolationException.getLocalizedMessage();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.getReasonPhrase(), this.messageHelper.getMessage(MessageCode.INTERNAL_SERVER_ERROR), "");
        return getResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CustomAuthorizationException.class)
    public ResponseEntity<ApiError> handlerCustomAuthenticationExceptions(CustomAuthorizationException customAuthorizationException) {
        ApiError apiError = new ApiError(customAuthorizationException.getHttpStatus().getReasonPhrase(), customAuthorizationException.getMessage(), customAuthorizationException.getDetails());
        return getResponseEntity(apiError, customAuthorizationException.getHttpStatus());
    }
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ApiError> handlerServletException(ServletException servletException) {
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN.getReasonPhrase(), this.messageHelper.getMessage(MessageCode.INTERNAL_SERVER_ERROR), "");
        return getResponseEntity(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(GenerateTokenException.class)
    public ResponseEntity<ApiError> handlerGenerateTokenExceptions(GenerateTokenException generateTokenException) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), generateTokenException.getMessage(), generateTokenException.getDetails());
        return getResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ApiError> getResponseEntity(ApiError apiError, HttpStatus httpStatus) {
        return new ResponseEntity<>(apiError, httpStatus);
    }
}
