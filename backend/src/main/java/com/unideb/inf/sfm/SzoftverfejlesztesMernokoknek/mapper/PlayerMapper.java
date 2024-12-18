package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.mapper;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.PlayerDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(source = "team.teamName", target = "teamName")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "nationality.countryName", target = "countryName")
    @Mapping(source = "nationality.countryCode", target = "countryCode")
    @Mapping(source = "user.username", target = "username")
    PlayerDTO toDTO(Player player);

    @Mapping(source = "teamName", target = "team.teamName")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "countryName", target = "nationality.countryName")
    @Mapping(source = "countryCode", target = "nationality.countryCode")
    @Mapping(source = "username", target = "user.username")
    Player toEntity(PlayerDTO playerDTO);
}
