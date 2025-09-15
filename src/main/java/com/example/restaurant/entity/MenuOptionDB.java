package com.example.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "menu_option")
public class MenuOptionDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // new way
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false, referencedColumnName = "id")
    @JsonIgnore     // To prevent serialization to avoid circular reference
    private MenuDB menuDB;

    @Column(name = "menu_id", insertable = false, updatable = false)
    private Long menuId;

    // new way
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false, referencedColumnName = "id")
    @JsonIgnore     // To prevent serialization to avoid circular reference
    private OptionDB optionDB;

    @Column(name = "option_id", insertable = false, updatable = false)
    private Long optionId;
}
