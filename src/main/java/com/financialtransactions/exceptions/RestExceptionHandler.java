package com.financialtransactions.exceptions;

import com.financialtransactions.dtos.ApiError;
import com.financialtransactions.enumerations.MessageCode;
import com.financialtransactions.helper.MessageHelper;
import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
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
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.getReasonPhrase(), businessException.getMessage(), businessException.getDetails());
        return getResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception exception) {
        exception.printStackTrace();
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

    @ExceptionHandler(GenericCustomException.class)
    public ResponseEntity<ApiError> handlerGenerateTokenExceptions(GenericCustomException genericCustomException) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), genericCustomException.getMessage(), genericCustomException.getDetails());
        return getResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExtractTokenClaimException.class)
    public ResponseEntity<ApiError> handlerExtractTokenExceptions(ExtractTokenClaimException extractTokenClaimException) {
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN.getReasonPhrase(), extractTokenClaimException.getMessage(), extractTokenClaimException.getDetails());
        return getResponseEntity(apiError, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ApiError> handlerHttpMessageNotReadableException(HttpMessageConversionException httpMessageConversionException) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.getReasonPhrase(), this.messageHelper.getMessage(MessageCode.BAD_REQUEST), "");
        return getResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handlerAuthenticationServiceException(AuthenticationException authenticationException) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED.getReasonPhrase(), authenticationException.getMessage(), this.messageHelper.getMessage(MessageCode.BAD_CREDENTIALS));
        return getResponseEntity(apiError, HttpStatus.UNAUTHORIZED);
    }


    private ResponseEntity<ApiError> getResponseEntity(ApiError apiError, HttpStatus httpStatus) {
        return new ResponseEntity<>(apiError, httpStatus);
    }
}
