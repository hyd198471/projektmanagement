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

    @Column(name = "type")
    private OrganisationType type;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "organisation_id", referencedColumnName = "id")
    private List<Project> projects;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "organisation_id", referencedColumnName = "id")
    private List<User> users;
}
