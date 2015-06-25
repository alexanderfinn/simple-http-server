package com.alexanderfinn.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alexanderfinn.httpserver.actions.URLMapping;
import com.alexanderfinn.httpserver.util.ConsoleLogger;

/**
 * 
 * @author Alexander Finn
 * 
 * HTTP Server implementation class
 *
 */
public class HttpServer {
  
  private final ServerSocket serverSocket;
  private final URLMapping mapping;
  private static ConsoleLogger logger;
  
  public HttpServer(int port, URLMapping mapping) throws IOException {
    this.serverSocket = new ServerSocket(port);
    this.mapping = mapping;
    logger.log(Level.FINE, "Initializing HTTP Server at port " + port);
  }

  public void start() {
    logger.log(Level.FINE, "Listening to the incoming connections...");
    while(true) {
      try {
        Socket socket = serverSocket.accept();
        logger.log(Level.FINE, "Incoming connection from " + socket.getInetAddress() + ":" + socket.getPort());
        Thread thread = new Thread(new RequestHandler(socket, mapping));
        thread.start();
      } catch (IOException e) {
        logger.log(Level.SEVERE, "Exception occured: " + e.getMessage());
        break;
      }
    }
  }

}
