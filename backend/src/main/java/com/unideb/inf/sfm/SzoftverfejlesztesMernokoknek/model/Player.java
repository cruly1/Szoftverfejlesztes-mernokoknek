package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.EGender;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.EIngameRoles;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.ENationality;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(mappedBy = "players")
    private Set<Event> events = new HashSet<>();
}
