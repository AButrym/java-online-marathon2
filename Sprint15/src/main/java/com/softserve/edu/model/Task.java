package com.softserve.edu.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(generator = "task_generator")
    @GenericGenerator(
            name = "task_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
    private Long id;

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
