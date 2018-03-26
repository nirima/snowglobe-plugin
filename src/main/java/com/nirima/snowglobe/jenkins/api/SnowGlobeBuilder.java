package com.nirima.snowglobe.jenkins.api;

public class SnowGlobeBuilder {

  private SnowGlobeBuilder(String url) {

  }

  public static SnowGlobeBuilder forRemoteServer(String url) {
    return new SnowGlobeBuilder(url);
  }

  public CloneCommandBuilder cloneCommand() {
    return new CloneCommandBuilder(this);
  }

}
