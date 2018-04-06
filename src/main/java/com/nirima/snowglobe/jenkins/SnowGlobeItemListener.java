package com.nirima.snowglobe.jenkins;

import com.nirima.snowglobe.jenkins.actions.SnowGlobeAction;

import hudson.Extension;
import hudson.model.Actionable;
import hudson.model.Failure;
import hudson.model.Item;
import hudson.model.ItemGroup;
import hudson.model.Run;
import hudson.model.listeners.ItemListener;

@Extension
public class SnowGlobeItemListener extends ItemListener {

  @Override
  public void onDeleted(Item item) {
    super.onDeleted(item);

    if( item instanceof Actionable) {
      SnowGlobeAction action  = ((Actionable)item).getAction(SnowGlobeAction.class);
      if( action != null ) {
        action.onDelete();
      }
    }
  }
}
