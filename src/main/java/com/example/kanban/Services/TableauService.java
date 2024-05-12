package com.example.kanban.Services;

import com.example.kanban.Models.Tableau;
import com.example.kanban.Repositories.TableauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TableauService {

    private final TableauRepository tableauRepository;

    @Autowired
    public TableauService(TableauRepository tableauRepository) {
        this.tableauRepository = tableauRepository;
    }

    public List<Tableau> getAllTableaux() {
        return tableauRepository.findAll();
    }

    public Tableau createTableau(Tableau tableau) {
        return tableauRepository.save(tableau);
    }

    public Tableau getTableauById(Long id) {
        Optional<Tableau> tableauOptional = tableauRepository.findById(id);
        return tableauOptional.orElse(null);
    }

    public void deleteTableau(Long id) {
        tableauRepository.deleteById(id);
    }

    public Tableau updateTableau(Long id, Tableau tableau) {
        Optional<Tableau> existingTableauOptional = tableauRepository.findById(id);
        if (existingTableauOptional.isPresent()) {
            tableau.setId(id);
            return tableauRepository.save(tableau);
        } else {
            return null;
        }
    }
    public List<Tableau> getTableauxByProjetId(Long projetId) {
        return tableauRepository.findByProjetId(projetId);
    }
}
