package es.gortizlavado.league.app.mapper;

import es.gortizlavado.league.app.entity.Player;
import es.gortizlavado.league.app.entity.Stat;
import es.gortizlavado.league.app.models.dto.PlayerDTO;
import es.gortizlavado.league.app.models.enums.Position;
import es.gortizlavado.league.app.models.enums.Status;
import es.gortizlavado.league.app.models.enums.Team;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
@AutoConfigureEmbeddedDatabase
class PlayerMapperTest {

    @Autowired
    private PlayerMapper playerMapper;

    @Test
    void shouldDoMapperFromStat() {
        final UUID idPlayerNew = UUID.randomUUID();
        final Stat stat = Stat.builder()
                .season("season")
                .idPlayer(idPlayerNew)
                .player(Player.builder()
                        .id(idPlayerNew)
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
                .id(idPlayerNew.toString())
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
        Assertions.assertEquals(expectedPlayerDTO, playerMapper.fromStat(stat));
    }

    @Test
    void shouldDoMapperFromPlayer() {
        final UUID idPlayerNew = UUID.randomUUID();
        final Player player = Player.builder()
                .id(idPlayerNew)
                .name("name")
                .lastname("last-name")
                .surname("surname")
                .position(Position.MIDFIELDER)
                .team(Team.NO_TEAM)
                .status(Status.ACTIVE)
                .build();
        final PlayerDTO expectedPlayerDTO = PlayerDTO.builder()
                .id(idPlayerNew.toString())
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
        final UUID idPlayerNew = UUID.randomUUID();
        final PlayerDTO playerDTO = PlayerDTO.builder()
                .id(idPlayerNew.toString())
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
        final Stat expectedStat = Stat.builder()
                .season("season")
                .idPlayer(idPlayerNew)
                .points(10)
                .matchPlayed(1)
                .goals(1)
                .goalsByPenalty(0)
                .yellowCards(1)
                .doubleYellowCards(0)
                .redCards(0)
                .player(Player.builder()
                        .id(idPlayerNew)
                        .name("name")
                        .lastname("last-name")
                        .surname("surname")
                        .position(Position.MIDFIELDER)
                        .team(Team.NO_TEAM)
                        .status(Status.ACTIVE)
                        .build())
                .build();

        Assertions.assertEquals(expectedStat, playerMapper.toStats(playerDTO));
    }

    @Test
    void shouldDoMapperToPlayer() {
        final UUID idPlayerNew = UUID.randomUUID();
        final PlayerDTO playerDTO = PlayerDTO.builder()
                .id(idPlayerNew.toString())
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
                .id(idPlayerNew)
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