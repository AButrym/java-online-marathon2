package com.softserve.edu.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString(exclude = {"sprints", "users"})
@EqualsAndHashCode(exclude = {"sprints", "users"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Marathon {

    @Id
    @GeneratedValue(generator = "marathon_generator")
    @GenericGenerator(
            name = "marathon_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
    private Long id;

    @Column
    @NotNull
    private String title;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "marathon")
    private Set<Sprint> sprints = new HashSet<>();

    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "marathon_user",
            joinColumns = {
                    @JoinColumn(
                            name = "marathon_id",
                            referencedColumnName = "id"
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "user_id",
                            referencedColumnName = "id"
                    )
            }
    )
    private Set<User> users = new HashSet<>();
}
