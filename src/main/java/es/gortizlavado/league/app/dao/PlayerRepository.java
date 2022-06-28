package es.gortizlavado.league.app.dao;

import es.gortizlavado.league.app.entity.Player;
import es.gortizlavado.league.app.models.enums.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerRepository extends CrudRepository<Player, UUID> {

    List<Player> findByTeam(Team team);

    //TODO think about fetch Player about name with the condition of the field name should be unique
    // because I intend to have a endpoint searching by name
    Optional<Player> findByName(String name);
}
