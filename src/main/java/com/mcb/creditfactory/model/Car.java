package com.mcb.creditfactory.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "CAR")
public class Car extends CollateralBaseEntity {

    private Double power;

    public Car(Long id, String brand, String model, Double power, Short year) {
        super(id, brand, model, year);
        this.power = power;
    }
}
