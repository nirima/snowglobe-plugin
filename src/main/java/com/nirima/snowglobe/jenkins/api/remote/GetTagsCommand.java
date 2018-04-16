package com.nirima.snowglobe.jenkins.api.remote;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class GetTagsCommand extends GlobeCommand<Set<String>> {

  public GetTagsCommand(String baseUrl, String id) {
    super(baseUrl, id);
  }

  @Override
  public Set<String> execute() throws IOException {
    try(CloseableHttpClient client = getClient()) {

      String url =  getGlobeBaseUrl() + "/tags";

      HttpGet method = new HttpGet(url);

      final HttpResponse response =  client.execute(method, getContext());

      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(response.getEntity().getContent(), new TypeReference<List<String>>(){});
    }
  }

}
