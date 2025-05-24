package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import program.Managers;
import program.TaskManager;

import java.io.IOException;

public class EpicsHandler extends BaseHttpHandler{

    public EpicsHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    public EpicsHandler(Gson gson) {
        super(gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
