package com.mcb.creditfactory.service.car;


import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.external.ExternalApproveService;
import com.mcb.creditfactory.model.Car;
import com.mcb.creditfactory.repository.CarRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceImplTest {

    //Сервис, который тестируем
    @InjectMocks
    public CarServiceImpl carService;
    //Инжектим заглушки(моки) вместо реальных сервисов, поскольку тестируем логику только сервиса CarServiceImpl
    @Mock
    private ExternalApproveService approveService;
    @Mock
    private CarRepository carRepository;

    @Test
    public void successfullyConvertFromDto() {
        long id = 1L;
        String brand = "BMW";
        String model = "testModel";
        double power = 10.00;
        Short year = Short.MAX_VALUE;
        BigDecimal value = BigDecimal.TEN;

        CarDto carDto = new CarDto(id, brand, model, power, year, value);

        Car car = carService.fromDto(carDto);

        assertEquals(id, car.getId().longValue());
        assertEquals(brand, car.getBrand());
        assertEquals(model, car.getModel());
        assertEquals(power, car.getPower(), 0.001);
        //todo в CarDto у тебя есть value, а в Car нет, ты его не проебал случаем?
    }

    @Test
    public void successfullyGetApprove() {
        //todo глянь примеры как с мокито работать https://www.baeldung.com/mockito-annotations
        when(approveService.approve(any())).thenReturn(0);

        boolean result = carService.approve(new CarDto());

        assertTrue(result);
    }

    @Test
    public void failGetApprove() {
        //todo глянь примеры как с мокито работать https://www.baeldung.com/mockito-annotations
        when(approveService.approve(any())).thenReturn(10);

        boolean result = carService.approve(new CarDto());

        assertFalse(result);
    }

}
