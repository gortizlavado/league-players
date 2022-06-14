package es.gortizlavado.league.app.exceptions;

import java.util.UUID;

public class JsonPatchCustomException extends RuntimeException {

    public JsonPatchCustomException(UUID id, String jsonPatch) {
        super(String.format("Fail to apply patch=%s with id=%s", jsonPatch, id));
    }
}
