package com.senai.Conta_Bancaria.interface_UI.exception;

import com.senai.Conta_Bancaria.domain.exception.ContaMesmoTipoException;
import com.senai.Conta_Bancaria.domain.exception.ValoresNegativosException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ValoresNegativosException.class)
    public ResponseEntity<String> handleResourceNotFound (ValoresNegativosException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ContaMesmoTipoException.class)
    public ResponseEntity<String> handleContaMesmoTipo (ContaMesmoTipoException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
