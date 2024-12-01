package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.ERole;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.validation.StrongPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "user_gen")
    @TableGenerator(
            name = "user_gen",
            table = "id_gen_table",
            pkColumnName = "gen_name",
            valueColumnName = "gen_value",
            pkColumnValue = "user_id",
            initialValue = 1000,
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String username;

    @Column(nullable = false)
    @NotBlank
    //@StrongPassword
    private String password;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player player;

    @Enumerated(EnumType.STRING)
    private ERole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
