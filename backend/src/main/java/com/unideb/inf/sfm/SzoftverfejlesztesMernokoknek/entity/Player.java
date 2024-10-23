package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "nick_name", nullable = false, unique = true, length = 24)
    private String nickName;

    @Column(name = "date_of_birth", nullable = false)
    @Past
    private LocalDate dateOfBirth;

    @Column(name = "events", nullable = false, length = 50)
    private String events;

    @Column(name = "gender", nullable = false, length = 24)
    private String gender;

    /*
    public enum Gender {
        MALE, FEMALE, OTHER
    }
    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING) // Store as a string in the database
    private Gender gender;
    */
    // ezt a nationalityt is nemtom hogy kéne megoldani szépen h ne kézzel írós legyen. - lehet fogyatékos a logikám majd elmondom
    @Column(name = "nationality", nullable = false)
    private String nationality;



    @ManyToOne
    private Team team;
}
