package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import gsonAdapter.DurationAdapter;
import gsonAdapter.LocalDateTimeAdapter;
import handlers.*;
import program.Managers;
import program.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {

    private final HttpServer httpServer;
    private final TaskManager taskManager;
    private final Gson gson;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        this.taskManager = taskManager;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .create();
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        TaskManager taskManager = Managers.getDefault();

        Gson gson1 = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .create();
        HttpTaskServer httpTaskServer = new HttpTaskServer(taskManager);

        httpTaskServer.start();

    }

    public void start() {
        httpServer.createContext("/tasks", new TasksHandler(taskManager, gson));
        httpServer.createContext("/epics", new EpicsHandler(taskManager, gson));
        httpServer.createContext("/subtasks", new SubtasksHandler(taskManager, gson));
        httpServer.createContext("/history", new HistoryHandler(taskManager, gson));
        httpServer.createContext("/prioritized", new PrioritizedHandler(taskManager, gson));

        httpServer.start();
    }

    public Gson getGson() {
        return gson;
    }

    public void stop() {
        httpServer.stop(0);
    }
}
