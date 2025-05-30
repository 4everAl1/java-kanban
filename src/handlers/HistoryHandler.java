package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exceptions.NotFoundException;
import program.TaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler {

    public HistoryHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    public HistoryHandler(Gson gson) {
        super(gson);
    }

    @Override
    public void handleRequest(HttpExchange exchange, String[] path, String method) throws IOException {
        if (path.length == 2 && method.equals("GET")) {
            sendText(exchange, gson.toJson(taskManager.getHistory()), 200);
            return;
        }
        throw new NotFoundException("Not found: " + method + " " + exchange.getRequestURI().getPath());
    }
}
