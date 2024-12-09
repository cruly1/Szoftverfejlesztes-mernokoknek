package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "image_gen")
    @TableGenerator(
            name = "image_gen",
            table = "id_gen_table",
            pkColumnName = "gen_name",
            valueColumnName = "gen_value",
            pkColumnValue = "image_id",
            initialValue = 5000,
            allocationSize = 1
    )
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String type;

    @Lob
    @Column(name = "imagedata", length = 10000)
    private byte[] imageData;
}