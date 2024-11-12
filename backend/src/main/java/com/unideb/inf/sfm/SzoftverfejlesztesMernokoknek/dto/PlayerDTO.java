package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.EGender;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.EIngameRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private String firstName;
    private String lastName;
    private String nickName;
    private EIngameRoles ingameRole;
    private LocalDate dateOfBirth;
    private EGender gender;
    private String countryName;
    private String demonym;
    private String cca2;
    private String teamName;
    private Set<EventDTO> events = new HashSet<>();
    private String username;
    private String profileImageName;
    private String profileImageType;
}
