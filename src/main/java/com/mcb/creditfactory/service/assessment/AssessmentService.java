package com.mcb.creditfactory.service.assessment;

import com.mcb.creditfactory.dto.AssessmentDto;
import com.mcb.creditfactory.model.Assessment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AssessmentService {
    Assessment save(Assessment assessment);
    List<Assessment> getAll();
    Assessment getById(Long id);
    void deleteById(Long id);
    Assessment save(BigDecimal value);
}