package com.mcb.creditfactory.service;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.model.Airplane;
import com.mcb.creditfactory.model.Assessment;
import com.mcb.creditfactory.model.Car;
import com.mcb.creditfactory.service.airplane.AirplaneService;
import com.mcb.creditfactory.service.assessment.AssessmentService;
import com.mcb.creditfactory.service.car.CarService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CollateralService {
    private CarService carService;
    private AirplaneService airplaneService;
    private AssessmentService assessmentService;


    public CollateralService(CarService carService, AirplaneService airplaneService, AssessmentService assessmentService) {
        this.carService = carService;
        this.airplaneService = airplaneService;
        this.assessmentService = assessmentService;
    }

    public Long saveCollateral(Collateral object) {
        Assessment assessment;

        if (object instanceof CarDto) {

            CarDto carDto = (CarDto) object;
            boolean approved = carService.approve(carDto);

            if (!approved) {
                return null;
            }

            assessment = assessmentService.save(carDto.getValue());

            return saveCarEntityAndGetId(assessment, carDto);

        } else if (object instanceof AirplaneDto) {

            AirplaneDto airplaneDto = (AirplaneDto) object;
            boolean approved = airplaneService.approve(airplaneDto);

            if (!approved) {
                return null;
            }

            assessment = assessmentService.save(airplaneDto.getValue());

            return saveAirplaneEntityAndGetId(assessment, airplaneDto);

        } else throw new IllegalArgumentException();

    }

    private Long saveAirplaneEntityAndGetId(Assessment assessment, AirplaneDto airplaneDto) {
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

    private Long saveCarEntityAndGetId(Assessment assessment, CarDto carDto) {
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

    public Collateral getInfo(Collateral object) {
        if (object instanceof CarDto) {

            return Optional.of((CarDto) object)
                    .flatMap(carDto -> carService.load(carDto.getId()))
                    .map(carService::toDTO)
                    .orElse(null);

        } else if (object instanceof AirplaneDto) {

            return Optional.of((AirplaneDto) object)
                    .flatMap(airplaneDto -> airplaneService.load(airplaneDto.getId()))
                    .map(airplaneService::toDTO)
                    .orElse(null);

        } else throw new IllegalArgumentException();
    }
}