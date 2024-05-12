package com.example.kanban.Repositories;

import com.example.kanban.Models.Projet;
import com.example.kanban.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjetRepository extends JpaRepository<Projet, Long> {
    List<Projet> findByOwnerId(Long ownerId);
    @Query("SELECT p FROM Projet p JOIN p.utilisateurs u WHERE u.id = :userId")
    List<Projet> findProjectsByParticipantId(Long userId);

    @Query("SELECT p.utilisateurs FROM Projet p WHERE p.id = :projetId")
    List<Utilisateur> findParticipantsByProjetId(@Param("projetId") Long projetId);
}
