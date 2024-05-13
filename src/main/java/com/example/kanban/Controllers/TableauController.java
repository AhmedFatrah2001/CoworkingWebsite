package com.example.kanban.Controllers;

import com.example.kanban.Models.Tableau;
import com.example.kanban.Models.ColonneTache;
import com.example.kanban.Models.Tache;
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
    @GetMapping("/{id}/board")
    public ResponseEntity<BoardResponse> getTableauBoard(@PathVariable Long id) {
        // Fetching the lanes (colonnes) for the tableau
        List<Object[]> colonnes = entityManager.createQuery(
                        "SELECT c.id, c.nom FROM ColonneTache c WHERE c.tableau.id = :id", Object[].class)
                .setParameter("id", id)
                .getResultList();

        BoardResponse boardResponse = new BoardResponse();
        List<Lane> lanes = new ArrayList<>();

        for (Object[] colonne : colonnes) {
            Long colonneId = (Long) colonne[0];
            String colonneNom = (String) colonne[1];

            Lane lane = new Lane();
            lane.setId(colonneId.toString());
            lane.setTitle(colonneNom);

            List<Card> cards = entityManager.createQuery(
                            "SELECT t.id, t.titre, t.description FROM Tache t WHERE t.colonneTache.id = :colonneId", Object[].class)
                    .setParameter("colonneId", colonneId)
                    .getResultList()
                    .stream()
                    .map(result -> {
                        Card card = new Card();
                        card.setId(((Long) result[0]).toString());
                        card.setTitle((String) result[1]);
                        card.setDescription((String) result[2]);
                        return card;
                    })
                    .collect(Collectors.toList());

            lane.setCards(cards);
            lanes.add(lane);
        }

        boardResponse.setLanes(lanes);
        return ResponseEntity.ok(boardResponse);
    }
    @Transactional
    @PostMapping("/{id}/updateBoard")
    public ResponseEntity<Void> updateTableauBoard(@PathVariable Long id, @RequestBody BoardResponse board) {
        Tableau boardEntity = entityManager.find(Tableau.class, id);

        board.getLanes().forEach(lane -> {
            ColonneTache colonne = entityManager.find(ColonneTache.class, Long.parseLong(lane.getId()));
            if (colonne == null) {
                colonne = new ColonneTache();
                colonne.setNom(lane.getTitle());
                colonne.setTableau(boardEntity);
            } else {
                colonne.setNom(lane.getTitle());
            }
            entityManager.persist(colonne);

            ColonneTache finalColonne = colonne;
            lane.getCards().forEach(card -> {
                Tache tache = null;
                try {
                    tache = entityManager.find(Tache.class, Long.parseLong(card.getId()));
                } catch (NumberFormatException e) {
                    // Expected when card.getId() is not a valid long number
                }
                if (tache == null) {
                    tache = new Tache();
                    tache.setTitre(card.getTitle());
                    tache.setDescription(card.getDescription());
                    tache.setColonneTache(finalColonne);
                } else {
                    tache.setTitre(card.getTitle());
                    tache.setDescription(card.getDescription());
                }
                entityManager.persist(tache);
            });
        });

        return ResponseEntity.ok().build();
    }




}
