package Handlers;

import Requests.LoginRequest;
import Services.LoginService;
import Results.LoginResult;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Gson gson = new Gson();

        try {
            Reader reader = new InputStreamReader(exchange.getRequestBody());
            LoginRequest request = gson.fromJson(reader, LoginRequest.class);
            LoginService service = new LoginService();
            LoginResult loginResult = service.service(request);

            if(loginResult.isSuccess()) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }

            Writer writer = new OutputStreamWriter(exchange.getResponseBody());
            String jsonStr = gson.toJson(loginResult);
            writer.write(jsonStr);
            writer.close();
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
