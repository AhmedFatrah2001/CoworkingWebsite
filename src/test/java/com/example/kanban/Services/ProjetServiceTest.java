package com.example.kanban.Services;

import com.example.kanban.Models.Projet;
import com.example.kanban.Repositories.ProjetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

public class ProjetServiceTest {

    @Mock
    private ProjetRepository projetRepository;

    @InjectMocks
    private ProjetService projetService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllProjets_ShouldReturnAllProjects() {
        // Given
        Projet projet1 = new Projet();
        projet1.setId(1L);
        projet1.setNom("Project A");
        Projet projet2 = new Projet();
        projet2.setId(2L);
        projet2.setNom("Project B");

        List<Projet> expectedProjects = Arrays.asList(projet1, projet2);
        when(projetRepository.findAll()).thenReturn(expectedProjects);
        List<Projet> actualProjects = projetService.getAllProjets();
        assertThat(actualProjects, is(expectedProjects));
        verify(projetRepository, times(1)).findAll();
    }
}
