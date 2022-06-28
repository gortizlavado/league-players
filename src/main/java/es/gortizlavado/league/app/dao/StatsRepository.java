package es.gortizlavado.league.app.dao;

import es.gortizlavado.league.app.entity.Stat;
import es.gortizlavado.league.app.entity.StatsId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends CrudRepository<Stat, StatsId> {

}
