package com.example.kanban.Services;

import com.example.kanban.Models.Utilisateur;
import com.example.kanban.Repositories.UtilisateurRepository;
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

public class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private UtilisateurService utilisateurService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllUtilisateurs_ShouldReturnAllUsers() {
        Utilisateur utilisateur1 = new Utilisateur();
        utilisateur1.setId(1L);
        utilisateur1.setEmail("user1@example.com");
        Utilisateur utilisateur2 = new Utilisateur();
        utilisateur2.setId(2L);
        utilisateur2.setEmail("user2@example.com");

        List<Utilisateur> expectedUtilisateurs = Arrays.asList(utilisateur1, utilisateur2);
        when(utilisateurRepository.findAll()).thenReturn(expectedUtilisateurs);

        List<Utilisateur> actualUtilisateurs = utilisateurService.getAllUtilisateurs();

        assertThat(actualUtilisateurs, is(expectedUtilisateurs));
        verify(utilisateurRepository, times(1)).findAll();
    }
}
