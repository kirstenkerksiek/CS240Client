package Handlers;

import Requests.LoadRequest;
import Results.LoadResult;
import Services.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Gson gson = new Gson();
        try {
            Reader reader = new InputStreamReader(exchange.getRequestBody());

            LoadRequest request = gson.fromJson(reader, LoadRequest.class);
            LoadService service = new LoadService();
            LoadResult result = service.service(request);

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
