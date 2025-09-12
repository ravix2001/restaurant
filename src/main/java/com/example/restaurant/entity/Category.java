package com.example.restaurant.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "category")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SizeGroup> sizeGroups = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<Item> items = new ArrayList<>();

//    // getters & setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//    public List<SizeGroup> getSizeGroups() { return sizeGroups; }
//    public void setSizeGroups(List<SizeGroup> sizeGroups) { this.sizeGroups = sizeGroups; }
//    public List<Item> getItems() { return items; }
//    public void setItems(List<Item> items) { this.items = items; }
}
