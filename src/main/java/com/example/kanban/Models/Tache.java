package com.example.kanban.Models;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "taches")
@Data
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "statut", nullable = false)
    private String statut;

    @Column(name = "date_echeance")
    private Date dateEcheance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigne_a")
    private Utilisateur assigneA;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colonne_id")
    private ColonneTache colonneTache;

}
