package com.example.kanban.Repositories;

import com.example.kanban.Models.Tableau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TableauRepository extends JpaRepository<Tableau, Long> {
    List<Tableau> findByProjetId(Long projetId);
    @Transactional
    @Modifying
    @Query("DELETE FROM Tableau t WHERE t.projet.id = :projetId")
    void deleteByProjetId(Long projetId);

}
