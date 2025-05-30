package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exceptions.NotFoundException;
import program.TaskManager;
import tasks.Epic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EpicsHandler extends BaseHttpHandler {

    public EpicsHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    public EpicsHandler(Gson gson) {
        super(gson);
    }

    @Override
    public void handleRequest(HttpExchange exchange, String[] path, String method) throws IOException {
        switch (method) {
            case "GET" -> {
                if (path.length == 2) {
                    sendText(exchange, gson.toJson(taskManager.getAllEpics()), 200);
                } else if (path.length == 3 && isInteger(path[2])) {
                    sendText(exchange, gson.toJson(taskManager.getEpicById(Integer.parseInt(path[2]))), 200);
                } else if (path.length == 4 && isInteger(path[2]) && path[3].equals("subtasks")) {
                    sendText(exchange, gson.toJson(taskManager.getSubtaskByEpic(Integer.parseInt(path[2]))), 200);
                }
            }
            case "POST" -> {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Epic epic = gson.fromJson(body, Epic.class);
                if (path.length == 2) {
                    Epic create = taskManager.addEpic(epic);
                    String json = gson.toJson(create);
                    sendText(exchange, json, 201);
                } else if (path.length == 3 && isInteger(path[2])) {
                    int id = Integer.parseInt(path[2]);
                    epic.setId(id);
                    taskManager.updateEpic(epic);
                    String json = gson.toJson(taskManager.getEpicById(id));
                    sendText(exchange, json, 201);
                }
            }
            case "DELETE" -> {
                if (path.length == 2) {
                    taskManager.removeAllEpic();
                    sendEmptyCodeResponse(exchange, 200);
                } else if (path.length == 3 && isInteger(path[2])) {
                    taskManager.removeEpicById(Integer.parseInt(path[2]));
                    sendEmptyCodeResponse(exchange, 200);
                }
            }
            default -> throw new NotFoundException("Not found: " + method + " " + exchange.getRequestURI().getPath());
        }
    }
}
