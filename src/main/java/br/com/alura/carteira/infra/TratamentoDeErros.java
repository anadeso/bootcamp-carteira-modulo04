package br.com.alura.carteira.infra;

import br.com.alura.carteira.dto.Erro400Dto;
import br.com.alura.carteira.dto.Erro500Dto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TratamentoDeErros {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public List<Erro400Dto> tratarError400(MethodArgumentNotValidException ex) {
        return ex
                .getFieldErrors()
                .stream()
                .map(erro -> new Erro400Dto(erro.getField(), erro.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Erro500Dto tratarError500(Exception ex, HttpServletRequest req) {
        return new Erro500Dto(
                LocalDateTime.now(),
                ex.getClass().toString(),
                ex.getMessage(),
                req.getRequestURI());
    }

    @ExceptionHandler({EntityNotFoundException.class, EmptyResultDataAccessException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public void tratarError400() {
    }
}
