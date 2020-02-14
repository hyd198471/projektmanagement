package com.solve_it_mvi.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "user_")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String userName;

    private String encodedPassword;

    private long lastLoginDate;

    @ManyToMany(cascade=CascadeType.MERGE,fetch=FetchType.LAZY)
    @JoinTable(name="user_role",
            joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")}
    )
    private List<Role> roles;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public long getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(long lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userName.equals(user.userName) &&
                encodedPassword.equals(user.encodedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, encodedPassword);
    }
}
