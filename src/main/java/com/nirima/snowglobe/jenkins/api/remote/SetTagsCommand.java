package com.nirima.snowglobe.jenkins.api.remote;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class SetTagsCommand extends GlobeCommand<Void> {

  final Set<String> tags;

  public SetTagsCommand(String baseUrl, String id, Set<String> tags) {
    super(baseUrl, id);
    this.tags = tags;
  }

  @Override
  public Void execute() throws IOException {
    try(CloseableHttpClient client = getClient()) {

      String url =  getGlobeBaseUrl() + "/tags";

      HttpPut method = new HttpPut(url);


      final ByteArrayOutputStream out = new ByteArrayOutputStream();
      final ObjectMapper mapper = new ObjectMapper();

      mapper.writeValue(out, tags);

      final byte[] data = out.toByteArray();

      method.setEntity(new StringEntity(new String(data), "UTF-8"));
      final HttpResponse response =  client.execute(method, getContext());

    }
    return null;
  }

}
