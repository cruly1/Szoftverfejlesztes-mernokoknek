package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.mapper;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.TeamDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PlayerMapper.class)
public interface TeamMapper {

    @Mapping(source = "playersInTeam", target = "players")
    TeamDTO toDTO(Team team);

    @Mapping(source = "players", target = "playersInTeam")
    Team toEntity(TeamDTO teamDTO);
}
