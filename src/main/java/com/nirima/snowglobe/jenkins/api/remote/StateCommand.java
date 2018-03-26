package com.nirima.snowglobe.jenkins.api.remote;

import org.apache.commons.fileupload.util.Streams;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

public class StateCommand extends GlobeCommand<String> {

  public StateCommand(String baseUrl, String id) {
    super(baseUrl, id);
  }

  @Override
  public String execute() throws IOException {
    try(CloseableHttpClient client = getClient()) {

      String url = getGlobeBaseUrl() + "/state";

      HttpGet method = new HttpGet(url);
      method.setHeader("accept","application/json");
      
      final HttpResponse response =  client.execute(method, getContext());
      return Streams.asString( response.getEntity().getContent() );
    }
  }

}
