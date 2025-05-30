package handlers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import exceptions.InvalidTimeException;
import exceptions.NotFoundException;
import program.Managers;
import program.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class BaseHttpHandler implements HttpHandler {

    TaskManager taskManager;
    Gson gson;

    public BaseHttpHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    public BaseHttpHandler(Gson gson) {
        this.taskManager = Managers.getDefault();
        this.gson = gson;
    }

    public static void sendError(HttpExchange exchange, String textError, int code) throws IOException {
        JsonObject errorObj = new JsonObject();
        errorObj.addProperty("message", textError);
        sendText(exchange, errorObj.toString(), code);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String[] path = exchange.getRequestURI().getPath().split("/");
            String method = exchange.getRequestMethod();
            handleRequest(exchange, path, method);
        } catch (NotFoundException e) {
            sendError(exchange, e.getMessage(), 404);
        } catch (InvalidTimeException e) {
            sendError(exchange, e.getMessage(), 406);
        } catch (Exception e) {
            sendError(exchange, e.getMessage(), 500);
        }
    }

    protected abstract void handleRequest(HttpExchange exchange, String[] path, String method) throws IOException;

    protected static void sendText(HttpExchange h, String text, int code) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(code, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendEmptyCodeResponse(HttpExchange httpExchange, int code) throws IOException {
        httpExchange.sendResponseHeaders(code, 0);
        httpExchange.close();
    }

    protected static boolean isInteger(String s) {
        if (s == null || s.isEmpty()) return false;
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
