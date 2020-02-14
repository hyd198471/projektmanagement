package com.solve_it_mvi.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "project_membership")
public class ProjectMembership {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(cascade= CascadeType.MERGE,fetch=FetchType.LAZY)
    @JoinTable(name="project_membership_role",
            joinColumns = {@JoinColumn(name="project_membership_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")}
    )
    private List<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
