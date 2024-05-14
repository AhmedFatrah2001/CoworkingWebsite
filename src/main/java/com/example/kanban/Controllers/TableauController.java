package com.example.kanban.Controllers;

import com.example.kanban.Models.Tableau;
import com.example.kanban.Services.TableauService;
import com.example.kanban.dto.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tableaux")
public class TableauController {

    private final TableauService tableauService;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    public TableauController(TableauService tableauService) {
        this.tableauService = tableauService;
    }

    @GetMapping
    public ResponseEntity<List<Tableau>> getAllTableaux() {
        List<Tableau> tableaux = tableauService.getAllTableaux();
        return new ResponseEntity<>(tableaux, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Tableau> createTableau(@RequestBody Tableau tableau) {
        Tableau createdTableau = tableauService.createTableau(tableau);
        return new ResponseEntity<>(createdTableau, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tableau> getTableauById(@PathVariable Long id) {
        Tableau tableau = tableauService.getTableauById(id);
        if (tableau != null) {
            return new ResponseEntity<>(tableau, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTableau(@PathVariable Long id) {
        tableauService.deleteTableau(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tableau> updateTableau(@PathVariable Long id, @RequestBody Tableau tableau) {
        Tableau updatedTableau = tableauService.updateTableau(id, tableau);
        if (updatedTableau != null) {
            return new ResponseEntity<>(updatedTableau, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/by-projet/{projetId}")
    public ResponseEntity<List<Tableau>> getTableauxByProjetId(@PathVariable Long projetId) {
        List<Tableau> tableaux = tableauService.getTableauxByProjetId(projetId);
        if (tableaux.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tableaux);
    }

}
