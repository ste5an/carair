package com.mcb.creditfactory.service;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.model.Assessment;
import com.mcb.creditfactory.service.airplane.AirplaneService;
import com.mcb.creditfactory.service.assessment.AssessmentService;
import com.mcb.creditfactory.service.car.CarService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@AllArgsConstructor
@Service
public class CollateralService {
    @Autowired
    private CarService carService;

    @Autowired
    private AirplaneService airplaneService;

    @Autowired
    private AssessmentService assessmentService;

    @SuppressWarnings("ConstantConditions")
    public Long saveCollateral(Collateral object) {
        boolean approved;
        Assessment assessment;

        if (object instanceof CarDto) {
            CarDto car = (CarDto) object;
            approved = carService.approve(car);
            if (!approved) {
                return null;
            }
            assessment = assessmentService.save(car.getValue());
            return saveCar(assessment, car);
        } else if (object instanceof AirplaneDto) {
            AirplaneDto airplane = (AirplaneDto) object;
            approved = airplaneService.approve(airplane);
            if (!approved) {
                return null;
            }
            assessment = assessmentService.save(airplane.getValue());
            return saveAirplane(assessment, airplane);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Collateral getInfo(Collateral object) {
        if (object instanceof CarDto) {
            return Optional.of((CarDto) object)
                    .map(carService::fromDto)
                    .map(carService::getId)
                    .flatMap(carService::load)
                    .map(carService::toDTO)
                    .orElse(null);
        } else if (object instanceof AirplaneDto) {
            return Optional.of((AirplaneDto) object)
                    .map(airplaneService::fromDto)
                    .map(airplaneService::getId)
                    .flatMap(airplaneService::load)
                    .map(airplaneService::toDTO)
                    .orElse(null);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Long saveCar(Assessment assessment, CarDto carDto) {
        return Optional.of(carDto)
                .map(carService::fromDto)
                .map(car -> {
                    Long id = car.getId();
                    List<Assessment> list = new ArrayList<>();
                    if (id != null) {
                        list = carService.load(id).get().getAssessments();
                    }
                    list.add(assessment);
                    car.setAssessments(list);
                    return carService.save(car);
                })
                .map(carService::getId)
                .orElse(null);
    }

    private Long saveAirplane(Assessment assessment, AirplaneDto airplaneDto) {
        return Optional.of(airplaneDto)
                .map(airplaneService::fromDto)
                .map(airplane -> {
                    Long id = airplane.getId();
                    List<Assessment> list = new ArrayList<>();
                    if (id != null) {
                        list = airplaneService.load(id).get().getAssessments();
                    }
                    list.add(assessment);
                    airplane.setAssessments(list);
                    return airplaneService.save(airplane);
                })
                .map(airplaneService::getId)
                .orElse(null);
    }

}