package com.nirima.snowglobe.jenkins.api.remote;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

public class ApplyCommand extends GlobeCommand<Void> {

  public ApplyCommand(String baseUrl, String id) {
    super(baseUrl, id);
  }

  @Override
  public Void execute() throws IOException {
    try(CloseableHttpClient client = getClient()) {

      log.info("Snowglobe apply for id {}", id);

      String url =  getGlobeBaseUrl() + "/apply";

      HttpPut method = new HttpPut(url);
      final HttpResponse response =  client.execute(method, getContext());

    }
    return null;
  }

}
