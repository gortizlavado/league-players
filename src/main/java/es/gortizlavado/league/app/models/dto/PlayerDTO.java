package es.gortizlavado.league.app.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerDTO playerDTO = (PlayerDTO) o;

        if (!Objects.equals(id, playerDTO.id)) return false;
        if (!Objects.equals(name, playerDTO.name)) return false;
        if (!Objects.equals(lastname, playerDTO.lastname)) return false;
        if (!Objects.equals(surname, playerDTO.surname)) return false;
        if (!Objects.equals(fullName, playerDTO.fullName)) return false;
        if (!Objects.equals(dateOfBirthday, playerDTO.dateOfBirthday)) return false;
        if (!Objects.equals(position, playerDTO.position)) return false;
        if (!Objects.equals(team, playerDTO.team)) return false;
        if (!Objects.equals(status, playerDTO.status)) return false;
        if (!Objects.equals(points, playerDTO.points)) return false;
        if (!Objects.equals(matchPlayed, playerDTO.matchPlayed)) return false;
        if (!Objects.equals(goals, playerDTO.goals)) return false;
        if (!Objects.equals(goalsConceded, playerDTO.goalsConceded)) return false;
        if (!Objects.equals(goalsByPenalty, playerDTO.goalsByPenalty)) return false;
        if (!Objects.equals(yellowCards, playerDTO.yellowCards)) return false;
        if (!Objects.equals(doubleYellowCards, playerDTO.doubleYellowCards)) return false;
        if (!Objects.equals(redCards, playerDTO.redCards)) return false;
        return Objects.equals(season, playerDTO.season);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (dateOfBirthday != null ? dateOfBirthday.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (team != null ? team.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (points != null ? points.hashCode() : 0);
        result = 31 * result + (matchPlayed != null ? matchPlayed.hashCode() : 0);
        result = 31 * result + (goals != null ? goals.hashCode() : 0);
        result = 31 * result + (goalsConceded != null ? goalsConceded.hashCode() : 0);
        result = 31 * result + (goalsByPenalty != null ? goalsByPenalty.hashCode() : 0);
        result = 31 * result + (yellowCards != null ? yellowCards.hashCode() : 0);
        result = 31 * result + (doubleYellowCards != null ? doubleYellowCards.hashCode() : 0);
        result = 31 * result + (redCards != null ? redCards.hashCode() : 0);
        result = 31 * result + (season != null ? season.hashCode() : 0);
        return result;
    }
}
