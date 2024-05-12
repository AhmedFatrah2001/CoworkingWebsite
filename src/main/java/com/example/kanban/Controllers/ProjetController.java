package com.example.kanban.Controllers;

import com.example.kanban.Models.Projet;
import com.example.kanban.Models.Utilisateur;
import com.example.kanban.Services.ProjetService;
import com.example.kanban.Services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projets")
public class ProjetController {

    private final ProjetService projetService;
    private final UtilisateurService utilisateurService;

    @Autowired
    public ProjetController(ProjetService projetService, UtilisateurService utilisateurService) {
        this.projetService = projetService;
        this.utilisateurService = utilisateurService;
    }

    // Mapping to get all projets
    @GetMapping
    public ResponseEntity<List<Projet>> getAllProjets() {
        List<Projet> projets = projetService.getAllProjets();
        return new ResponseEntity<>(projets, HttpStatus.OK);
    }

    // Mapping to create a new projet
    @PostMapping
    public ResponseEntity<Projet> createProjet(@RequestBody Projet projet, @RequestParam Long ownerId) {
        Projet createdProjet = projetService.createProjet(projet, ownerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProjet);
    }


    // Mapping to get a single projet by id
    @GetMapping("/{id}")
    public ResponseEntity<Projet> getProjetById(@PathVariable Long id) {
        Projet projet = projetService.getProjetById(id);
        if (projet != null) {
            return new ResponseEntity<>(projet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Mapping to delete a projet by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjet(@PathVariable Long id) {
        projetService.deleteProjet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Mapping to update a projet
    @PutMapping("/{projetId}")
    public ResponseEntity<?> updateProjet(@PathVariable Long projetId, @RequestBody Map<String, Object> payload) {
        Long ownerId = Long.parseLong(payload.get("ownerId").toString());
        Projet projet = new Projet();
        projet.setNom(payload.get("nom").toString());


        Projet updatedProjet = projetService.updateProjet(projetId, projet, ownerId);
        if (updatedProjet == null) {
            return ResponseEntity.badRequest().body("Failed to update projet.");
        }
        return ResponseEntity.ok(updatedProjet);
    }

    @PostMapping("/{projetId}/add-participant")
    public ResponseEntity<?> addParticipantByEmail(@PathVariable Long projetId, @RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        Utilisateur utilisateur = utilisateurService.findByEmail(email);
        if (utilisateur == null) {
            return ResponseEntity.badRequest().body("User with email " + email + " does not exist.");
        }
        Projet updatedProjet = projetService.addUtilisateurToProjet(projetId, utilisateur);
        if (updatedProjet == null) {
            return ResponseEntity.badRequest().body("Failed to add utilisateur to projet or projet does not exist.");
        }
        return ResponseEntity.ok(utilisateur);
    }


    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Projet>> getProjetsByOwner(@PathVariable Long ownerId) {
        List<Projet> projets = projetService.getProjetsByOwnerId(ownerId);
        if (projets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projets, HttpStatus.OK);
    }
    @GetMapping("/participant/{userId}")
    public ResponseEntity<List<Projet>> getProjectsByParticipant(@PathVariable Long userId) {
        List<Projet> participantProjects = projetService.getProjectsByParticipant(userId);
        if (participantProjects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(participantProjects, HttpStatus.OK);
    }
    @GetMapping("/{projetId}/participants")
    public ResponseEntity<List<Utilisateur>> getParticipantsByProjetId(@PathVariable Long projetId) {
        List<Utilisateur> participants = projetService.getProjectParticipants(projetId);
        if (participants.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(participants, HttpStatus.OK);
    }
    @DeleteMapping("/{projetId}/participants/{utilisateurId}")
    public ResponseEntity<?> removeParticipantFromProject(@PathVariable Long projetId, @PathVariable Long utilisateurId) {
        try {
            Projet updatedProjet = projetService.removeUtilisateurFromProjet(projetId, utilisateurId);
            return ResponseEntity.ok(updatedProjet);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
