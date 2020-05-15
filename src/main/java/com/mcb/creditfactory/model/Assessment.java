package com.mcb.creditfactory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ASSESSED_VALUE")
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="assessed_value")
    private BigDecimal assessedValue;

    @Column(name = "assessment_date")
    private LocalDate localDate = LocalDate.now();

    public Assessment(BigDecimal assessedValue) {
        this.assessedValue = assessedValue;
    }

    public Assessment(Long id, BigDecimal assessedValue) {
        this.id = id;
        this.assessedValue = assessedValue;
    }
}