package com.nirima.snowglobe.jenkins;

import com.nirima.snowglobe.jenkins.actions.SnowGlobeAction;
import com.nirima.snowglobe.jenkins.api.remote.DestroyCommand;
import com.nirima.snowglobe.jenkins.api.remote.RemoveCommand;

import java.io.IOException;

import hudson.Extension;
import hudson.model.Run;
import hudson.model.listeners.RunListener;

@Extension
public class SnowGlobeListener extends RunListener<Run<?,?>> {

  @Override
  public void onDeleted(Run<?,?> run) {
    super.onDeleted(run);
    SnowGlobeAction action  = run.getAction(SnowGlobeAction.class);
    if( action != null ) {
      if (action.destroyOnDelete() ) {
        String baseUrl = SnowGlobePluginConfiguration.get().getServerUrl();

        try {
          new DestroyCommand(baseUrl, action.getId()).execute();
          new RemoveCommand(baseUrl, action.getId()).execute();
        } catch (IOException e) {
          e.printStackTrace();
        }


      }
    }
  }
}
