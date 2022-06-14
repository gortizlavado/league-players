package es.gortizlavado.league.app.services;

import com.github.fge.jsonpatch.JsonPatch;
import es.gortizlavado.league.app.models.dto.PlayerDTO;

import java.util.List;
import java.util.UUID;

public interface PlayerService {

    PlayerDTO fetchPlayerById(UUID id);

    PlayerDTO fetchPlayerStatById(UUID id, String seasonId);

    List<PlayerDTO> fetchPlayersByTeam(String id);

    List<PlayerDTO> getAllPlayers();

    PlayerDTO savePlayer(final Long id, String seasonId, PlayerDTO playerDTO);

    PlayerDTO updatePlayer(final UUID id, String seasonId, JsonPatch jsonPatch);

}
