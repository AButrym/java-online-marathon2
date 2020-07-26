package edu.softserve.jom.sprint13.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    @NotNull
    private String title;

    @Column
    private Instant created;

    @Column
    private Instant updated;

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "task")
    @Builder.Default
    private Set<Progress> progresses = new HashSet<>();
}
