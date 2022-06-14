package es.gortizlavado.league.app.exceptions;

import java.util.UUID;

public class PlayerStatNotFoundException extends RuntimeException {

    public PlayerStatNotFoundException(UUID id, String season) {
        super(String.format("Player with id=%s-%s not found", season, id));
    }
}
