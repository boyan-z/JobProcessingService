package com.bdz.procservice;

import com.bdz.procservice.converter.TaskToTextConvertor;
import com.bdz.procservice.dto.TaskRequestDTO;
import com.bdz.procservice.dto.TaskResponseDTO;
import com.bdz.procservice.dto.TasksRequestDTO;
import com.bdz.procservice.model.TaskModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TasksIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testTasksSortWithJson() throws Exception {

        var tasks = createTaskRequest();
        var expected = tasks
                .stream()
                .map(t -> TaskResponseDTO.builder()
                        .name(t.getName())
                        .command(t.getCommand())
                        .build())
                .collect(Collectors.toList());

        // Shuffle the request
        Collections.shuffle(tasks);

        var result = mockMvc.perform(post("/tasks")
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TasksRequestDTO.builder().tasks(tasks).build())))
                .andExpect(status().isOk())
                .andReturn();
        var taskResponses = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<TaskResponseDTO>>() {
        });

        assertEquals(expected, taskResponses);
    }

    @Test
    void testTasksSortWithBash() throws Exception {

        var tasks = createTaskRequest();
        var expected = TaskToTextConvertor.convertAll(tasks
                .stream()
                .map(t -> TaskModel.builder()
                        .name(t.getName())
                        .command(t.getCommand())
                        .build())
                .collect(Collectors.toList()));

        // Shuffle the request
        Collections.shuffle(tasks);

        var result = mockMvc.perform(post("/tasks")
                .contentType("application/json")
                .accept(MediaType.TEXT_PLAIN)
                .content(objectMapper.writeValueAsString(TasksRequestDTO.builder().tasks(tasks).build())))
                .andExpect(status().isOk())
                .andReturn();
        var taskResponsesAsText = result.getResponse().getContentAsString();

        assertEquals(expected, taskResponsesAsText);

    }

    private List<TaskRequestDTO> createTaskRequest() {
        var taskList = new ArrayList<TaskRequestDTO>();

        var taskRequestDto1 = TaskRequestDTO.builder()
                .name("name1")
                .command("command1")
                .requires(Collections.emptyList())
                .build();
        taskList.add(taskRequestDto1);

        var taskRequestDto2 = TaskRequestDTO.builder()
                .name("name2")
                .command("command2")
                .requires(List.of("name1"))
                .build();
        taskList.add(taskRequestDto2);

        var taskRequestDto3 = TaskRequestDTO.builder()
                .name("name3")
                .command("command3")
                .requires(List.of("name2", "name1"))
                .build();
        taskList.add(taskRequestDto3);

        var taskRequestDto4 = TaskRequestDTO.builder()
                .name("name4")
                .command("command4")
                .requires(List.of("name3"))
                .build();
        taskList.add(taskRequestDto4);

        return taskList;
    }

}