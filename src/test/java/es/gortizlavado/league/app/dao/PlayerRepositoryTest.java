package es.gortizlavado.league.app.dao;

import es.gortizlavado.league.app.entity.Player;
import es.gortizlavado.league.app.entity.Stats;
import es.gortizlavado.league.app.entity.StatsId;
import es.gortizlavado.league.app.models.enums.Position;
import es.gortizlavado.league.app.models.enums.Status;
import es.gortizlavado.league.app.models.enums.Team;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private StatsRepository statsRepository;

    @BeforeEach
    void setUp() {
        prepareDataInDDBB();
    }

    @Test
    void shouldFetchPlayerNumberOne() {
        final Optional<Player> playerOptional = playerRepository.findById(1L);
        Assertions.assertTrue(playerOptional.isPresent());
        Assertions.assertEquals("footballer-one", playerOptional.get().getName());
    }

    @Test
    void shouldFetchPlayerWithStats_whenStoredInDDBB() {
        final Optional<Player> playerOptional = playerRepository.findById(2L);
        Assertions.assertTrue(playerOptional.isPresent());
        final Optional<Stats> stats2019Optional = statsRepository.findById(StatsId.builder()
                .idPlayer(playerOptional.get().getId())
                .season("2019/2020")
                .build());
        Assertions.assertTrue(stats2019Optional.isPresent());
        Assertions.assertEquals(-6, stats2019Optional.get().getPoints());

        final Optional<Stats> stats2020Optional = statsRepository.findById(StatsId.builder()
                .idPlayer(playerOptional.get().getId())
                .season("2020/2021")
                .build());
        Assertions.assertTrue(stats2020Optional.isPresent());
        Assertions.assertEquals(10, stats2020Optional.get().getPoints());
    }

    private void prepareDataInDDBB() {
        final List<Player> players = List.of(Player.builder()
                        .id(1L)
                        .name("footballer-one")
                        .dateOfBirthday(LocalDate.of(1992, 5, 29))
                        .position(Position.DEFENSE)
                        .team(Team.CD_BADAJOZ)
                        .status(Status.HURT).build(),
                Player.builder()
                        .id(2L)
                        .name("footballer-two")
                        .lastname("lastname-two")
                        .surname("surname-two")
                        .dateOfBirthday(LocalDate.of(1989, 11, 9))
                        .position(Position.GOALKEEPER)
                        .team(Team.CD_DON_BENITO)
                        .status(Status.ACTIVE).build());

        final List<Stats> stats = List.of(Stats.builder()
                        .season("2020/2021")
                        .idPlayer(2L)
                        .points(10)
                        .goals(1)
                        .redCards(1)
                        .build(),
                Stats.builder()
                        .season("2019/2020")
                        .idPlayer(2L)
                        .points(-6)
                        .goals(0)
                        .yellowCards(2)
                        .redCards(1)
                        .build());

        playerRepository.saveAll(players);
        statsRepository.saveAll(stats);
    }
}