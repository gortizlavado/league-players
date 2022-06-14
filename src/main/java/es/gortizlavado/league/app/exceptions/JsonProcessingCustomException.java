package es.gortizlavado.league.app.exceptions;

import java.util.UUID;

public class JsonProcessingCustomException extends RuntimeException {

    public JsonProcessingCustomException(UUID id, String jsonNode) {
        super(String.format("Fail to processing node=%s with id=%s", jsonNode, id));
    }
}
