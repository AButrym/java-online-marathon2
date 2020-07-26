package edu.softserve.jom.sprint13.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    public enum Role {
        MENTOR, TRAINEE
    }

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    @NotNull
    @Email
    private String email;

    @Column
    @NotNull
    private String firstName;

    @Column
    @NotNull
    @Size(min = 2, max = 20,
            message = "Last name must be between 2 and 20 characters long")
    @Pattern(regexp = "\\p{Lu}\\pL*(-\\p{Lu}\\pL*)?",
            message = "Last name must contain letters only and be capitalized")
    private String lastName;

    @Column
    @NotNull
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "trainee")
    private Set<Progress> progresses = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "users")
    private Set<Marathon> marathons = new HashSet<>();
}
