package com.example.kanban.Services;

import com.example.kanban.Models.Tache;
import com.example.kanban.Repositories.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TacheService {

    private final TacheRepository tacheRepository;

    @Autowired
    public TacheService(TacheRepository tacheRepository) {
        this.tacheRepository = tacheRepository;
    }

    public List<Tache> getAllTaches() {
        return tacheRepository.findAll();
    }

    public Tache createTache(Tache tache) {
        return tacheRepository.save(tache);
    }

    public Tache getTacheById(Long id) {
        Optional<Tache> tacheOptional = tacheRepository.findById(id);
        return tacheOptional.orElse(null);
    }

    public void deleteTache(Long id) {
        tacheRepository.deleteById(id);
    }

    public Tache updateTache(Long id, Tache tache) {
        Optional<Tache> existingTacheOptional = tacheRepository.findById(id);
        if (existingTacheOptional.isPresent()) {
            tache.setId(id);
            return tacheRepository.save(tache);
        } else {
            return null;
        }
    }
}
