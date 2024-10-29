package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.EGender;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.EIngameRoles;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.ENationality;
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
    private LocalDate dateOfBirth;
    private EGender gender;
    private ENationality nationality;
    private String teamName;
}
