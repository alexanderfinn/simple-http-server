package com.alexanderfinn.httpserver;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpRequest {

  private String method;

  private String path;

  private int contentLength;
  
  Map<String, String> params = new HashMap<String, String>();

  public HttpRequest() {
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public void setPath(String path) {
    this.path = path;
  }
  
  public int getContentLength() {
    return contentLength;
  }

  public void parseHeaderLine(String line) {
    System.out.println(line);
    if (line.startsWith("GET") || line.startsWith("POST")
        || line.startsWith("PUT") || line.startsWith("DELETE")) {
      StringTokenizer tokenizer = new StringTokenizer(line);
      this.setMethod(tokenizer.nextToken());
      ;
      String[] queryString = tokenizer.nextToken().split("\\?");
      this.setPath(queryString[0]);
      if (queryString.length > 1) this.parseBody(queryString[1]);
    } else if (line.startsWith("Content-Length:")) {
      this.setContentLength(Integer.valueOf(line.split(" ")[1]));
    }

  }

  private void setContentLength(Integer contentLength) {
    this.contentLength = contentLength;
  }

  public void parseBody(String body) {
    System.out.println(body);
    if (body.length() > 0) {
      for (String pair: body.split("&")) {
        String[] kv = pair.split("=");
        try {
          this.params.put(kv[0], URLDecoder.decode(kv[1], "UTF-8"));
        } catch (UnsupportedEncodingException e) {
          // Should never happen
        }
      }
    }
  }

  public String getPath() {
    return this.path;
  }

  public String getMethod() {
    return method;
  }
  
  public String getParam(String key) {
    return params.get(key);
  }

}
