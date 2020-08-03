package com.bdz.procservice.converter;

import com.bdz.procservice.model.TaskModel;

import java.util.List;

/**
 * Converts TaskModel to bash file text.
 */
public class TaskToTextConvertor {

    private static final char LINE_DELIMITER = '\n';

    /**
     * Converts TaskModel to a command contained in it.
     *
     * @param taskModel to be converted.
     * @return the command.
     */
    public static String convert(final TaskModel taskModel) {
        return taskModel.getCommand();
    }

    /**
     * Converts a list of TaskModels to a text with commands contained in the TaskModels.
     *
     * @param taskModelList to be converted.
     * @return bash style text with commands.
     */
    public static String convertAll(final List<TaskModel> taskModelList) {
        StringBuilder stringBuilder = new StringBuilder();
        taskModelList.forEach(taskModel -> stringBuilder.append(convert(taskModel)).append(LINE_DELIMITER));
        return stringBuilder.toString();
    }
}
