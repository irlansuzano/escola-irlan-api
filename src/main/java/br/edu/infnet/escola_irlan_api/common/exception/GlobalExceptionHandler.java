package br.edu.infnet.biblioteca_irlan_api.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> tratarRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        ErroResponse erroResponse = new ErroResponse(status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(erroResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroResponse> tratarArgumentoInvalido(IllegalArgumentException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroResponse erroResponse = new ErroResponse(status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(erroResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> tratarValidacao(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        String mensagem = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErroResponse erroResponse = new ErroResponse(status.value(),
                "Erro de Validação",
                mensagem,
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(erroResponse);
    }
}
