package es.gortizlavado.league.app.exceptions;

public class JsonPatchCustomException extends RuntimeException {

    public JsonPatchCustomException(Long id, String jsonPatch) {
        super(String.format("Fail to apply patch=%s with id=%s", jsonPatch, id));
    }
}
