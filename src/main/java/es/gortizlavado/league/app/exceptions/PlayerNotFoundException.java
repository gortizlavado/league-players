package es.gortizlavado.league.app.exceptions;

import java.util.UUID;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(UUID id) {
        super(String.format("Player with id=%s not found", id));
    }
}
