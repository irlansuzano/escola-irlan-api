package br.edu.infnet.escola_irlan_api.common.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErroResponse> tratarBadRequest(BadRequestException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroResponse erroResponse = new ErroResponse(status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(erroResponse);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErroResponse> tratarErroInterno(InternalServerErrorException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResponse> tratarTipoArgumentoInvalido(MethodArgumentTypeMismatchException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String mensagem = String.format("O parâmetro '%s' deve ser do tipo '%s'", ex.getName(), 
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconhecido");
        ErroResponse erroResponse = new ErroResponse(status.value(),
                "Tipo de Argumento Inválido",
                mensagem,
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(erroResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponse> tratarIntegridadeDados(DataIntegrityViolationException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        ErroResponse erroResponse = new ErroResponse(status.value(),
                "Conflito de Dados",
                "Não foi possível completar a operação devido a uma violação de integridade (ex: registro duplicado ou dependência ativa).",
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(erroResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> tratarErroGenerico(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErroResponse erroResponse = new ErroResponse(status.value(),
                "Erro Inesperado",
                "Ocorreu um erro interno no servidor. Por favor, tente novamente mais tarde ou contate o suporte.",
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(erroResponse);
    }
}
