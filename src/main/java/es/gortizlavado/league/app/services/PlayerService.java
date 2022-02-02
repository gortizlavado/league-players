package es.gortizlavado.league.app.services;

import com.github.fge.jsonpatch.JsonPatch;
import es.gortizlavado.league.app.models.dto.PlayerDTO;

import java.util.List;

public interface PlayerService {

    PlayerDTO fetchPlayerById(Long id);

    PlayerDTO fetchPlayerStatById(Long id, String seasonId);

    List<PlayerDTO> getAllPlayers();

    PlayerDTO savePlayer(final Long id, String seasonId, PlayerDTO playerDTO);

    PlayerDTO updatePlayer(final Long id, String seasonId, JsonPatch jsonPatch);

}
