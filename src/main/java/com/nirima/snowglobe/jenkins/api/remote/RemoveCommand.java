package com.nirima.snowglobe.jenkins.api.remote;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

public class RemoveCommand extends GlobeCommand {

  public RemoveCommand(String baseUrl, String id) {
    super(baseUrl, id);
  }

  @Override
  public Void execute() throws IOException {
    try(CloseableHttpClient client = getClient()) {

      String url = getGlobeBaseUrl();

      HttpDelete method = new HttpDelete(url);

      final HttpResponse response =  client.execute(method, getContext());

    }
    return null;
  }

}
