package com.nirima.snowglobe.jenkins.api.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GlobeCommand<T> extends CommandBase<T> {

  protected static final Logger log = LoggerFactory.getLogger(GlobeCommand.class);

  public final String id;

  public GlobeCommand(String baseUrl, String id) {
    super(baseUrl);
    this.id = id;
  }

  public String getGlobeBaseUrl() {
    return this.getBaseUrl() + "/data/globe/" + id;
  }



}
