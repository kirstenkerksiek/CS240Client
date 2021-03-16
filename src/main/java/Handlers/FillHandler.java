package Handlers;

import Requests.FillRequest;
import Results.FillResult;
import Services.FillService;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URI;

public class FillHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();

        try {
            URI uri = exchange.getRequestURI();
            String[] strings = uri.toString().split("/");
            String username = strings[2];
            int generations = 4;
            if(strings.length > 3) {
                generations = Integer.parseInt(strings[3]);
            }
            FillRequest request = new FillRequest(username, generations);
            FillService service = new FillService();
            FillResult result = service.service(request);

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
        catch(IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
