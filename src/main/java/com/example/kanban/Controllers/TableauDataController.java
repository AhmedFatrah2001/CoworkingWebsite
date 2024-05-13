package com.example.kanban.Controllers;

import com.example.kanban.Models.Tableau;
import com.example.kanban.Models.TableauData;
import com.example.kanban.Repositories.TableauRepository;
import com.example.kanban.Services.TableauDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tableauData")
public class TableauDataController {

    @Autowired
    private TableauDataService tableauDataService;
    @Autowired
    private TableauRepository tableauRepository;

    @PostMapping
    public ResponseEntity<TableauData> createTableauData(@RequestBody TableauData tableauData) {
        TableauData savedData = tableauDataService.saveTableauData(tableauData);
        return ResponseEntity.ok(savedData);
    }

    @GetMapping("/{tableau_id}")
    public ResponseEntity<String> getTableauDataByTableauId(@PathVariable("tableau_id") Long tableauId) {
        TableauData data = tableauDataService.getTableauDataByTableauId(tableauId);
        if (data != null) {
            return ResponseEntity.ok(data.getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<TableauData> updateOrCreateTableauData(@PathVariable Long id, @RequestBody String jsonData) {
        TableauData result = tableauDataService.updateOrCreateTableauData(id, jsonData);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}