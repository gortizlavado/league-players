package es.gortizlavado.league.app.mapper;

import es.gortizlavado.league.app.entity.Player;
import es.gortizlavado.league.app.entity.Stat;
import es.gortizlavado.league.app.models.dto.PlayerDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AgeMapper.class)
@DecoratedWith(PlayerMapperDecorator.class)
public interface PlayerMapper {

    @Mapping(source = "dateOfBirthday", target = "age")
    @Mapping(source = "dateOfBirthday", target = "dateOfBirthday")
    @Mapping(target = "points", ignore = true)
    @Mapping(target = "matchPlayed", ignore = true)
    @Mapping(target = "goals", ignore = true)
    @Mapping(target = "goalsConceded", ignore = true)
    @Mapping(target = "goalsByPenalty", ignore = true)
    @Mapping(target = "yellowCards", ignore = true)
    @Mapping(target = "doubleYellowCards", ignore = true)
    @Mapping(target = "redCards", ignore = true)
    @Mapping(target = "season", ignore = true)
    @Mapping(target = "fullName", ignore = true)
    PlayerDTO fromPlayer(Player player);

    @Mapping(source = "player.id", target = "id")
    @Mapping(source = "player.name", target = "name")
    @Mapping(source = "player.lastname", target = "lastname")
    @Mapping(source = "player.surname", target = "surname")
    @Mapping(source = "player.position", target = "position")
    @Mapping(source = "player.team", target = "team")
    @Mapping(source = "player.status", target = "status")
    @Mapping(source = "player.dateOfBirthday", target = "age")
    @Mapping(source = "player.dateOfBirthday", target = "dateOfBirthday")
    @Mapping(target = "fullName", ignore = true)
    PlayerDTO fromStat(Stat stat);

    @InheritInverseConfiguration
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updated_at", ignore = true)
    @Mapping(target = "deleted_at", ignore = true)
    @Mapping(target = "dateOfBirthday", source = "dateOfBirthday")
    Player toPlayer(PlayerDTO playerDTO);

    @InheritInverseConfiguration
    @Mapping(source = "id", target = "idPlayer")
    @Mapping(target = "player.dateOfBirthday", source = "dateOfBirthday")
    Stat toStats(PlayerDTO playerDTO);

}
