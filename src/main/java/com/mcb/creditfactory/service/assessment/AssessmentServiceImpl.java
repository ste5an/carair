package com.mcb.creditfactory.service.assessment;

import com.mcb.creditfactory.dto.AssessmentDto;
import com.mcb.creditfactory.model.Assessment;
import com.mcb.creditfactory.repository.AssessmentRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AssessmentServiceImpl implements AssessmentService {
    private AssessmentRepository repository;

    public AssessmentServiceImpl(AssessmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Assessment save(Assessment assessment) {
        return repository.save(assessment);
    }

    @Override
    public List<Assessment> getAll() {
        List<Assessment> list = new ArrayList<>();
        Iterable<Assessment> iterable = repository.findAll();
        iterable.forEach(list::add);
        return list;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Long getId(Assessment assessment) {
        return assessment.getId();
    }

    public Assessment save(BigDecimal value) {
        return repository.save(new Assessment(value));
    }

    public Assessment getById(Long id) {
        return repository.findById(id).orElse(null);
    }

}
