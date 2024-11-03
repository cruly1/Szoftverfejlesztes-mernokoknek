package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.mapper;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.EventDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(source = "eventStartDate", target = "eventStartDate")
    @Mapping(source = "eventEndDate", target = "eventEndDate")
    EventDTO toDTO(Event event);

    @Mapping(source = "eventStartDate", target = "eventStartDate")
    @Mapping(source = "eventEndDate", target = "eventEndDate")
    Event toEntity(EventDTO eventDTO);

    EventDTO toSimpleDTO(Event event);
}
