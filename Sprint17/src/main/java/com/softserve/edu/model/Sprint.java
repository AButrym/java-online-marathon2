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
public class Sprint {

    @Id
    @GeneratedValue(generator = "sprint_generator")
    @GenericGenerator(
            name = "sprint_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
    private Long id;

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
