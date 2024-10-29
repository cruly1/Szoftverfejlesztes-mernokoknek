package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.mapper;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.PlayerDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(source = "team.teamName", target = "teamName")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    PlayerDTO toDTO(Player player);

    @Mapping(source = "teamName", target = "team.teamName")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    Player toEntity(PlayerDTO playerDTO);
}
