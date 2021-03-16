package Handlers;

import Requests.RegisterRequest;
import Results.RegisterResult;
import Services.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();

        try {
            Reader reader = new InputStreamReader(exchange.getRequestBody());
            RegisterRequest request = gson.fromJson(reader, RegisterRequest.class);
            RegisterService service = new RegisterService();
            RegisterResult result = service.service(request);

            if(result.isSuccess()) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }

            Writer writer = new OutputStreamWriter(exchange.getResponseBody());
            String json = gson.toJson(result);
            writer.write(json);
            writer.close();
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
