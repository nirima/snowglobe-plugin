package com.nirima.snowglobe.jenkins.api.remote;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

public class DestroyCommand extends GlobeCommand {

  public DestroyCommand(String baseUrl, String id) {
    super(baseUrl, id);
  }

  @Override
  public Void execute() throws IOException {
    try(CloseableHttpClient client = getClient()) {

      log.info("Destroy globe {}", id);

      String url = getGlobeBaseUrl() + "/destroy";

      HttpPost method = new HttpPost(url);

      final HttpResponse response =  client.execute(method, getContext());

    }
    return null;
  }

}
