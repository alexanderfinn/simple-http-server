package com.alexanderfinn.httpserver.actions;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLMapping {
  
  private final Map<Pattern, Class<? extends Action>> mapping = new HashMap<Pattern, Class<? extends Action>>();
  
  public void addMapping(String pattern, Class<? extends Action> action) {
    Pattern p = Pattern.compile(pattern);
    mapping.put(p, action);
  }
  
  public Action getAction(String path) {
    Action action = null;
    for (Map.Entry<Pattern, Class<? extends Action>> e: mapping.entrySet()) {
      Matcher matcher = e.getKey().matcher(path);
      if (matcher.matches()) {
        try {
          Constructor<? extends Action> constructor = e.getValue().getConstructor();
          action = constructor.newInstance();
        } catch (Exception ex) {
        }
        if (action != null) return action;
      }
    }
    return null;
  }

}
