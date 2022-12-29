package com.application.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities = new ArrayList<>();

    public Role() { }
    public Role(String name) { this.name = name; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name;}
    public void setAuthorities(List<Authority> authorities) { this.authorities = authorities; }

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<Authority> getAuthorities() { return authorities; }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
