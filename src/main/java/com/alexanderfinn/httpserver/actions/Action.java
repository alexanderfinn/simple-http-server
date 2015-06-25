package com.alexanderfinn.httpserver.actions;

import com.alexanderfinn.httpserver.HttpRequest;
import com.alexanderfinn.httpserver.HttpResponse;

public interface Action {
  
  HttpResponse process(HttpRequest request);
  
}
