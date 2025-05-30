package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exceptions.NotFoundException;
import program.TaskManager;
import tasks.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TasksHandler extends BaseHttpHandler {

    public TasksHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    public TasksHandler(Gson gson) {
        super(gson);
    }

    @Override
    public void handleRequest(HttpExchange exchange, String[] path, String method) throws IOException {
        switch (method) {
            case "GET" -> {
                if (path.length == 2) {
                    sendText(exchange, gson.toJson(taskManager.getAllTasks()), 200);
                } else if (path.length == 3 && isInteger(path[2])) {
                    sendText(exchange, gson.toJson(taskManager.getTaskByID(Integer.parseInt(path[2]))), 200);
                }
            }
            case "POST" -> {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Task task = gson.fromJson(body, Task.class);
                if (path.length == 2) {
                    Task create = taskManager.addTask(task);
                    String json = gson.toJson(create);
                    sendText(exchange, json, 201);
                } else if (path.length == 3 && isInteger(path[2])) {
                    int id = Integer.parseInt(path[2]);
                    task.setId(id);
                    taskManager.updateTask(task);
                    String json = gson.toJson(taskManager.getTaskByID(id));
                    sendText(exchange, json, 201);
                }
            }
            case "DELETE" -> {
                if (path.length == 2) {
                    taskManager.removeAllTask();
                    sendEmptyCodeResponse(exchange, 200);
                } else if (path.length == 3 && isInteger(path[2])) {
                    taskManager.removeTaskById(Integer.parseInt(path[2]));
                    sendEmptyCodeResponse(exchange, 200);
                }
            }
            default -> throw new NotFoundException("Not found: " + method + " " + exchange.getRequestURI().getPath());
        }
    }
}
