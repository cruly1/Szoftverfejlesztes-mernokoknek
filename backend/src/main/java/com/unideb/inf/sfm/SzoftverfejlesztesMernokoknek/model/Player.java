package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.EGender;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.EIngameRoles;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.ENationality;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "player_gen")
    @TableGenerator(
            name = "player_gen",
            table = "id_gen_table",
            pkColumnName = "gen_name",
            valueColumnName = "gen_value",
            pkColumnValue = "player_id",
            initialValue = 2000,
            allocationSize = 1
    )
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "nick_name", nullable = false, unique = true, length = 24)
    private String nickName;

    @Column(name = "ingame_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private EIngameRoles ingameRole;

    @Column(name = "date_of_birth", nullable = false)
    @Past
    private LocalDate dateOfBirth;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private EGender gender;

    @Column(name = "nationality", nullable = false)
    @Enumerated(EnumType.STRING)
    private ENationality nationality;

    @ManyToOne
    private Team team;
}
