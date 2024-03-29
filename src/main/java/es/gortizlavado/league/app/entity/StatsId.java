package es.gortizlavado.league.app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//See https://www.baeldung.com/jpa-composite-primary-keys
public class StatsId implements Serializable {

    private String season;

    private UUID idPlayer;
}
