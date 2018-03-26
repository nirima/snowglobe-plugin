package com.nirima.snowglobe.jenkins;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.Functions;
import hudson.Util;
import jenkins.model.GlobalConfiguration;

@Extension
public class SnowGlobePluginConfiguration extends GlobalConfiguration {

  private String serverUrl;


  public SnowGlobePluginConfiguration() {
    load();
  }

  @Override
  public boolean configure(StaplerRequest req, JSONObject json)
      throws FormException
  {
    req.bindJSON(this, json);
    save();
    return true;
  }

  /**
   * Returns this singleton instance.
   *
   * @return the singleton.
   */
  public static SnowGlobePluginConfiguration get() {
    return GlobalConfiguration.all().get(SnowGlobePluginConfiguration.class);
  }

  public String getServerUrl() {
    return serverUrl;
  }

  public void setServerUrl(String serverUrl) {
    this.serverUrl = serverUrl;
  }
}
