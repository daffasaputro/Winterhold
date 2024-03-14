package com.winterhold.Winterhold.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Category")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Category {
    @Id
    @Column(name = "Name")
    private String name;

    @Column(name = "Floor")
    private Integer floor;

    @Column(name = "Isle")
    private String isle;

    @Column(name = "Bay")
    private String bay;
}
