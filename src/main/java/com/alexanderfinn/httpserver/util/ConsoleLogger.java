package com.alexanderfinn.httpserver.util;

import java.util.logging.Level;

public class ConsoleLogger {
  
  public static void log(Level level, String msg) {
    System.out.println(level.getName() + ": " + msg);
  }

}
