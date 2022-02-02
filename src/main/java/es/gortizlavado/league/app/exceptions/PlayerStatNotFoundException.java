package es.gortizlavado.league.app.exceptions;

public class PlayerStatNotFoundException extends RuntimeException {

    public PlayerStatNotFoundException(Long id, String season) {
        super(String.format("Player with id=%s-%s not found", season, id));
    }
}
