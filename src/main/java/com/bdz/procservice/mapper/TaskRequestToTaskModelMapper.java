package com.bdz.procservice.mapper;

import com.bdz.procservice.dto.TaskRequestDTO;
import com.bdz.procservice.dto.TaskResponseDTO;
import com.bdz.procservice.model.TaskModel;
import org.mapstruct.Mapper;

/**
 *
 */
@Mapper(componentModel = "spring")
public interface TaskRequestToTaskModelMapper {

    TaskModel sourceToDestination(TaskRequestDTO source);

    TaskResponseDTO destinationToSource(TaskModel destination);
}