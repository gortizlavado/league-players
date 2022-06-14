package es.gortizlavado.league.app.dao;

import es.gortizlavado.league.app.entity.Player;
import es.gortizlavado.league.app.entity.Stats;
import es.gortizlavado.league.app.entity.StatsId;
import es.gortizlavado.league.app.models.enums.Position;
import es.gortizlavado.league.app.models.enums.Status;
import es.gortizlavado.league.app.models.enums.Team;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import io.zonky.test.db.flyway.OptimizedFlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        OptimizedFlywayTestExecutionListener.class})
@AutoConfigureEmbeddedDatabase(
        provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY,
        refresh = AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD)
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private StatsRepository statsRepository;

    @Test
    @FlywayTest
    void shouldBeOneRecord_whenPutPlayer() {

        var newPlayer = Player.builder()
                .name("footballer-new")
                .dateOfBirthday(LocalDate.of(1991, 2, 1))
                .position(Position.DEFENSE)
                .team(Team.NO_TEAM)
                .status(Status.ACTIVE).build();

        Player playerSaved = playerRepository.save(newPlayer);

        Assertions.assertEquals(1, playerRepository.count());
        Assertions.assertNotNull(playerSaved);
        Assertions.assertNotNull(playerSaved.getId());

        final Optional<Player> playerRepositoryById = playerRepository.findById(playerSaved.getId());
        Assertions.assertNotNull(playerRepositoryById.orElseThrow().getCreatedAt());
    }

    @Test
    @FlywayTest
    void shouldBeOneRecord_whenPutPlayerWithId() {
        // If you provide your own id value then Spring Data will assume that you need to check the DB
        // for a duplicate key (hence the select+insert).
        final UUID uuid = UUID.randomUUID();
        System.out.println("UUID: " + uuid);
        var newPlayer = Player.builder()
                .id(uuid)
                .name("footballer-new")
                .dateOfBirthday(LocalDate.of(1991, 2, 1))
                .position(Position.DEFENSE)
                .team(Team.NO_TEAM)
                .status(Status.ACTIVE).build();

        Player playerSaved = playerRepository.save(newPlayer);

        Assertions.assertEquals(1, playerRepository.count());
        Assertions.assertNotNull(playerSaved);
        Assertions.assertEquals(uuid, playerSaved.getId());

        final Optional<Player> playerRepositoryById = playerRepository.findById(playerSaved.getId());
        Assertions.assertNotNull(playerRepositoryById.orElseThrow().getCreatedAt());
    }

    @Test
    @FlywayTest(locationsForMigrate = {"loadmsql"}, invokeBaselineDB = true)
    void shouldFetchPlayerWithStats_whenStoredInDDBB() {
        final UUID uuid = UUID.fromString("00000002-f1b1-11ec-8ea0-0242ac120002");
        final Optional<Player> playerOptional = playerRepository.findById(uuid);
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

    @Test
    @FlywayTest(locationsForMigrate = {"loadmsql"}, invokeBaselineDB = true)
    void shouldCreateNewEntry_whenPlayerIsCreated() {
        final long initialCount = statsRepository.count();

        UUID newId = UUID.randomUUID();
        Stats stats = Stats.builder()
                .idPlayer(newId)
                .season("2021/2022")
                .matchPlayed(1)
                .points(5)
                .player(Player.builder()
                        .id(newId)
                        .name("footballer-new")
                        .dateOfBirthday(LocalDate.of(1991, 2, 1))
                        .position(Position.DEFENSE)
                        .team(Team.NO_TEAM)
                        .status(Status.ACTIVE).build()).build();

        statsRepository.save(stats);

        Assertions.assertEquals(initialCount + 1L, statsRepository.count());
    }

    @Test
    @FlywayTest(locationsForMigrate = {"loadmsql"}, invokeBaselineDB = true)
    void shouldDelete_whenPlayerIsDeleted() {
        final long initialCount = statsRepository.count();

        Player player = Player.builder()
                .name("footballer-new")
                .dateOfBirthday(LocalDate.of(1991, 2, 1))
                .position(Position.DEFENSE)
                .team(Team.NO_TEAM)
                .status(Status.ACTIVE).build();

        playerRepository.save(player);

        playerRepository.delete(player);
        Assertions.assertEquals(initialCount, statsRepository.count());
    }

}
