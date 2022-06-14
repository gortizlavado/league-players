package es.gortizlavado.league.app.controller;

import com.github.fge.jsonpatch.JsonPatch;
import es.gortizlavado.league.app.models.dto.PlayerDTO;
import es.gortizlavado.league.app.services.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/alive")
    @ResponseStatus(HttpStatus.OK)
    public void isAlive() {
    }

    @GetMapping("/{id}")
    public PlayerDTO getPlayerById(@PathVariable UUID id) {
        log.debug("Call getPlayerById with id: {}", id);
        return playerService.fetchPlayerById(id);
    }

    @GetMapping("/{id}/{seasonId}")
    public PlayerDTO getPlayerStatById(@PathVariable UUID id, @PathVariable String seasonId) {
        log.debug("Call getPlayerById with id: {} for the season: {}", id, seasonId);
        return playerService.fetchPlayerStatById(id, seasonId);
    }

    @GetMapping("/team/{id}")
    public List<PlayerDTO> getPlayersByTeam(@PathVariable String id) {
        log.debug("Call getPlayerById with team id: {}", id);
        return playerService.fetchPlayersByTeam(id);
    }

    @PutMapping("/{id}/{seasonId}")
    public PlayerDTO savePlayer(@PathVariable Long id, @PathVariable String seasonId, PlayerDTO playerDTO) {
        log.debug("Call savePlayer with id: {}-{} for body: {}", id, seasonId, playerDTO);
        return playerService.savePlayer(id, seasonId, playerDTO);
    }

    @PatchMapping(path = "/{id}/{seasonId}", consumes = "application/json-patch+json")
    public PlayerDTO updatePlayer(@PathVariable UUID id, @PathVariable String seasonId, JsonPatch patch) {
        log.debug("Call updatePlayer with id: {} for body: {}", id, patch);
        return playerService.updatePlayer(id, seasonId, patch);
    }

    @GetMapping
    public List<PlayerDTO> getAllPlayers() {
        return playerService.getAllPlayers();
    }

}
