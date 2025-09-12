package com.example.restaurant.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "item_size", uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "size_id"}))
public class ItemSize {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

//    // getters & setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public Item getItem() { return item; }
//    public void setItem(Item item) { this.item = item; }
//    public Size getSize() { return size; }
//    public void setSize(Size size) { this.size = size; }
//    public BigDecimal getPrice() { return price; }
//    public void setPrice(BigDecimal price) { this.price = price; }
}
