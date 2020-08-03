package com.bdz.procservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Task Model.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskModel {

    private String name;
    private String command;
    private List<String> requires;
}