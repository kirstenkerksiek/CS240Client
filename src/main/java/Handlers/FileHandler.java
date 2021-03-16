package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String filePathStr = "web";
            String relPath = exchange.getRequestURI().getPath();

            if(relPath.equals("/")) {
                relPath = "/index.html";
            }

            String pathStr = filePathStr + relPath;
            Path filePath = FileSystems.getDefault().getPath(pathStr);
            byte[] fileData = Files.readAllBytes(filePath);

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream response = exchange.getResponseBody();
            response.write(fileData);
            response.close();
        }
        catch (IOException e) {
            //open 404.html and write it to response file
            String path404 = "web/HTML/404.html";
            Path file404 = FileSystems.getDefault().getPath(path404);
            byte[] file404Data = Files.readAllBytes(file404);

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);

            OutputStream response404 = exchange.getResponseBody();
            response404.write(file404Data);
            response404.close();

            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
