package es.gortizlavado.league.app.exceptions;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(Long id) {
        super(String.format("Player with id=%s not found", id));
    }
}
