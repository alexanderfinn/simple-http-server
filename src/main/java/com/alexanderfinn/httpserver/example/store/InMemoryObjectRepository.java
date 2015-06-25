package com.alexanderfinn.httpserver.example.store;

import java.util.HashMap;
import java.util.Map;

public class InMemoryObjectRepository<T> implements ObjectRepository<T> {
  
  Map<String, T> store = new HashMap<String, T>();

  public T get(String key) {
    return store.get(key);
  }

  public void put(String key, T entity) {
    store.put(key, entity);
  }

}
