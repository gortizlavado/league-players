package es.gortizlavado.league.app.dao;

import es.gortizlavado.league.app.entity.Player;
import es.gortizlavado.league.app.models.enums.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerRepository extends CrudRepository<Player, UUID> {

    List<Player> findByTeam(Team team);

}
