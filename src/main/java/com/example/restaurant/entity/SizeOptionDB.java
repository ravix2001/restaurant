package com.example.restaurant.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "size_option")
public class SizeOptionDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id", nullable = false, referencedColumnName = "id")
    private SizeDB sizeDB;

    @Column(name = "size_id", insertable = false, updatable = false)
    private Long sizeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false, referencedColumnName = "id")
    private OptionDB optionDB;

    @Column(name = "option_id", insertable = false, updatable = false)
    private Long optionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_group_option_group_id", nullable = false, referencedColumnName = "id")
    private SizeGroupOptionGroupDB sizeGroupOptionGroupDB;

    @Column(name = "size_group_option_group_id", insertable = false, updatable = false)
    private Long sizeGroupOptionGroupId;
}
