package com.example.restaurant.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "item_size", uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "size_id"}))
public class MenuSizeDB {

    // price initialization
    public MenuSizeDB() {
        this.price = BigDecimal.ZERO;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private MenuDB item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id", nullable = false)
    private SizeDB sizeDB;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

}
