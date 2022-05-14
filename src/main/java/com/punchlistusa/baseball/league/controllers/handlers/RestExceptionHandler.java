package com.punchlistusa.baseball.league.controllers.handlers;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.punchlistusa.baseball.league.dto.CustomError;
import com.punchlistusa.baseball.league.exceptions.ApplicationException;
import com.punchlistusa.baseball.league.exceptions.BusinessException;
import com.punchlistusa.baseball.league.exceptions.ConflictException;
import com.punchlistusa.baseball.league.exceptions.NotFoundException;
import com.punchlistusa.baseball.league.exceptions.TechnicalException;
import lombok.extern.log4j.Log4j2;

/**
 * 
 * @ClassName: RestExceptionHandler
 * @author SKG
 * @since: 2022-02-03
 * @version 1.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Log4j2
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(UnrecognizedPropertyException.class)
  protected ResponseEntity<Object> handleConflictException(final UnrecognizedPropertyException ex) {
    return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
  }


  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status,
      final WebRequest request) {
    final String error = "Malformed JSON request";
    return buildResponseEntity(HttpStatus.BAD_REQUEST, error, ex);
  }


  @ExceptionHandler(NotFoundException.class)
  protected ResponseEntity<Object> handleNotFoundException(final NotFoundException ex) {
    return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
  }


  @ExceptionHandler(ConflictException.class)
  protected ResponseEntity<Object> handleConflictException(final ConflictException ex) {
    return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), ex);
  }


  @ExceptionHandler(TechnicalException.class)
  protected ResponseEntity<Object> handleTechnicalException(final TechnicalException ex) {
    return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
  }


  @ExceptionHandler(BusinessException.class)
  protected ResponseEntity<Object> handleBusinessException(final BusinessException ex) {
    return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
  }


  @ExceptionHandler(ApplicationException.class)
  protected ResponseEntity<Object> handleBusinessException(final ApplicationException ex) {
    return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
  }


  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status,
      final WebRequest request) {
    final List<String> errors = ex.getBindingResult().getFieldErrors().stream()
        .map(x -> x.getDefaultMessage()).collect(Collectors.toList());
    final CustomError customError = CustomError.builder().status(status).errors(errors).build();

    return new ResponseEntity<>(customError, headers, status);

  }


  private ResponseEntity<Object> buildResponseEntity(final HttpStatus status, final String error,
      final Exception ex) {
    log.error("Error handler: {}", ex.getMessage(), ex);
    final CustomError customErrorType = CustomError.builder().status(status).build();
    customErrorType.addError(error);
    return new ResponseEntity<>(customErrorType, status);
  }

}
