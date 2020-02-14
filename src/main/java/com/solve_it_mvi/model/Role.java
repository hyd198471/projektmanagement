package com.solve_it_mvi.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "display_name")
    private String displayName;

    @ManyToMany(cascade=CascadeType.MERGE,fetch=FetchType.LAZY)
    @JoinTable(name="role_permission",
            joinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="permission_id", referencedColumnName="id")}
    )
    private List<Permission> permissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String name) {
        this.displayName = name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
