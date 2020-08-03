package com.bdz.procservice.service;

import com.bdz.procservice.model.TaskModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskServiceTests {

    List<TaskModel> taskModelList;
    List<TaskModel> taskModelListSorted;

    TaskService taskService;

    @BeforeEach
    void setUp() {
        var task1 = TaskModel
                .builder()
                .name("TaskList1")
                .command("TaskCommand1")
                .requires(Collections.emptyList())
                .build();
        var task2 = TaskModel
                .builder()
                .name("TaskList2")
                .command("TaskCommand2")
                .requires(List.of(task1.getName()))
                .build();
        var task3 = TaskModel
                .builder()
                .name("TaskList3")
                .command("TaskCommand3")
                .requires(List.of(task2.getName()))
                .build();
        taskService = new TaskService();
        taskModelList = List.of(task2, task3, task1);
        taskModelListSorted = List.of(task1, task2, task3);
    }

    @Test
    void sortTasksWithTasksExpectSortedTasks() {
        // arrange - done in setup

        // act
        var result = taskService.sortTasks(taskModelList);

        // assert
        assertEquals(taskModelListSorted, result);
    }

    @Test
    void sortTasksWithEmptyListExpectError() {
        // arrange - done in setup

        // act & assert
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.sortTasks(Collections.emptyList());
        });
    }

    @Test
    void sortTasksWithNullExpectError() {
        // arrange - done in setup

        // act & assert
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.sortTasks(null);
        });
    }
}