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
public class Sprint {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    @NotNull
    private String title;

    @Column
    private Instant finish;

    @Column
    private Instant startDate;

    @ManyToOne
    @JoinColumn(name = "marathon_id")
    private Marathon marathon;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "sprint")
    @Builder.Default
    private Set<Task> tasks = new HashSet<>();
}
