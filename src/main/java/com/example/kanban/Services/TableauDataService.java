package com.example.kanban.Services;

import com.example.kanban.Models.Tableau;
import com.example.kanban.Models.TableauData;
import com.example.kanban.Repositories.TableauDataRepository;
import com.example.kanban.Repositories.TableauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TableauDataService {

    @Autowired
    private TableauDataRepository tableauDataRepository;
    @Autowired
    private TableauRepository tableauRepository;
    public TableauData saveTableauData(TableauData tableauData) {
        return tableauDataRepository.save(tableauData);
    }

    public TableauData getTableauDataByTableauId(Long tableauId) {
        return tableauDataRepository.findByTableauId(tableauId);
    }
    public TableauData updateOrCreateTableauData(Long tableauId, String jsonData) {
        Tableau tableau = tableauRepository.findById(tableauId).orElse(null);
        if (tableau == null) {
            return null;
        }

        TableauData tableauData = tableauDataRepository.findByTableau(tableau).orElse(null);
        if (tableauData == null) {
            tableauData = new TableauData();
            tableauData.setTableau(tableau);
            tableauData.setData(jsonData);
            tableauDataRepository.save(tableauData);
        } else {
            tableauData.setData(jsonData);
            tableauDataRepository.save(tableauData);
        }
        return tableauData;
    }
}
