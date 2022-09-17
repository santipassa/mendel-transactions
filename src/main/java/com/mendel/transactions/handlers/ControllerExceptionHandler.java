package com.mendel.transactions.handlers;

import com.mendel.transactions.dto.ApiErrorDTO;
import com.mendel.transactions.exceptions.TransactionNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> transactionNotFoundExceptionHandler(TransactionNotFoundException ex) {
        ApiErrorDTO apiErrorDTO = ApiErrorDTO.builder().error("transaction_not_found").message(ex.getMessage()).status(HttpStatus.NOT_FOUND.value()).build();
        return ResponseEntity.status(apiErrorDTO.getStatus()).body(apiErrorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
        ApiErrorDTO apiErrorDTO = ApiErrorDTO.builder().error("invalid_body").message(message).status(HttpStatus.BAD_REQUEST.value()).build();
        return ResponseEntity.status(apiErrorDTO.getStatus()).body(apiErrorDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorDTO> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        ApiErrorDTO apiErrorDTO = ApiErrorDTO.builder().error("invalid_body").message(ex.getMessage()).status(HttpStatus.BAD_REQUEST.value()).build();
        return ResponseEntity.status(apiErrorDTO.getStatus()).body(apiErrorDTO);
    }
}
