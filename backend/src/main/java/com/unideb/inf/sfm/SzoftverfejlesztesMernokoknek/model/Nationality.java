package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nationalities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Nationality {

    @Id
    @TableGenerator(
            name = "nat_gen",
            table = "id_gen_table",
            pkColumnName = "gen_name",
            valueColumnName = "gen_value",
            pkColumnValue = "nat_id",
            initialValue = 6000,
            allocationSize = 1
    )
    private Long id;

    @Column(name = "country_name", nullable = false)
    private String countryName;

    @Column(nullable = false)
    private String demonym;

    @Column(nullable = false)
    private String cca2;
}
