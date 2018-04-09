package com.nirima.snowglobe.jenkins.api.remote;

import org.apache.commons.fileupload.util.Streams;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

public class GetVariablesCommand extends GlobeCommand<String> {

  public GetVariablesCommand(String baseUrl, String id) {
    super(baseUrl, id);
  }

  @Override
  public String execute() throws IOException {
    try(CloseableHttpClient client = getClient()) {

      String url =  getGlobeBaseUrl() + "/vars";

      HttpGet method = new HttpGet(url);

      final HttpResponse response =  client.execute(method, getContext());
      return Streams.asString(response.getEntity().getContent() );

    }
  }

}
