package com.alexanderfinn.httpserver;

import java.io.IOException;

import com.alexanderfinn.httpserver.actions.Action;
import com.alexanderfinn.httpserver.actions.URLMapping;
import com.alexanderfinn.httpserver.example.User;
import com.alexanderfinn.httpserver.example.UserAction;
import com.alexanderfinn.httpserver.example.store.InMemoryObjectRepository;
import com.alexanderfinn.httpserver.example.store.ObjectRepository;
import com.alexanderfinn.httpserver.example.store.RepositoryFactory;

/**
 * @author Alexander Finn
 * 
 * Main class that launches the server
 *
 */
public class App {
  
  private static final int DEFAULT_PORT = 8000;

  public static void main(String[] args) {
    
    int port = DEFAULT_PORT;
    
    if (args.length > 0) {
      port = Integer.valueOf(args[0]);
    }
    HttpServer server;
    
    initializeStore();
    try {
      URLMapping mapping = new URLMapping();
      mapping.addMapping("\\/user\\/(\\d+)", UserAction.class);
      server = new HttpServer(port, mapping);
      server.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void initializeStore() {
    ObjectRepository<User> repository = new InMemoryObjectRepository<User>();
    RepositoryFactory.getInstance().setObjectRepository(User.class, repository);
    repository.put("1", new User("1", "Test1 Name", "test1@test.com"));
    repository.put("2", new User("2", "Test2 Name", "test2@test.com"));
    repository.put("3", new User("3", "Test3 Name", "test3@test.com"));
    
  }

}
