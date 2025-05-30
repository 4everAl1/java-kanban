package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exceptions.NotFoundException;
import program.TaskManager;
import tasks.Subtask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SubtasksHandler extends BaseHttpHandler {

    public SubtasksHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    public SubtasksHandler(Gson gson) {
        super(gson);
    }

    @Override
    public void handleRequest(HttpExchange exchange, String[] path, String method) throws IOException {
        switch (method) {
            case "GET" -> {
                if (path.length == 2) {
                    sendText(exchange, gson.toJson(taskManager.getAllSubtask()), 200);
                } else if (path.length == 3 && isInteger(path[2])) {
                    sendText(exchange, gson.toJson(taskManager.getSubtaskByID(Integer.parseInt(path[2]))), 200);
                }
            }
            case "POST" -> {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Subtask subtask = gson.fromJson(body, Subtask.class);
                if (subtask.getId() == 0) {
                    Subtask create = taskManager.addSubtask(subtask, subtask.getEpicId());
                    String json = gson.toJson(create);
                    sendText(exchange, json, 201);
                } else {
                    taskManager.updateSubtask(subtask);
                    String json = gson.toJson(taskManager.getSubtaskByID(subtask.getId()));
                    sendText(exchange, json, 201);
                }
            }
            case "DELETE" -> {
                if (path.length == 2) {
                    taskManager.removeAllSubtask();
                    sendEmptyCodeResponse(exchange, 200);
                } else if (path.length == 3 && isInteger(path[2])) {
                    taskManager.removeSubtaskById(Integer.parseInt(path[2]));
                    sendEmptyCodeResponse(exchange, 200);
                }
            }
            default -> throw new NotFoundException("Not found: " + method + " " + exchange.getRequestURI().getPath());
        }
    }
}
