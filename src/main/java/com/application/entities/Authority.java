package com.application.entities;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Authority() { }
    public Authority(String name) { this.name = name; }

    public Authority(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }

    public Long getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
