package com.alexanderfinn.httpserver.example;

import java.util.List;

import com.alexanderfinn.httpserver.HttpRequest;
import com.alexanderfinn.httpserver.HttpResponse;
import com.alexanderfinn.httpserver.actions.Action;
import com.alexanderfinn.httpserver.example.store.ObjectRepository;
import com.alexanderfinn.httpserver.example.store.RepositoryFactory;

public class UserAction implements Action {
  
  public HttpResponse process(HttpRequest request) {
    ObjectRepository<User> repository = RepositoryFactory.getInstance().getObjectRepository(User.class);
    HttpResponse response = new HttpResponse();
    String userId = request.getPath().substring(request.getPath().lastIndexOf("/")+1, request.getPath().length());
    User user = repository.get(userId);
    response.setContentType("application/json");
    if (user == null) {
      response.setStatus(404);
      response.setBody("{'errorCode': 404, 'reason': 'User does not exist'}");
      return response;
    }
    if (request.getMethod().equals("PUT")) {
      user.setName(request.getParam("name"));
      user.setEmail(request.getParam("email"));;
      repository.put(userId, user);
      response.setStatus(201);
    }
    response.setBody("{'id': '" + user.getId() + "', 'name': '" + user.getName() + "', 'email': '" + user.getEmail() + "'}");
    response.setStatus(200);
    return response;
  }

}
