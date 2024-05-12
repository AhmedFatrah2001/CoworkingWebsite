package com.example.kanban.Models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "colonneTaches")
@Data
public class ColonneTache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tableau_id")
    private Tableau tableau;

}