package edu.softserve.jom.sprint13.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.awt.print.Book;
import java.util.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Marathon {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    @NotNull
    private String title;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "marathon")
    @Builder.Default
    private Set<Sprint> sprints = new HashSet<>();

    @ToString.Exclude
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
