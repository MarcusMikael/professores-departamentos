
package com.example.professores.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ResponseStatusException.class)
        public ResponseEntity<Map<String, String>> tratarErro(
                        ResponseStatusException ex) {

                String mensagem = ex.getReason() != null
                                ? ex.getReason()
                                : "Ocorreu um erro na solicitação.";

                return ResponseEntity
                                .status(ex.getStatusCode())
                                .body(Map.of("mensagem", mensagem));
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<Map<String, String>> tratarJsonInvalido(
                        HttpMessageNotReadableException ex) {

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(Map.of(
                                                "mensagem",
                                                "JSON inválido. Verifique os campos enviados."));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> tratarErrosValidacao(
                        MethodArgumentNotValidException ex) {

                String mensagem = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(erro -> erro.getDefaultMessage())
                                .distinct()
                                .collect(Collectors.joining(" "));

                if (mensagem.isBlank()) {
                        mensagem = "Os dados enviados são inválidos.";
                }

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(Map.of("mensagem", mensagem));
        }
}
