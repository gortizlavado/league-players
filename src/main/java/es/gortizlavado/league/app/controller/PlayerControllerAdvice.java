package es.gortizlavado.league.app.controller;

import es.gortizlavado.league.app.exceptions.PlayerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseBody
public class PlayerControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String playerNotFound(PlayerNotFoundException ex, WebRequest request) {
        return String.format("There was an error with the request '%s'. The message was: %s", request.getContextPath(), ex.getMessage());
    }
}
