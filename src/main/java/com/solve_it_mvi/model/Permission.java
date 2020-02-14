package com.solve_it_mvi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "permission")
public class Permission {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "display_name")
    private String displayName;


}
