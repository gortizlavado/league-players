package es.gortizlavado.league.app.mapper;

import es.gortizlavado.league.app.entity.Player;
import es.gortizlavado.league.app.entity.Stats;
import es.gortizlavado.league.app.models.dto.PlayerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class PlayerMapperDecorator implements PlayerMapper {

    @Autowired
    @Qualifier("delegate")
    private PlayerMapper delegate;

    @Override
    public PlayerDTO fromPlayer(Player player) {
        final PlayerDTO dto = delegate.fromPlayer(player);
        return addFullName(dto);
    }

    @Override
    public PlayerDTO fromStat(Stats stats) {
        final PlayerDTO dto = delegate.fromStat(stats);
        return addFullName(dto);
    }

    private PlayerDTO addFullName(PlayerDTO dto) {
        dto.setFullName(dto.getName() + " " + dto.getLastname() + " " + dto.getSurname());
        return dto;
    }
}
