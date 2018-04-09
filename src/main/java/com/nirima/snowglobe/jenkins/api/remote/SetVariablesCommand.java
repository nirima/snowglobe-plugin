package com.nirima.snowglobe.jenkins.api.remote;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

public class SetVariablesCommand extends GlobeCommand<Void> {

  final String vars;

  public SetVariablesCommand(String baseUrl, String id, String vars) {
    super(baseUrl, id);
    this.vars = vars;
  }

  @Override
  public Void execute() throws IOException {
    try(CloseableHttpClient client = getClient()) {

      String url =  getGlobeBaseUrl() + "/vars";

      HttpPut method = new HttpPut(url);
      method.setEntity(new StringEntity(this.vars, "UTF-8"));
      final HttpResponse response =  client.execute(method, getContext());

    }
    return null;
  }

}
