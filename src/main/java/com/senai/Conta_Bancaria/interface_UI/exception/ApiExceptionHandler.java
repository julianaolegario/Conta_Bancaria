package com.senai.Conta_Bancaria.interface_UI.exception;

import ch.qos.logback.core.status.Status;
import com.senai.Conta_Bancaria.domain.exception.AutenticacaoIoTExpiradaException;
import com.senai.Conta_Bancaria.domain.exception.PagamentoInvalidoException;
import com.senai.Conta_Bancaria.domain.exception.SaldoInsuficienteException;
import com.senai.Conta_Bancaria.domain.exception.TaxaInvalidaException;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(TaxaInvalidaException.class)
    public ResponseEntity<Object> handleTaxaInvalida(TaxaInvalidaException ex, WebRequest request) {
        return buildProblemDetail(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PagamentoInvalidoException.class)
    public ResponseEntity<Object> handlePagamentoInvalido(PagamentoInvalidoException ex, WebRequest request) {
        return buildProblemDetail(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<Object> handleSaldoInsuficiente(SaldoInsuficienteException ex, WebRequest request) {
        return buildProblemDetail(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AutenticacaoIoTExpiradaException.class)
    public ResponseEntity<Object> handleAutenticacaoIoTExpirada(AutenticacaoIoTExpiradaException ex, WebRequest request) {
        return buildProblemDetail(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }



    private ResponseEntity<Object> buildProblemDetail(String message, HttpStatus status) {
        ProblemDetail problem = ProblemDetail.forStatus(status);
        problem.setTitle(status.getReasonPhrase());
        problem.setDetail(message);

        return new ResponseEntity<>(problem, status);
    }
}
