package com.example.kanban.Services;

import com.example.kanban.Models.TableauData;
import com.example.kanban.Repositories.TableauDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TableauDataServiceTest {

    @Mock
    private TableauDataRepository tableauDataRepository;

    @InjectMocks
    private TableauDataService tableauDataService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getTableauDataByTableauId() {
        TableauData expectedTableauData = new TableauData();
        expectedTableauData.setId(1L);
        expectedTableauData.setData("{\"key\":\"value\"}");
        when(tableauDataRepository.findByTableauId(1L)).thenReturn(expectedTableauData);

        TableauData actualTableauData = tableauDataService.getTableauDataByTableauId(1L);

        assertThat(actualTableauData, is(expectedTableauData));
        verify(tableauDataRepository, times(1)).findByTableauId(1L);
    }
}
