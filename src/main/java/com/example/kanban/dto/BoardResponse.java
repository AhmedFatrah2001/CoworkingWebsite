package com.example.kanban.dto;

import lombok.Data;
import java.util.List;

@Data
public class BoardResponse {
    private List<Lane> lanes;
}