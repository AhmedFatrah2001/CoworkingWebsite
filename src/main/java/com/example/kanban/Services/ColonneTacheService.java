package com.example.kanban.Services;

import com.example.kanban.Models.ColonneTache;
import com.example.kanban.Repositories.ColonneTacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ColonneTacheService {

    private final ColonneTacheRepository colonneTacheRepository;

    @Autowired
    public ColonneTacheService(ColonneTacheRepository colonneTacheRepository) {
        this.colonneTacheRepository = colonneTacheRepository;
    }

    public List<ColonneTache> getAllColonneTaches() {
        return colonneTacheRepository.findAll();
    }

    public ColonneTache createColonneTache(ColonneTache colonneTache) {
        return colonneTacheRepository.save(colonneTache);
    }

    public ColonneTache getColonneTacheById(Long id) {
        Optional<ColonneTache> colonneTacheOptional = colonneTacheRepository.findById(id);
        return colonneTacheOptional.orElse(null);
    }

    public void deleteColonneTache(Long id) {
        colonneTacheRepository.deleteById(id);
    }

    public ColonneTache updateColonneTache(Long id, ColonneTache colonneTache) {
        Optional<ColonneTache> existingColonneTacheOptional = colonneTacheRepository.findById(id);
        if (existingColonneTacheOptional.isPresent()) {
            colonneTache.setId(id);
            return colonneTacheRepository.save(colonneTache);
        } else {
            return null;
        }
    }
}
