package es.gortizlavado.league.app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@IdClass(StatsId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stats")
public class Stats implements Serializable {

    @Id
    @Column(name = "season_id")
    private String season;

    @Id
    @Column(name = "player_id")
    private Long idPlayer;

    private int points;

    @Column(name = "match_played")
    private int matchPlayed;

    private int goals;

    @Column(name = "goals_conceded")
    private int goalsConceded;

    @Column(name = "goals_penalty")
    private int goalsByPenalty;

    @Column(name = "yellow_cards")
    private int yellowCards;

    @Column(name = "double_yellow_cards")
    private int doubleYellowCards;

    @Column(name = "red_cards")
    private int redCards;
}
