package io.exiled.salesbook.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name = "sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int totalCost;
    private String name;
    private String cat;
    private String email;
    private String description;
    private boolean isDone;

    public Sale() {
    }

    public Sale(int totalCost, String name, String cat, String email, String description, boolean isDone) {
        this.totalCost = totalCost;
        this.name = name;
        this.cat = cat;
        this.email = email;
        this.description = description;
        this.isDone = isDone;
    }
}
