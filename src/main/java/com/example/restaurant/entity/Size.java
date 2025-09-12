package com.example.restaurant.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "size")
public class Size {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_group_id", nullable = false)
    private SizeGroup sizeGroup;

    @OneToMany(mappedBy = "size")
    private List<ItemSize> itemSizes = new ArrayList<>();

//    // getters & setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//    public SizeGroup getSizeGroup() { return sizeGroup; }
//    public void setSizeGroup(SizeGroup sizeGroup) { this.sizeGroup = sizeGroup; }
//    public List<ItemSize> getItemSizes() { return itemSizes; }
//    public void setItemSizes(List<ItemSize> itemSizes) { this.itemSizes = itemSizes; }
}
