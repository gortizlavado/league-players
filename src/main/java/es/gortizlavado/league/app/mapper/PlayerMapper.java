package es.gortizlavado.league.app.mapper;

import es.gortizlavado.league.app.entity.Player;
import es.gortizlavado.league.app.entity.Stats;
import es.gortizlavado.league.app.models.dto.PlayerDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AgeMapper.class)
@DecoratedWith(PlayerMapperDecorator.class)
public interface PlayerMapper {

    @Mapping(source = "player.name", target = "name")
    @Mapping(source = "player.dateOfBirthday", target = "age")
    @Mapping(source = "player.dateOfBirthday", target = "dateOfBirthday")
    @Mapping(source = "stats.season", target = "season")
    @Mapping(target = "fullName", ignore = true)
    PlayerDTO fromPlayer(Player player, Stats stats);

    @InheritInverseConfiguration
    Player toPlayer(PlayerDTO playerDTO);

    @InheritInverseConfiguration
    @Mapping(source = "id", target = "idPlayer")
    Stats toStats(PlayerDTO playerDTO);

}
