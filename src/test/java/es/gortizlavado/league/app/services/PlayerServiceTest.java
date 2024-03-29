package es.gortizlavado.league.app.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import es.gortizlavado.league.app.dao.PlayerRepository;
import es.gortizlavado.league.app.dao.StatsRepository;
import es.gortizlavado.league.app.entity.Player;
import es.gortizlavado.league.app.entity.Stat;
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
import java.util.UUID;

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
        final UUID idPlayerNew = UUID.randomUUID();
        Mockito.when(statsRepository.findById(ArgumentMatchers.eq(StatsId.builder().idPlayer(idPlayerNew).season("currently-season-id").build())))
                .thenReturn(Optional.of(Stat.builder().build()));
        Mockito.when(playerMapper.fromStat(any(Stat.class)))
                .thenReturn(PlayerDTO.builder().build());
        final PlayerDTO playerDTO = service.fetchPlayerStatById(idPlayerNew, "currently-season-id");
        Assertions.assertNotNull(playerDTO);
    }

    @Test
    void shouldReturnPlayerInCurrentSeason_whenSeasonIsNotSpecified() {
        final UUID idPlayerNew = UUID.randomUUID();
        ReflectionTestUtils.setField(service, "currentlySeasonId", "2021/2022");
        Mockito.when(statsRepository.findById(ArgumentMatchers.eq(StatsId.builder().idPlayer(idPlayerNew).season("2021/2022").build())))
                .thenReturn(Optional.of(Stat.builder().build()));
        Mockito.when(playerMapper.fromStat(any(Stat.class)))
                .thenReturn(PlayerDTO.builder().build());
        final PlayerDTO playerDTO = service.fetchPlayerStatById(idPlayerNew, "");
        Assertions.assertNotNull(playerDTO);
    }

    @Test
    void shouldTrowException_whenNotStoredInDataBase() {
        Mockito.when(statsRepository.findById(any(StatsId.class))).thenReturn(Optional.empty());
        Assertions.assertThrows(PlayerStatNotFoundException.class, () -> service.fetchPlayerStatById(UUID.randomUUID(), "currently-season-id"));
    }

    @Test
    void shouldUpdatePlayer() throws IOException {
        final UUID idPlayerNew = UUID.randomUUID();
        ObjectMapper oMapper = new ObjectMapper();
        Mockito.when(statsRepository.findById(ArgumentMatchers.eq(StatsId.builder().idPlayer(idPlayerNew).season("2021/2022").build())))
                .thenReturn(Optional.of(Stat.builder().player(Player.builder().name("name").lastname("lastname").build()).build()));
        Mockito.when(playerMapper.fromStat(any(Stat.class)))
                .thenReturn(PlayerDTO.builder().id("0").season("2021/2022").name("name").lastname("lastname").build());
        Mockito.when(objectMapper.convertValue(any(), (Class<Object>) any()))
                .thenReturn(oMapper.valueToTree(PlayerDTO.builder().id("0").season("2021/2022").name("name").lastname("lastname").build()));
        Mockito.when(objectMapper.treeToValue(any(), (Class<Object>) any()))
                .thenReturn(PlayerDTO.builder().id("0").season("2021/2022").name("new-name").build());
        Mockito.when(playerMapper.toStats(any(PlayerDTO.class)))
                .thenReturn(Stat.builder().idPlayer(idPlayerNew).season("2021/2022").player(Player.builder().id(idPlayerNew).name("new-name").build()).build());

        JsonPatch patch = JsonPatch.fromJson(oMapper.readTree("[" +
                "  { \"op\": \"replace\", \"path\": \"/name\", \"value\": \"new-name\" },\n" +
                "  { \"op\": \"remove\", \"path\": \"/lastname\" }\n" +
                "]"));

        service.updatePlayer(idPlayerNew, "2021/2022", patch);

        Mockito.verify(statsRepository).save(Stat.builder().idPlayer(idPlayerNew).season("2021/2022").player(Player.builder().id(idPlayerNew).name("new-name").build()).build());
    }

}