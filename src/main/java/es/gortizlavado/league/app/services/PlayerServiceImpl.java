package es.gortizlavado.league.app.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import es.gortizlavado.league.app.dao.PlayerRepository;
import es.gortizlavado.league.app.dao.StatsRepository;
import es.gortizlavado.league.app.entity.Player;
import es.gortizlavado.league.app.entity.Stats;
import es.gortizlavado.league.app.entity.StatsId;
import es.gortizlavado.league.app.exceptions.JsonPatchCustomException;
import es.gortizlavado.league.app.exceptions.JsonProcessingCustomException;
import es.gortizlavado.league.app.exceptions.PlayerNotFoundException;
import es.gortizlavado.league.app.exceptions.PlayerStatNotFoundException;
import es.gortizlavado.league.app.mapper.PlayerMapper;
import es.gortizlavado.league.app.models.dto.PlayerDTO;
import es.gortizlavado.league.app.models.enums.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final StatsRepository statsRepository;

    private final ObjectMapper objectMapper;
    private final PlayerMapper playerMapper;

    @Value("${league-players.season.currently-id}")
    private String currentlySeasonId;

    @Override
    @Transactional(readOnly = true)
    public PlayerDTO fetchPlayerById(UUID id) {
        final Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));
        return playerMapper.fromPlayer(player);
    }

    @Override
    @Transactional(readOnly = true)
    public PlayerDTO fetchPlayerStatById(UUID id, String seasonId) {
        final Stats stats = statsRepository.findById(StatsId.builder()
                .idPlayer(id)
                .season(StringUtils.hasLength(seasonId) ? seasonId: currentlySeasonId)
                .build()).orElseThrow(() -> new PlayerStatNotFoundException(id, seasonId));
        return playerMapper.fromStat(stats);
    }

    @Override
    public List<PlayerDTO> fetchPlayersByTeam(String id) {
        final List<Player> listPlayer = playerRepository.findByTeam(Team.valueOf(id));
        return fromPlayers(listPlayer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlayerDTO> getAllPlayers() {
        List<Player> listPlayer = (List<Player>) playerRepository.findAll();
        return fromPlayers(listPlayer);
    }

    @Override
    @Transactional
    public PlayerDTO savePlayer(final Long id, String seasonId, PlayerDTO playerDTO) {
        playerDTO.setId(String.valueOf(id));
        playerDTO.setSeason(seasonId);
        return savePlayer(playerDTO);
    }

    @Override
    @Transactional
    public PlayerDTO updatePlayer(final UUID id, String seasonId, JsonPatch jsonPatch) {
        final PlayerDTO playerDTO = this.fetchPlayerStatById(id, seasonId);
        final PlayerDTO playerPatched = applyPatchToPlayerDTO(id, jsonPatch, playerDTO);
        return savePlayer(playerPatched);
    }

    private PlayerDTO savePlayer(PlayerDTO playerDTO) {
        Stats stats = playerMapper.toStats(playerDTO);
        final Stats statsSaved = statsRepository.save(stats);
        return playerMapper.fromStat(statsSaved);
    }

    private List<PlayerDTO> fromPlayers(List<Player> listPlayer) {
        return listPlayer.stream()
                .map(playerMapper::fromPlayer)
                .collect(Collectors.toList());
    }

    private PlayerDTO applyPatchToPlayerDTO(UUID id, JsonPatch jsonPatch, PlayerDTO playerDTO) {
        final JsonNode patched;
        try {
            final JsonNode jsonNode = objectMapper.convertValue(playerDTO, JsonNode.class);
            log.debug("Convert successful PlayerDTO to JsonNode: {}", jsonNode);
            patched = jsonPatch.apply(jsonNode);
        } catch (JsonPatchException e) {
            throw new JsonPatchCustomException(id, jsonPatch.toString());
        }

        final PlayerDTO playerPatched;
        try {
            log.debug("Trying to convert to PlayerDTO: {}", patched);
            playerPatched = objectMapper.treeToValue(patched, PlayerDTO.class);
        } catch (JsonProcessingException e) {
            throw new JsonProcessingCustomException(id, patched.asText());
        }
        return playerPatched;
    }

}
