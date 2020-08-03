package com.bdz.procservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Tasks request DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TasksRequestDTO {

    private List<TaskRequestDTO> tasks;
}
