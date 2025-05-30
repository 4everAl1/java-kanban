package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import handlers.*;
import program.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {

    private final HttpServer httpServer;
    private final TaskManager taskManager;
    private final Gson gson;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        this.taskManager = taskManager;
        this.gson = new Gson();
    }

    public static void main(String[] args) throws IOException {

    }

    public void start() {
        httpServer.createContext("/tasks", new TasksHandler(taskManager, gson));
        httpServer.createContext("/epics", new EpicsHandler(taskManager, gson));
        httpServer.createContext("/subtasks", new SubtasksHandler(taskManager, gson));
        httpServer.createContext("/history", new HistoryHandler(taskManager, gson));
        httpServer.createContext("/prioritized", new PrioritizedHandler(taskManager, gson));

        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
    }
}
