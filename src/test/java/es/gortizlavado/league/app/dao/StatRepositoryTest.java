package es.gortizlavado.league.app.dao;

import es.gortizlavado.league.app.entity.Player;
import es.gortizlavado.league.app.entity.Stats;
import es.gortizlavado.league.app.entity.StatsId;
import es.gortizlavado.league.app.models.enums.Position;
import es.gortizlavado.league.app.models.enums.Status;
import es.gortizlavado.league.app.models.enums.Team;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase
class StatRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private StatsRepository statsRepository;

    @Test
    void shouldFetchPlayerStatsWithAllData_whenPlayerAlreadyStored() {

        final Player player = Player.builder()
                .id(1L)
                .name("footballer-one")
                .dateOfBirthday(LocalDate.of(1992, 5, 29))
                .position(Position.DEFENSE)
                .team(Team.CD_BADAJOZ)
                .status(Status.HURT).build();
        playerRepository.save(player);

        final Stats stats = Stats.builder()
                .season("2020/2021")
                .player(player)
                .idPlayer(1L)
                .points(10)
                .goals(1)
                .redCards(1)
                .build();
        statsRepository.save(stats);

        final Optional<Player> playerOptional = playerRepository.findById(1L);
        Assertions.assertTrue(playerOptional.isPresent());

        final Optional<Stats> statsOptional = statsRepository.findById(StatsId.builder()
                .idPlayer(1L)
                .season("2020/2021")
                .build());
        Assertions.assertTrue(statsOptional.isPresent());
        Assertions.assertNotNull(statsOptional.get().getPlayer());
        Assertions.assertEquals("footballer-one", statsOptional.get().getPlayer().getName());
    }

    @Test
    void shouldNotFetchStats_whenJustPlayerInfoStored() {
        final Player player = Player.builder()
                .id(1L)
                .name("footballer-one")
                .dateOfBirthday(LocalDate.of(1992, 5, 29))
                .position(Position.DEFENSE)
                .team(Team.CD_BADAJOZ)
                .status(Status.HURT).build();
        playerRepository.save(player);

        final Optional<Stats> statsOptional = statsRepository.findById(StatsId.builder()
                .idPlayer(1L)
                .season("2020/2021")
                .build());
        Assertions.assertFalse(statsOptional.isPresent());
    }

}