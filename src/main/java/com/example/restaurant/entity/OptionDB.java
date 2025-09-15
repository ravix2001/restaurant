package com.example.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "option")
public class OptionDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // new way
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id", nullable = false, referencedColumnName = "id")
    @JsonIgnore     // To prevent serialization to avoid circular reference
    private OptionGroupDB optionGroupDB;

    @Column(name = "option_group_id", insertable = false, updatable = false)
    private Long optionGroupId;

}
