package com.example.kanban.Models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tableaus")
@Data
public class Tableau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projet_id")
    private Projet projet;
}