package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exceptions.NotFoundException;
import program.TaskManager;

import java.io.IOException;

public class PrioritizedHandler extends BaseHttpHandler {

    public PrioritizedHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    public PrioritizedHandler(Gson gson) {
        super(gson);
    }

    @Override
    public void handleRequest(HttpExchange exchange, String[] path, String method) throws IOException {
        if (path.length == 2 && method.equals("GET")) {
            sendText(exchange, gson.toJson(taskManager.getPrioritizedTasks()), 200);
            return;
        }
        throw new NotFoundException("Not found: " + method + " " + exchange.getRequestURI().getPath());
    }
}
