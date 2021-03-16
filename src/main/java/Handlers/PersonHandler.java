package Handlers;

import Model.AuthToken;
import Requests.AllPersonRequest;
import Requests.PersonRequest;
import Results.AllPersonResult;
import Results.PersonResult;
import Services.AllPersonService;
import Services.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URI;

public class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();

        try {
            Headers headers = exchange.getRequestHeaders();
            AuthToken token = new AuthToken();
            token.setAuthID(headers.getFirst("Authorization"));
            URI uri = exchange.getRequestURI();
            String[] strings = uri.toString().split("/");

            if(strings.length > 2) {
                String id = strings[2];
                PersonRequest request = new PersonRequest(token, id);
                PersonService service = new PersonService();
                PersonResult result = service.service(request);

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
                AllPersonRequest request = new AllPersonRequest(token);
                AllPersonService service = new AllPersonService();
                AllPersonResult result = service.service(request);

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
