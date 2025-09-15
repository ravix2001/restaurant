package com.example.restaurant.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "size_group_option_group")
public class SizeGroupOptionGroupDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_group_id", nullable = false, referencedColumnName = "id")
    private SizeGroupDB sizeGroupDB;

    @Column(name = "size_group_id", insertable = false, updatable = false)
    private Long sizeGroupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id", nullable = false, referencedColumnName = "id")
    private OptionGroupDB optionGroupDB;

    @Column(name = "option_group_id", insertable = false, updatable = false)
    private Long optionGroupId;

}
