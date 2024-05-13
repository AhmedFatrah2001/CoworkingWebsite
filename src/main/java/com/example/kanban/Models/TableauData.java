package com.example.kanban.Models;
import com.example.kanban.Models.Tableau;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "tableau_data")
public class TableauData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "json")
    private String data;

    @ManyToOne
    @JoinColumn(name = "tableau_id", nullable = false)
    private Tableau tableau;
}
