package com.nirima.snowglobe.jenkins.api.remote;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public abstract class CommandBase<T> {

  private final String baseUrl;

  public CommandBase(String baseUrl) {
   this.baseUrl = baseUrl;
  }

  public abstract T  execute()  throws IOException;

  CloseableHttpClient getClient() {
    HttpClientBuilder clientBuilder = HttpClientBuilder.create().useSystemProperties();
    return clientBuilder.build();
  }

  HttpContext getContext() {
    HttpContext context = new BasicHttpContext();;
    return context;
  }

  String getBaseUrl() {
     String url = this.baseUrl;
     if( url.endsWith("/") )
       url = url.substring(0,url.length()-1);
     return url;
  }

}
