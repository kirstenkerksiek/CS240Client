package Handlers;

import Model.AuthToken;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URI;

import Requests.EventRequest;
import Requests.AllEventsRequest;
import Results.EventResult;
import Results.AllEventsResult;
import Services.EventService;
import Services.AllEventsService;

public class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();

        try
        {
            Headers headers = exchange.getRequestHeaders();
            AuthToken token = new AuthToken();
            token.setAuthID(headers.getFirst("Authorization"));

            URI uri = exchange.getRequestURI();
            String[] strings = uri.toString().split("/");

            if(strings.length > 2) {
                String eventID = strings[2];
                EventRequest request = new EventRequest(token, eventID);
                EventService service = new EventService();
                EventResult result = service.service(request);

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
            else {
                AllEventsRequest request = new AllEventsRequest(token);
                AllEventsService service = new AllEventsService();
                AllEventsResult result = service.service(request);

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
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
