package com.nirima.snowglobe.jenkins.api.remote;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.List;

public class ListCommand extends CommandBase<List<Globe>> {

  public ListCommand(String baseUrl) {
    super(baseUrl);
  }

  @Override
  public List<Globe> execute() throws IOException {
    try(CloseableHttpClient client = getClient()) {

      String url = this.getBaseUrl() + "/data/globes";

      HttpGet method = new HttpGet(url);


      final HttpResponse response =  client.execute(method, getContext());

      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Globe>>(){});
    }
    
  }
}
