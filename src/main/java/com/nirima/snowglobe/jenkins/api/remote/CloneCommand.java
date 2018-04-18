package com.nirima.snowglobe.jenkins.api.remote;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

public class CloneCommand extends GlobeCommand {


  final String newName;


  public CloneCommand(String baseUrl, String oldName, String newName) {
    super(baseUrl, oldName);

    this.newName = newName;

  }

  public Void execute()  throws IOException  {
 {

      try(CloseableHttpClient client = getClient()) {

        log.info("Snowglobe clone {} to {}", id, newName);

        String url = getGlobeBaseUrl() + "/clone/" + newName ;

        HttpPost method = new HttpPost(url);


        final HttpResponse response =  client.execute(method, getContext());

      }
      return null;
    }

  }


}
