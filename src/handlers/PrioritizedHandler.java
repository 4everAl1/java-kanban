package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exceptions.NotFoundException;
import program.TaskManager;

import java.io.IOException;

public class PrioritizedHandler extends BaseHttpHandler{

    public PrioritizedHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    public PrioritizedHandler(Gson gson) {
        super(gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String[] path = exchange.getRequestURI().getPath().split("/");
        String method = exchange.getRequestMethod();
        if (path.length == 2 && method.equals("GET")) {
            sendText(exchange, gson.toJson(taskManager.()), 200);
            return;
        }
        throw new NotFoundException("Not found: " + method + " " + exchange.getRequestURI().getPath());
    }
}
