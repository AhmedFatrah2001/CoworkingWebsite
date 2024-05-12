package com.example.kanban.Controllers;

import com.example.kanban.Models.ColonneTache;
import com.example.kanban.Services.ColonneTacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/colonnes")
public class ColonneTacheController {

    private final ColonneTacheService colonneTacheService;

    @Autowired
    public ColonneTacheController(ColonneTacheService colonneTacheService) {
        this.colonneTacheService = colonneTacheService;
    }
    @GetMapping
    public ResponseEntity<List<ColonneTache>> getAllColonnes() {
        List<ColonneTache> colonnes = colonneTacheService.getAllColonneTaches();
        return new ResponseEntity<>(colonnes, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ColonneTache> createColonne(@RequestBody ColonneTache colonneTache) {
        ColonneTache createdColonne = colonneTacheService.createColonneTache(colonneTache);
        return new ResponseEntity<>(createdColonne, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColonneTache> getColonneById(@PathVariable Long id) {
        ColonneTache colonneTache = colonneTacheService.getColonneTacheById(id);
        if (colonneTache != null) {
            return new ResponseEntity<>(colonneTache, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColonne(@PathVariable Long id) {
        colonneTacheService.deleteColonneTache(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColonneTache> updateColonne(@PathVariable Long id, @RequestBody ColonneTache colonneTache) {
        ColonneTache updatedColonne = colonneTacheService.updateColonneTache(id, colonneTache);
        if (updatedColonne != null) {
            return new ResponseEntity<>(updatedColonne, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
