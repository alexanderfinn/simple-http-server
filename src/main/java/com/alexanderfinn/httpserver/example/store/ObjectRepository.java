package com.alexanderfinn.httpserver.example.store;

public interface ObjectRepository<T> {
  
  T get(String key);
  
  void put(String key, T entity);

}
