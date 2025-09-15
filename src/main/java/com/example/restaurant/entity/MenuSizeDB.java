package com.example.restaurant.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "menu_size")
public class MenuSizeDB {

    // price initialization
    public MenuSizeDB() {
        this.price = BigDecimal.ZERO;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // new way
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false, referencedColumnName = "id")
    private MenuDB menuDB;

    @Column(name = "menu_id", insertable = false, updatable = false)
    private Long menuId;

    // new way
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id", nullable = false, referencedColumnName = "id")
    private SizeDB sizeDB;

    @Column(name = "size_id", insertable = false, updatable = false)
    private Long sizeId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

}
