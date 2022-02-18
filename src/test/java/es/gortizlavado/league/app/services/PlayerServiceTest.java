package es.gortizlavado.league.app.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import es.gortizlavado.league.app.dao.PlayerRepository;
import es.gortizlavado.league.app.dao.StatsRepository;
import es.gortizlavado.league.app.entity.Player;
import es.gortizlavado.league.app.entity.Stats;
import es.gortizlavado.league.app.entity.StatsId;
import es.gortizlavado.league.app.exceptions.PlayerStatNotFoundException;
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

import java.io.IOException;
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

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnPlayerInAnySeason_whenStoredInDataBase() {
        Mockito.when(statsRepository.findById(ArgumentMatchers.eq(StatsId.builder().idPlayer(0L).season("currently-season-id").build())))
                .thenReturn(Optional.of(Stats.builder().build()));
        Mockito.when(playerMapper.fromStat(any(Stats.class)))
                .thenReturn(PlayerDTO.builder().build());
        final PlayerDTO playerDTO = service.fetchPlayerStatById(0L, "currently-season-id");
        Assertions.assertNotNull(playerDTO);
    }

    @Test
    void shouldReturnPlayerInCurrentSeason_whenSeasonIsNotSpecified() {
        ReflectionTestUtils.setField(service, "currentlySeasonId", "2021/2022");
        Mockito.when(statsRepository.findById(ArgumentMatchers.eq(StatsId.builder().idPlayer(0L).season("2021/2022").build())))
                .thenReturn(Optional.of(Stats.builder().build()));
        Mockito.when(playerMapper.fromStat(any(Stats.class)))
                .thenReturn(PlayerDTO.builder().build());
        final PlayerDTO playerDTO = service.fetchPlayerStatById(0L, "");
        Assertions.assertNotNull(playerDTO);
    }

    @Test
    void shouldTrowException_whenNotStoredInDataBase() {
        Mockito.when(statsRepository.findById(any(StatsId.class))).thenReturn(Optional.empty());
        Assertions.assertThrows(PlayerStatNotFoundException.class, () -> service.fetchPlayerStatById(0L, "currently-season-id"));
    }

    @Test
    void shouldUpdatePlayer() throws IOException {
        ObjectMapper oMapper = new ObjectMapper();
        Mockito.when(statsRepository.findById(ArgumentMatchers.eq(StatsId.builder().idPlayer(0L).season("2021/2022").build())))
                .thenReturn(Optional.of(Stats.builder().player(Player.builder().name("name").lastname("lastname").build()).build()));
        Mockito.when(playerMapper.fromStat(any(Stats.class)))
                .thenReturn(PlayerDTO.builder().id("0").season("2021/2022").name("name").lastname("lastname").build());
        Mockito.when(objectMapper.convertValue(any(), (Class<Object>) any()))
                .thenReturn(oMapper.valueToTree(PlayerDTO.builder().id("0").season("2021/2022").name("name").lastname("lastname").build()));
        Mockito.when(objectMapper.treeToValue(any(), (Class<Object>) any()))
                .thenReturn(PlayerDTO.builder().id("0").season("2021/2022").name("new-name").build());
        Mockito.when(playerMapper.toStats(any(PlayerDTO.class)))
                .thenReturn(Stats.builder().idPlayer(1L).season("2021/2022").player(Player.builder().id(1L).name("new-name").build()).build());

        JsonPatch patch = JsonPatch.fromJson(oMapper.readTree("[" +
                "  { \"op\": \"replace\", \"path\": \"/name\", \"value\": \"new-name\" },\n" +
                "  { \"op\": \"remove\", \"path\": \"/lastname\" }\n" +
                "]"));

        service.updatePlayer(0L, "2021/2022", patch);

        Mockito.verify(statsRepository).save(Stats.builder().idPlayer(1L).season("2021/2022").player(Player.builder().id(1L).name("new-name").build()).build());
    }

}