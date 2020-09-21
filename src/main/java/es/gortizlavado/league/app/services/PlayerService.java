package es.gortizlavado.league.app.services;

import com.github.fge.jsonpatch.JsonPatch;
import es.gortizlavado.league.app.models.dto.PlayerDTO;

import java.util.List;

public interface PlayerService {

    PlayerDTO fetchPlayerById(Long id, String seasonId);

    List<PlayerDTO> getAllPlayers();

    void addPlayer(PlayerDTO playerDTO);

    PlayerDTO updatePlayer(final Long id, JsonPatch jsonPatch);

    PlayerDTO replacePlayer(final Long id, PlayerDTO playerDTO);
}
