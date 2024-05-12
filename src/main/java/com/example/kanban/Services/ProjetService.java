package com.example.kanban.Services;

import com.example.kanban.Models.Projet;
import com.example.kanban.Models.Utilisateur;
import com.example.kanban.Repositories.ProjetRepository;
import com.example.kanban.Repositories.TableauRepository;
import com.example.kanban.Repositories.UtilisateurRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjetService {

    @Autowired
    private EntityManager entityManager;
    private final ProjetRepository projetRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private TableauRepository tableauRepository;
    @Autowired
    public ProjetService(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    public List<Projet> getAllProjets() {
        return projetRepository.findAll();
    }

    public Projet createProjet(Projet projet, Long ownerId) {
        Utilisateur owner = utilisateurRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found with id: " + ownerId));
        projet.setOwner(owner);
        return projetRepository.save(projet);
    }

    public Projet getProjetById(Long id) {
        Optional<Projet> projetOptional = projetRepository.findById(id);
        return projetOptional.orElse(null);
    }

    @Transactional
    public void deleteProjet(Long projetId) {
        tableauRepository.deleteByProjetId(projetId);
        projetRepository.deleteById(projetId);
    }

    public Projet updateProjet(Long projetId, Projet updatedProjet, Long ownerId) {
        Utilisateur owner = utilisateurRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found with id: " + ownerId));
        return projetRepository.findById(projetId)
                .map(projet -> {
                    projet.setNom(updatedProjet.getNom());
                    projet.setOwner(owner);
                    projet.setUtilisateurs(updatedProjet.getUtilisateurs());
                    return projetRepository.save(projet);
                })
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + projetId));
    }

    public Projet addUtilisateurToProjet(Long projetId, Utilisateur utilisateur) {
        Projet projet = projetRepository.findById(projetId).orElse(null);
        if (projet != null && !projet.getUtilisateurs().contains(utilisateur)) {
            projet.getUtilisateurs().add(utilisateur);
            return projetRepository.save(projet);
        }
        return null;
    }
    public List<Projet> getProjetsByOwnerId(Long ownerId) {
        return projetRepository.findByOwnerId(ownerId);
    }
    public List<Projet> getProjectsByParticipant(Long userId) {
        return projetRepository.findProjectsByParticipantId(userId);
    }
    public List<Utilisateur> getProjectParticipants(Long projetId) {
        return projetRepository.findParticipantsByProjetId(projetId);
    }
    @Transactional
    public Projet removeUtilisateurFromProjet(Long projetId, Long utilisateurId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + projetId));
        Query query = entityManager.createNativeQuery("DELETE FROM projet_utilisateurs WHERE projet_id = "+projetId+" AND utilisateur_id ="+utilisateurId);
        query.executeUpdate();

        entityManager.refresh(projet);

        return projet;
    }

}
