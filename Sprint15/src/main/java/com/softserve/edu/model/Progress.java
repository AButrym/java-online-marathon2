package com.softserve.edu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Progress {

    public enum TaskStatus {
        NEW, SUBMITTED, PASS, FAIL
    }

    @Id
    @GeneratedValue(generator = "progress_generator")
    @GenericGenerator(
            name = "progress_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column
    private Instant started;

    @Column
    private Instant updated;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private User trainee;
}
