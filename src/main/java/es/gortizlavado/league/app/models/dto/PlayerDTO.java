package es.gortizlavado.league.app.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerDTO {

    private String id;

    private String name;

    private String lastname;

    private String surname;

    private String fullName;

    private String dateOfBirthday;

    private Integer age;

    private String position;

    private String team;

    private String status;

    private Integer points;

    private Integer matchPlayed;

    private Integer goals;

    private Integer goalsConceded;

    private Integer goalsByPenalty;

    private Integer yellowCards;

    private Integer doubleYellowCards;

    private Integer redCards;

    private String season;
}
