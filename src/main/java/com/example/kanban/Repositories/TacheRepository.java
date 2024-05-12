package com.example.kanban.Repositories;

import com.example.kanban.Models.Tache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacheRepository extends JpaRepository<Tache, Long> {

}
