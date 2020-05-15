package com.mcb.creditfactory.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Access(AccessType.FIELD)
public class CollateralBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;

    @Column(name = "year_of_issue")
    private Short year;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Assessment> assessments;

    public CollateralBaseEntity(Long id, String brand, String model, Short year) {
        this(id, brand, model, year, Collections.emptyList());
    }
}
