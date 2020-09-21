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
import es.gortizlavado.league.app.mapper.PlayerMapper;
import es.gortizlavado.league.app.models.dto.PlayerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
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
    public PlayerDTO fetchPlayerById(Long id, String seasonId) {
        final Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));
        final Stats stats = statsRepository.findById(StatsId.builder()
                .idPlayer(id)
                .season(StringUtils.isEmpty(seasonId) ? currentlySeasonId : seasonId)
                .build()).orElse(new Stats());
        return playerMapper.fromPlayer(player, stats);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlayerDTO> getAllPlayers() {
        List<Player> listPlayer = (List<Player>) playerRepository.findAll();

        return listPlayer.stream()
                .map(player -> playerMapper.fromPlayer(player, Stats.builder().build()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addPlayer(PlayerDTO playerDTO) {
        this.savePlayer(playerDTO);
    }

    @Override
    @Transactional
    public PlayerDTO replacePlayer(final Long id, PlayerDTO playerDTO) {
        playerDTO.setId(String.valueOf(id));
        return savePlayer(playerDTO);
    }

    @Override
    @Transactional
    public PlayerDTO updatePlayer(final Long id, JsonPatch jsonPatch) {
        final PlayerDTO playerDTO = this.fetchPlayerById(id);
        final PlayerDTO playerPatched = applyPatchToPlayerDTO(id, jsonPatch, playerDTO);
        return savePlayer(playerPatched);
    }

    private PlayerDTO fetchPlayerById(Long id) {
        return fetchPlayerById(id, null);
    }

    private PlayerDTO savePlayer(PlayerDTO playerDTO) {
        Player player = playerMapper.toPlayer(playerDTO);
        Stats stats = playerMapper.toStats(playerDTO);

        final Player playerSaved = playerRepository.save(player);
        final Stats statsSaved = statsRepository.save(stats);
        return playerMapper.fromPlayer(playerSaved, statsSaved);
    }

    private PlayerDTO applyPatchToPlayerDTO(Long id, JsonPatch jsonPatch, PlayerDTO playerDTO) {
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
