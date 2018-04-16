package com.nirima.snowglobe.jenkins.api.remote;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

public class RemoveTagCommand extends GlobeCommand<Void> {

  final String tag;

  public RemoveTagCommand(String baseUrl, String id, String tag) {
    super(baseUrl, id);
    this.tag = tag;
  }

  @Override
  public Void execute() throws IOException {
    try(CloseableHttpClient client = getClient()) {

      String url =  getGlobeBaseUrl() + "/tag/" + this.tag;

      HttpDelete method = new HttpDelete(url);
      final HttpResponse response =  client.execute(method, getContext());

    }
    return null;
  }

}
