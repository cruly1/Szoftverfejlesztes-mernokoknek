package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.EGender;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.EIngameRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private String firstName;
    private String lastName;
    private String nickName;
    private EIngameRoles ingameRole;
    private LocalDate birthDate;
    private String teamName;
    private EGender gender;
    private String nationality;
}
