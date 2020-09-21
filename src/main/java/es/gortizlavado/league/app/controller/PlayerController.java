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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public PlayerDTO getPlayerById(@PathVariable Long id, @RequestParam(required = false) String seasonId) {
        log.debug("Call getPlayerById with id: {} for the season: {}", id, seasonId);
        return playerService.fetchPlayerById(id, seasonId);
    }

    @PostMapping
    public void addPlayer(PlayerDTO playerDTO) {
        log.debug("Call addPlayer with body: {}", playerDTO);
        playerService.addPlayer(playerDTO);
    }

    @PutMapping("/{id}")
    public PlayerDTO replacePlayer(@PathVariable Long id, PlayerDTO playerDTO) {
        log.debug("Call replacePlayer with id: {} for body: {}", id, playerDTO);
        return playerService.replacePlayer(id, playerDTO);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public PlayerDTO updatePlayer(@PathVariable Long id, JsonPatch patch) {
        log.debug("Call updatePlayer with id: {} for body: {}", id, patch);
        return playerService.updatePlayer(id, patch);
    }

    @GetMapping
    public List<PlayerDTO> getAllPlayers() {
        return playerService.getAllPlayers();
    }

}
