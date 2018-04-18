package com.nirima.snowglobe.jenkins.api.remote;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

public class AddTagCommand extends GlobeCommand<Void> {

  final String tag;

  public AddTagCommand(String baseUrl, String id, String tag) {
    super(baseUrl, id);
    this.tag = tag;
  }

  @Override
  public Void execute() throws IOException {
    try(CloseableHttpClient client = getClient()) {

      log.info("Add Tag {} to Globe {}", tag, id);

      String url =  getGlobeBaseUrl() + "/tag/" + this.tag;

      HttpPut method = new HttpPut(url);
      final HttpResponse response =  client.execute(method, getContext());

    }
    return null;
  }

}
