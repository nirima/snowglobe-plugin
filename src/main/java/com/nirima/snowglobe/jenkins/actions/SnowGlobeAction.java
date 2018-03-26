package com.nirima.snowglobe.jenkins.actions;


import com.nirima.snowglobe.jenkins.Consts;

import java.io.Serializable;
import java.util.logging.Logger;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Run;
import hudson.model.listeners.RunListener;
import jenkins.model.Jenkins;
/**
 * SnowGlobes linked to a job (action badge)
 */
public class SnowGlobeAction implements Action, Serializable,
                                                                 Describable<SnowGlobeAction> {

  private static final Logger LOGGER = Logger.getLogger(SnowGlobeAction.class.getName());

  private String id;

  public SnowGlobeAction(String runId) {
    this.id = runId;
  }

  public String getId() {
    return id;
  }

  public boolean destroyOnDelete() {
    return true;
  }

  public static class ActionData {
    public String id;
    public ActionData(String id) {
      this.id = id;

    }

    
    public String getId() {
      return id;
    }


  }

  @Override
  public String getIconFileName() {
    return  "/plugin/snowglobe-plugin/images/32x32/snow-globe.png";
  }

  @Override
  public String getDisplayName() {
    return "Snowglobe";
  }

  @Override
  public String getUrlName() {
    return "snowglobeAction";
  }

  public DescriptorImpl getDescriptor() {
    return (DescriptorImpl) Jenkins.getInstance().getDescriptorOrDie(getClass());
  }


  public String getJsUrl(String jsName) {
    return Consts.PLUGIN_JS_URL + jsName;
  }

  


  /**
   * Just for assisting form related stuff.
   */
  @Extension
  public static class DescriptorImpl extends Descriptor<SnowGlobeAction> {
    public String getDisplayName() {
      return "SnowGlobe";
    }
  }
}
