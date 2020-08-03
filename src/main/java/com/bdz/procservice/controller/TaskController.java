package com.bdz.procservice.controller;

import com.bdz.procservice.converter.TaskToTextConvertor;
import com.bdz.procservice.dto.TaskResponseDTO;
import com.bdz.procservice.dto.TasksRequestDTO;
import com.bdz.procservice.mapper.TaskRequestToTaskModelMapper;
import com.bdz.procservice.model.TaskModel;
import com.bdz.procservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Task REST controller.
 */
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskRequestToTaskModelMapper taskRequestToTaskModelMapper;

    /**
     * Sorts a given tasks list taking in account the dependencies between them.
     *
     * @param tasksRequest list of tasks.
     * @return sorted tasks.
     */
    @PostMapping(path = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<TaskResponseDTO> tasksSort(@RequestBody final TasksRequestDTO tasksRequest) {
        List<TaskModel> sourceTasks = convertRequestToTaskModels(tasksRequest);
        // Do the sort
        var sortedTasks = taskService.sortTasks(sourceTasks);
        // Map to response
        return sortedTasks.stream().map(taskRequestToTaskModelMapper::destinationToSource).collect(Collectors.toList());
    }

    /**
     * Sorts a given tasks list taking in account the dependencies between them and return them formatted as bash file.
     *
     * @param tasksRequest list of tasks.
     * @return text with commands from tasks sorted.
     */
    @PostMapping(path = "/tasks", produces = MediaType.TEXT_PLAIN_VALUE)
    public String tasksSortPlainText(@RequestBody final TasksRequestDTO tasksRequest) {
        // Map requested tasks to TaskModel
        List<TaskModel> sourceTasks = convertRequestToTaskModels(tasksRequest);
        // Do the sort
        var sortedTasks = taskService.sortTasks(sourceTasks);
        // Map to text
        return TaskToTextConvertor.convertAll(sortedTasks);
    }

    private List<TaskModel> convertRequestToTaskModels(@RequestBody TasksRequestDTO tasksRequest) {
        // Map requested tasks to TaskModel
        return tasksRequest.getTasks().stream().map(taskRequestToTaskModelMapper::sourceToDestination).collect(Collectors.toList());
    }
}
