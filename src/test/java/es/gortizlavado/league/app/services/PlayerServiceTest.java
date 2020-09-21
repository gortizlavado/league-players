package es.gortizlavado.league.app.services;

import es.gortizlavado.league.app.dao.PlayerRepository;
import es.gortizlavado.league.app.dao.StatsRepository;
import es.gortizlavado.league.app.entity.Player;
import es.gortizlavado.league.app.entity.Stats;
import es.gortizlavado.league.app.entity.StatsId;
import es.gortizlavado.league.app.exceptions.PlayerNotFoundException;
import es.gortizlavado.league.app.mapper.PlayerMapper;
import es.gortizlavado.league.app.models.dto.PlayerDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @InjectMocks
    private PlayerServiceImpl service;

    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private StatsRepository statsRepository;

    @Mock
    private PlayerMapper playerMapper;

    @Test
    void shouldReturnPlayer_whenStoredInDataBase() {
        Mockito.when(playerRepository.findById(ArgumentMatchers.eq(0L)))
                .thenReturn(Optional.of(Player.builder().build()));
        Mockito.when(statsRepository.findById(ArgumentMatchers.eq(StatsId.builder().idPlayer(0L).season("currently-season-id").build())))
                .thenReturn(Optional.of(Stats.builder().build()));
        Mockito.when(playerMapper.fromPlayer(any(Player.class), any(Stats.class)))
                .thenReturn(PlayerDTO.builder().build());
        final PlayerDTO playerDTO = service.fetchPlayerById(0L, "currently-season-id");
        Assertions.assertNotNull(playerDTO);
    }

    @Test
    void shouldReturnPlayerWithoutSpecifySeason_whenStoredInDataBase() {
        ReflectionTestUtils.setField(service,"currentlySeasonId", "2021/2022");
        Mockito.when(playerRepository.findById(ArgumentMatchers.eq(0L)))
                .thenReturn(Optional.of(Player.builder().build()));
        Mockito.when(statsRepository.findById(ArgumentMatchers.eq(StatsId.builder().idPlayer(0L).season("2021/2022").build())))
                .thenReturn(Optional.of(Stats.builder().build()));
        Mockito.when(playerMapper.fromPlayer(any(Player.class), any(Stats.class)))
                .thenReturn(PlayerDTO.builder().build());
        final PlayerDTO playerDTO = service.fetchPlayerById(0L, "");
        Assertions.assertNotNull(playerDTO);
    }

    @Test
    void shouldTrowException_whenNotStoredInDataBase() {
        Mockito.when(playerRepository.findById(ArgumentMatchers.eq(0L))).thenReturn(Optional.empty());
        Assertions.assertThrows(PlayerNotFoundException.class, () -> service.fetchPlayerById(0L, "currently-season-id"));
    }

    @Test
    void shouldReturnPlayerWithEmptyStats_whenStoredInDataBase() {
        Mockito.when(playerRepository.findById(ArgumentMatchers.eq(0L))).thenReturn(Optional.of(Player.builder().build()));
        Mockito.when(statsRepository.findById(any())).thenReturn(Optional.empty());
        Mockito.when(playerMapper.fromPlayer(any(Player.class), any(Stats.class))).thenReturn(PlayerDTO.builder().build());
        final PlayerDTO playerDTO = service.fetchPlayerById(0L, "currently-season-id");
        Assertions.assertNotNull(playerDTO);
        Assertions.assertNull(playerDTO.getPoints());
        Assertions.assertNull(playerDTO.getMatchPlayed());
        Assertions.assertNull(playerDTO.getGoals());
        Assertions.assertNull(playerDTO.getGoalsByPenalty());
        Assertions.assertNull(playerDTO.getYellowCards());
        Assertions.assertNull(playerDTO.getDoubleYellowCards());
        Assertions.assertNull(playerDTO.getRedCards());
    }
}