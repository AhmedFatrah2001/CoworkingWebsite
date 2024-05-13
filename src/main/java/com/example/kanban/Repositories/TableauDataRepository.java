package com.example.kanban.Repositories;

import com.example.kanban.Models.Tableau;
import com.example.kanban.Models.TableauData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TableauDataRepository extends JpaRepository<TableauData, Long> {
    TableauData findByTableauId(Long tableauId);
    Optional<TableauData> findByTableau(Tableau tableau);
}