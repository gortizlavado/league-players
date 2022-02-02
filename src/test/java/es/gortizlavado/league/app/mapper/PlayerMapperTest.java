package es.gortizlavado.league.app.mapper;

import es.gortizlavado.league.app.entity.Player;
import es.gortizlavado.league.app.entity.Stats;
import es.gortizlavado.league.app.models.dto.PlayerDTO;
import es.gortizlavado.league.app.models.enums.Position;
import es.gortizlavado.league.app.models.enums.Status;
import es.gortizlavado.league.app.models.enums.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class PlayerMapperTest {

    @Autowired
    private PlayerMapper playerMapper;

    @Test
    void shouldDoMapperFromStat() {
        final Stats stats = Stats.builder()
                .season("season")
                .idPlayer(0L)
                .player(Player.builder()
                        .id(0L)
                        .name("name")
                        .lastname("last-name")
                        .surname("surname")
                        .position(Position.MIDFIELDER)
                        .team(Team.NO_TEAM)
                        .status(Status.ACTIVE)
                        .build())
                .points(10)
                .matchPlayed(1)
                .goals(1)
                .goalsByPenalty(0)
                .yellowCards(1)
                .doubleYellowCards(0)
                .redCards(0)
                .build();
        final PlayerDTO expectedPlayerDTO = PlayerDTO.builder()
                .id("0")
                .season("season")
                .name("name")
                .lastname("last-name")
                .surname("surname")
                .fullName("name last-name surname")
                .position(Position.MIDFIELDER.name())
                .team(Team.NO_TEAM.name())
                .status(Status.ACTIVE.name())
                .points(10)
                .matchPlayed(1)
                .goals(1)
                .goalsConceded(0)
                .goalsByPenalty(0)
                .yellowCards(1)
                .doubleYellowCards(0)
                .redCards(0)
                .build();
        Assertions.assertEquals(expectedPlayerDTO, playerMapper.fromStat(stats));
    }

    @Test
    void shouldDoMapperFromPlayer() {
        final Player player = Player.builder()
                .id(0L)
                .name("name")
                .lastname("last-name")
                .surname("surname")
                .position(Position.MIDFIELDER)
                .team(Team.NO_TEAM)
                .status(Status.ACTIVE)
                .build();
        final PlayerDTO expectedPlayerDTO = PlayerDTO.builder()
                .id("0")
                .name("name")
                .lastname("last-name")
                .surname("surname")
                .fullName("name last-name surname")
                .position(Position.MIDFIELDER.name())
                .team(Team.NO_TEAM.name())
                .status(Status.ACTIVE.name())
                .build();
        Assertions.assertEquals(expectedPlayerDTO, playerMapper.fromPlayer(player));
    }

    @Test
    void shouldMapperHasBirthdayField() {
        final Player player = Player.builder()
                .dateOfBirthday(LocalDate.of(1989, 12, 31))
                .build();
        final PlayerDTO playerDTO = playerMapper.fromPlayer(player);

        Assertions.assertEquals("1989-12-31", playerDTO.getDateOfBirthday());
    }

    @Test
    void shouldDoMapperToStats() {
        final PlayerDTO playerDTO = PlayerDTO.builder()
                .id("0")
                .season("season")
                .name("name")
                .lastname("last-name")
                .surname("surname")
                .fullName("name last-name surname")
                .position(Position.MIDFIELDER.name())
                .team(Team.NO_TEAM.name())
                .status(Status.ACTIVE.name())
                .points(10)
                .matchPlayed(1)
                .goals(1)
                .goalsConceded(0)
                .goalsByPenalty(0)
                .yellowCards(1)
                .doubleYellowCards(0)
                .redCards(0)
                .build();
        final Stats expectedStats = Stats.builder()
                .season("season")
                .idPlayer(0L)
                .points(10)
                .matchPlayed(1)
                .goals(1)
                .goalsByPenalty(0)
                .yellowCards(1)
                .doubleYellowCards(0)
                .redCards(0)
                .player(Player.builder()
                        .id(0L)
                        .name("name")
                        .lastname("last-name")
                        .surname("surname")
                        .position(Position.MIDFIELDER)
                        .team(Team.NO_TEAM)
                        .status(Status.ACTIVE)
                        .build())
                .build();

        Assertions.assertEquals(expectedStats, playerMapper.toStats(playerDTO));
    }

    @Test
    void shouldDoMapperToPlayer() {
        final PlayerDTO playerDTO = PlayerDTO.builder()
                .id("0")
                .season("season")
                .name("name")
                .lastname("last-name")
                .surname("surname")
                .fullName("name last-name surname")
                .position(Position.MIDFIELDER.name())
                .team(Team.NO_TEAM.name())
                .status(Status.ACTIVE.name())
                .points(10)
                .matchPlayed(1)
                .goals(1)
                .goalsConceded(0)
                .goalsByPenalty(0)
                .yellowCards(1)
                .doubleYellowCards(0)
                .redCards(0)
                .build();
        final Player expectedPlayer = Player.builder()
                .id(0L)
                .name("name")
                .lastname("last-name")
                .surname("surname")
                .position(Position.MIDFIELDER)
                .team(Team.NO_TEAM)
                .status(Status.ACTIVE)
                .build();

        Assertions.assertEquals(expectedPlayer, playerMapper.toPlayer(playerDTO));
    }

}