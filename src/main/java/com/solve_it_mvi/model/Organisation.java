package com.solve_it_mvi.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "organisation")
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="organisations_id_seq")
    @SequenceGenerator(name="organisations_id_seq", sequenceName="organisations_id_seq", allocationSize=1)
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
