package com.nirima.snowglobe.jenkins.api.remote;

public abstract class GlobeCommand<T> extends CommandBase<T> {

  public final String id;

  public GlobeCommand(String baseUrl, String id) {
    super(baseUrl);
    this.id = id;
  }

  public String getGlobeBaseUrl() {
    return this.getBaseUrl() + "/data/globe/" + id;
  }



}
