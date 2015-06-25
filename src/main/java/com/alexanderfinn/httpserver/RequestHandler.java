package com.alexanderfinn.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;

import com.alexanderfinn.httpserver.actions.Action;
import com.alexanderfinn.httpserver.actions.URLMapping;
import com.alexanderfinn.httpserver.util.ConsoleLogger;

public class RequestHandler implements Runnable {

  final static String CRLF = "\r\n";

  private ConsoleLogger logger;

  private final Socket socket;
  private final URLMapping mapping;
  private final BufferedReader inReader;

  public RequestHandler(Socket socket, URLMapping mapping) throws IOException {
    this.socket = socket;
    this.mapping = mapping;
    inReader = new BufferedReader(
        new InputStreamReader(socket.getInputStream()));
  }

  public void run() {
    try {
      processRequest();
      this.socket.getOutputStream().close();
      this.inReader.close();
      this.socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void processRequest() throws IOException {
    HttpRequest request = parseRequest();
    Action action = mapping.getAction(request.getPath());
    HttpResponse response;
    if (action != null) {
      try {
        response = action.process(request);
      } catch(Exception e) {
        response = getErrorResponse();
        logger.log(Level.SEVERE, "Error during request processing: " + e.getMessage());
        e.printStackTrace();
      }
    } else {
      response = getNotFoundResponse();
    }
    socket.getOutputStream().write(response.serialize().getBytes());
    logger.log(Level.FINE, "Request proceesing completed, status: " + response.getStatus());
  }

  private HttpResponse getErrorResponse() {
    HttpResponse response = new HttpResponse();
    response.setStatus(500);
    response.setBody("Internal error");
    return response;
  }

  private HttpResponse getNotFoundResponse() {
    HttpResponse response = new HttpResponse();
    response.setStatus(404);
    response.setBody("Not found");
    return response;
  }

  private HttpRequest parseRequest() throws IOException {
    // This assumes we do not handle file uploads or any large requests
    HttpRequest request = new HttpRequest();
    String line;

    while ((line = inReader.readLine()) != null) {
      if (line.equals(""))
        break;
      request.parseHeaderLine(line);
    }

    int length = request.getContentLength();
    StringBuilder body = new StringBuilder();
    if (length > 0) {
      int read;
      while ((read = inReader.read()) != -1) {
        body.append((char) read);
        if (body.length() == length)
          break;
      }
    }
    
    request.parseBody(body.toString());
    return request;
  }

}
