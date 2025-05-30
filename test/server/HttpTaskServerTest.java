package server;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import program.Managers;
import program.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskServerTest {
    private static TaskManager taskManager;
    private static HttpTaskServer server;
    private static Gson gson;
    private static HttpClient client;
    HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

    @BeforeAll
    static void beforeAll() throws IOException {
        taskManager = Managers.getDefault();
        server = new HttpTaskServer(taskManager);
        gson = server.getGson();
        server.start();
        client = HttpClient.newHttpClient();
    }

    @AfterAll
    static void afterAll() {
        server.stop();
        client.close();
    }

    @BeforeEach
    void beforeEach() {
        taskManager.removeAllTask();
        taskManager.removeAllSubtask();
        taskManager.removeAllEpic();
    }

    @Test
    void testAddTask() throws IOException, InterruptedException {

        Task task = new Task("Test 2", "Testing task 2", LocalDateTime.now(), Duration.ofMinutes(5));
        String taskJson = gson.toJson(task);

        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build();


        HttpResponse<String> response = client.send(request, handler);
        assertEquals(201, response.statusCode());

        List<Task> tasksFromManager = taskManager.getAllTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Test 2", tasksFromManager.get(0).getTitle(), "Некорректное имя задачи");
    }

    @Test
    void testGetTasks() throws IOException, InterruptedException {

        Task task = new Task("Test 2", "Testing task 2", LocalDateTime.now(), Duration.ofMinutes(5));
        Task addTask = taskManager.addTask(task);
        String taskJson = gson.toJson(addTask);

        URI url = URI.create("http://localhost:8080/tasks");

        HttpRequest getRequest = HttpRequest
                .newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, handler);
        String body = getResponse.body();
        assertEquals(200, getResponse.statusCode());
        assertTrue(body.contains(taskJson));
    }

    @Test
    void testGetTaskById() throws IOException, InterruptedException {
        Task task = new Task("Test 2", "Testing task 2", LocalDateTime.now(), Duration.ofMinutes(5));
        Task addTask = taskManager.addTask(task);
        String taskJson = gson.toJson(addTask);

        URI url = URI.create("http://localhost:8080/tasks/" + addTask.getId());

        HttpRequest getRequest = HttpRequest
                .newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, handler);
        String body = getResponse.body();
        assertEquals(200, getResponse.statusCode());
        assertTrue(body.contains(taskJson));
    }

    @Test
    void returnEmptyListAfterCleanTasks() throws IOException, InterruptedException {
        Task task = new Task("Test 2", "Testing task 2", LocalDateTime.now(), Duration.ofMinutes(5));
        Task addTask = taskManager.addTask(task);

        URI url = URI.create("http://localhost:8080/tasks");

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());

        HttpRequest getRequest = HttpRequest
                .newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, handler);
        String body = getResponse.body();

        assertEquals("[]", body);
    }

    @Test
    void testAddEpic() throws IOException, InterruptedException {

        Epic epic = new Epic("Test 2", "Testing task 2");
        String epicJson = gson.toJson(epic);

        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(epicJson))
                .build();


        HttpResponse<String> response = client.send(request, handler);
        assertEquals(201, response.statusCode());

        List<Epic> epicFromManager = taskManager.getAllEpics();

        assertNotNull(epicFromManager, "Задачи не возвращаются");
        assertEquals(1, epicFromManager.size(), "Некорректное количество задач");
        assertEquals("Test 2", epicFromManager.get(0).getTitle(), "Некорректное имя задачи");
    }

    @Test
    void testGetEpics() throws IOException, InterruptedException {

        Epic epic = new Epic("Test 2", "Testing task 2");
        Epic addEpic = taskManager.addEpic(epic);
        String epicJson = gson.toJson(addEpic);

        URI url = URI.create("http://localhost:8080/epics");

        HttpRequest getRequest = HttpRequest
                .newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, handler);
        String body = getResponse.body();
        assertEquals(200, getResponse.statusCode());
        assertTrue(body.contains(epicJson));
    }

    @Test
    void testGetEpicById() throws IOException, InterruptedException {
        Epic epic = new Epic("Test 2", "Testing task 2");
        Epic addEpic = taskManager.addEpic(epic);
        String epicJson = gson.toJson(addEpic);

        URI url = URI.create("http://localhost:8080/epics/" + addEpic.getId());

        HttpRequest getRequest = HttpRequest
                .newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, handler);
        String body = getResponse.body();
        assertEquals(200, getResponse.statusCode());
        assertTrue(body.contains(epicJson));
    }

    @Test
    void returnEmptyListAfterCleanEpics() throws IOException, InterruptedException {
        Epic epic = new Epic("Test 2", "Testing task 2");
        Epic addEpic = taskManager.addEpic(epic);

        URI url = URI.create("http://localhost:8080/epics");

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());

        HttpRequest getRequest = HttpRequest
                .newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, handler);
        String body = getResponse.body();

        assertEquals("[]", body);
    }

    @Test
    void testAddSubtask() throws IOException, InterruptedException {

        Epic epic = new Epic("Test 2", "Testing task 2");
        Epic addEpic = taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Test",
                "Testing task",
                LocalDateTime.now(),
                Duration.ofMinutes(5),
                addEpic.getId());
        String subtaskJson = gson.toJson(subtask);

        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(201, response.statusCode());

        List<Subtask> subtasksFromManager = taskManager.getAllSubtask();

        assertNotNull(subtasksFromManager, "Задачи не возвращаются");
        assertEquals(1, subtasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Test", subtasksFromManager.get(0).getTitle(), "Некорректное имя задачи");
    }

    @Test
    void testGetSubtasks() throws IOException, InterruptedException {
        Epic epic = new Epic("Test 2", "Testing task 2");
        Epic addEpic = taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Test",
                "Testing task",
                LocalDateTime.now(),
                Duration.ofMinutes(5),
                addEpic.getId());
        Subtask addSubtask = taskManager.addSubtask(subtask, subtask.getEpicId());
        String subtaskJason = gson.toJson(addSubtask);

        URI url = URI.create("http://localhost:8080/subtasks");

        HttpRequest getRequest = HttpRequest
                .newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, handler);
        String body = getResponse.body();
        assertEquals(200, getResponse.statusCode());
        assertTrue(body.contains(subtaskJason));
    }

    @Test
    void testGetSubtaskById() throws IOException, InterruptedException {
        Epic epic = new Epic("Test 2", "Testing task 2");
        Epic addEpic = taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Test",
                "Testing task",
                LocalDateTime.now(),
                Duration.ofMinutes(5),
                addEpic.getId());
        Subtask addSubtask = taskManager.addSubtask(subtask, subtask.getEpicId());
        String subtaskJason = gson.toJson(addSubtask);

        URI url = URI.create("http://localhost:8080/subtasks/" + addSubtask.getId());

        HttpRequest getRequest = HttpRequest
                .newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, handler);
        String body = getResponse.body();
        assertEquals(200, getResponse.statusCode());
        assertTrue(body.contains(subtaskJason));
    }

    @Test
    void returnEmptyListAfterCleanSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic("Test 2", "Testing task 2");
        Epic addEpic = taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Test",
                "Testing task",
                LocalDateTime.now(),
                Duration.ofMinutes(5),
                addEpic.getId());
        Subtask addSubtask = taskManager.addSubtask(subtask, subtask.getEpicId());

        URI url = URI.create("http://localhost:8080/subtasks");

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());

        HttpRequest getRequest = HttpRequest
                .newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, handler);
        String body = getResponse.body();

        assertEquals("[]", body);
    }
}
