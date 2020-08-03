package com.bdz.procservice.service;

import com.bdz.procservice.graph.TopologicalSort;
import com.bdz.procservice.model.TaskModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for operations over tasks.
 */
@Service
public class TaskService {

    /**
     * Sorts tasks objects topologically.
     *
     * @param tasks - list of tasks to be sorted.
     * @return list of the tasks sorted.
     * @see TopologicalSort
     */
    public List<TaskModel> sortTasks(final List<TaskModel> tasks) {
        // Do topological sort over given tasks
        return TopologicalSort.topologicalSort(tasks, TaskModel::getName, TaskModel::getRequires);
    }
}
