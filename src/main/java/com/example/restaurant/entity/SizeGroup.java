package com.example.restaurant.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "size_group")
public class SizeGroup {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "sizeGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Size> sizes = new ArrayList<>();

//    // getters & setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//    public Category getCategory() { return category; }
//    public void setCategory(Category category) { this.category = category; }
//    public List<Size> getSizes() { return sizes; }
//    public void setSizes(List<Size> sizes) { this.sizes = sizes; }
}
