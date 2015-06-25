package com.alexanderfinn.httpserver;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
  
  private final static String CRLF = "\r\n";
  
  private final static String SERVER_LINE = "Server: SimpleHttpServer by Alexander Finn";
  
  private final static Map<Integer, String> STATUSES = new HashMap<Integer, String>();
  
  static {
    STATUSES.put(200, "HTTP/1.0 200 OK");
    STATUSES.put(201, "HTTP/1.0 201 Created");
    STATUSES.put(404, "HTTP/1.0 404 Not Found");
    STATUSES.put(405, "HTTP/1.0 405 Method Not Allowed");
    STATUSES.put(500, "HTTP/1.0 500 Error");
  }
  
  private int status = 200;
  
  private String contentType = "text/plain";
  
  private String body = "";
  
  public String serialize() {
    String result = STATUSES.get(this.status) + CRLF;
    result += SERVER_LINE + CRLF;
    result += "Content-type: " + this.contentType + CRLF;
    result += "Content-Length: " + this.body.getBytes().length + CRLF + CRLF;
    result += this.body + CRLF;
    
    return result;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

}
