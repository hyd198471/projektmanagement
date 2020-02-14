package com.solve_it_mvi.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "organisation")
public class Organisation {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "display_name")
    private String displayName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private List<Project> projects;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private List<Subject> subjects;
}
