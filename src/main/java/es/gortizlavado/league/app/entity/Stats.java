package es.gortizlavado.league.app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@IdClass(StatsId.class)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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
    private UUID idPlayer;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId("idPlayer")
    @JoinColumn(name="player_id", referencedColumnName="id")
    private Player player;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Stats stats = (Stats) o;
        return season != null && Objects.equals(season, stats.season)
                && idPlayer != null && Objects.equals(idPlayer, stats.idPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(season, idPlayer);
    }
}
