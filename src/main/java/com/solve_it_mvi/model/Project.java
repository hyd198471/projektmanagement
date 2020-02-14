package com.solve_it_mvi.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "project")
public class Project {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "display_name")
    private String displayName;

    @ManyToOne(optional = true)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Project parent;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "parent_id")
    @OrderColumn
    private List<Project> subProjects = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_membership_id", referencedColumnName = "id")
    private List<ProjectMembership> projectMemberships;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Project getParent() {
        return parent;
    }

    public void setParent(Project parentProject) {
        this.parent = parentProject;
    }

    public List<Project> getSubProjects() {
        return subProjects;
    }

    public void setSubProjects(List<Project> childrenProjects) {
        this.subProjects = childrenProjects;
    }
}
