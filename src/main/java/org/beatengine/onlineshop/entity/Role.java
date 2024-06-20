package org.beatengine.onlineshop.entity;

import jakarta.persistence.*;


import java.math.BigInteger;
import java.util.Set;

@Entity
public class Role extends SuperEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(columnDefinition = "BIGINT")
    private Long id;

    @Column
    private String name;

    @Column
    private String displayName;
    @Column
    private String description;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "UserRole",
            joinColumns = { @JoinColumn(name = "roleId") },
            inverseJoinColumns = { @JoinColumn(name = "userId") })
    Set<User> users;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
