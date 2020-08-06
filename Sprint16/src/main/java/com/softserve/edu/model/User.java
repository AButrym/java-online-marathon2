package com.softserve.edu.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
@Data
@ToString(exclude = {"progresses", "marathons"})
@EqualsAndHashCode(exclude = {"progresses", "marathons"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public enum Role {
        MENTOR, TRAINEE
    }

    @Id
    @GeneratedValue(generator = "user_generator")
    @GenericGenerator(
            name = "user_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
    private Long id;

    @Column(unique = true)
    @NotNull
    @Email
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    @Size(min = 2, max = 20,
            message = "Last name must be between 2 and 20 characters long")
    @Pattern(regexp = "\\p{Lu}\\pL*(-\\p{Lu}\\pL*)?",
            message = "Last name must contain letters only and be capitalized")
    private String lastName;

    @NotNull
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "trainee")
    private Set<Progress> progresses = new HashSet<>();

    @Builder.Default
    @ManyToMany(cascade = CascadeType.REMOVE,
            mappedBy = "users")
    private Set<Marathon> marathons = new HashSet<>();
}
