package org.example;

import org.example.config.Configuration;
import org.example.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static void main(String[] args) {
        System.out.println("Server ...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();
        System.out.println("Using Port: " + configuration.getPort());
        System.out.println("Using WebRoot: " + configuration.getWebroot());
        try {
            ServerSocket serverSocket = new ServerSocket(configuration.getPort());
            Socket sockert = serverSocket.accept();
            InputStream inputStream = sockert.getInputStream();
            OutputStream outputStream = sockert.getOutputStream();
            String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>Java Server Demo</h1></body></html>";
            //TODO: read/write
            final String CRLF = "\n\r";
            String response = "HTTP/1.1 200 OK" + CRLF +
                    "Content-Length: " + html.getBytes().length + CRLF +
                    CRLF +
                    html +
                    CRLF + CRLF;
            outputStream.write(response.getBytes());
            inputStream.close();
            outputStream.close();;
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}