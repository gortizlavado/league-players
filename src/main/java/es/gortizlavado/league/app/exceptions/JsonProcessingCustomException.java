package es.gortizlavado.league.app.exceptions;

public class JsonProcessingCustomException extends RuntimeException {

    public JsonProcessingCustomException(Long id, String jsonNode) {
        super(String.format("Fail to processing node=%s with id=%s", jsonNode, id));
    }
}
