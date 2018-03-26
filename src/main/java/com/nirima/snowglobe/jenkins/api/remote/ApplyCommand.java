package com.nirima.snowglobe.jenkins.api.remote;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

public class ApplyCommand extends GlobeCommand<Void> {

  final String body;
  public ApplyCommand(String baseUrl, String id, String body) {
    super(baseUrl, id);
    this.body = body;
  }

  @Override
  public Void execute() throws IOException {
    try(CloseableHttpClient client = getClient()) {

      String url =  getGlobeBaseUrl() + "/apply";

      HttpPost method = new HttpPost(url);
      method.setEntity(new StringEntity(this.body, "text/plain"));
      final HttpResponse response =  client.execute(method, getContext());

    }
    return null;
  }

}
