package com.mcb.creditfactory.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "AIRPLANE")
public class Airplane extends CollateralBaseEntity {
    private String manufacturer;

    @Column(name = "fuel_capacity")
    private int fuelCapacity;
    private int seats;

    public Airplane(Long id, String brand, String model, Short year,String manufacturer, int fuelCapacity, int seats) {
        super(id, brand, model, year);
        this.fuelCapacity = fuelCapacity;
        this.manufacturer = manufacturer;
        this.seats = seats;
    }

}