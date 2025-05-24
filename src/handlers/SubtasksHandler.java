package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import program.TaskManager;

import java.io.IOException;

public class SubtasksHandler extends BaseHttpHandler {

    public SubtasksHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    public SubtasksHandler(Gson gson) {
        super(gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
