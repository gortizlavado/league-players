package es.gortizlavado.league.app.entity;

import es.gortizlavado.league.app.models.enums.Position;
import es.gortizlavado.league.app.models.enums.Status;
import es.gortizlavado.league.app.models.enums.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "players")
public class Player implements Serializable {

    @Id
    private Long id;

    private String name;

    private String lastname;

    private String surname;

    @Column(name = "date_birthday")
    private LocalDate dateOfBirthday;

    @Column(name = "player_position")
    private Position position;

    private Team team;

    private Status status;

}
