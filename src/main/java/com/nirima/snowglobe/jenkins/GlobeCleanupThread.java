package com.nirima.snowglobe.jenkins;

import com.nirima.snowglobe.jenkins.api.remote.DestroyCommand;
import com.nirima.snowglobe.jenkins.api.remote.Globe;
import com.nirima.snowglobe.jenkins.api.remote.ListCommand;
import com.nirima.snowglobe.jenkins.api.remote.RemoveCommand;

import org.jenkinsci.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

import hudson.Extension;
import hudson.model.AsyncPeriodicWork;
import hudson.model.TaskListener;

@Extension
@Symbol({"snowglobeCleanup"})
public class GlobeCleanupThread extends AsyncPeriodicWork {

  private static final Logger log = LoggerFactory.getLogger(GlobeCleanupThread.class);

  public GlobeCleanupThread() {
    super("SnowGlobe cleanup");
  }

  public GlobeCleanupThread(String name) {
    super(name);
  }

  @Override
  protected void execute(TaskListener taskListener) throws IOException, InterruptedException {

    log.debug("Running SnowGlobe cleanup thread");


    destroyGlobesBeyondMaximumAge();

    removeGlobesBeyondMaximumAge();

    deleteGlobesBeyondTotalNumber();
  }

  private void deleteGlobesBeyondTotalNumber() throws IOException {
    SnowGlobePluginConfiguration config = SnowGlobePluginConfiguration.get();

    // Potentially remove some items
    ListCommand lc = new ListCommand(config.getServerUrl());

    if (config.getMaxCount() != Integer.MAX_VALUE) {
      List<Globe> globeList = lc.execute();

      globeList = globeList.stream().filter(it -> it.tags.contains("jenkins")).collect(Collectors.toList());

      globeList.sort(Comparator.comparing(o -> o.lastUpdate));

      int toSize = globeList.size() - config.maximumCount;

      if( toSize > 0) {
        globeList.subList(0, globeList.size() - 1).forEach(
            this::removeGlobe
        );
      }
    }
  }

  private void removeGlobesBeyondMaximumAge() throws IOException {
    SnowGlobePluginConfiguration config = SnowGlobePluginConfiguration.get();

    // Potentially remove some items
    ListCommand lc = new ListCommand(config.getServerUrl());

    if (config.getMaximumEmptyAgeMs() != Long.MAX_VALUE) {
      List<Globe> globeList = lc.execute();
      long oldest = new Date().getTime() - config.getMaximumEmptyAgeMs();
      Date oldestDate = new Date(oldest);

      globeList.stream()
          .filter(it -> it.tags.contains("jenkins"))
          .filter(it -> it.tags.contains("_empty"))
          .filter(it ->
                      it.lastUpdate.before(oldestDate)
          ).forEach(
          this::destroyGlobe
      );
    }
  }

  private void destroyGlobesBeyondMaximumAge() throws IOException {
    SnowGlobePluginConfiguration config = SnowGlobePluginConfiguration.get();

    // Potentially remove some items
    ListCommand lc = new ListCommand(config.getServerUrl());

    if (config.getMaximumAgeMs() != Long.MAX_VALUE) {
      List<Globe> globeList = lc.execute();
      long oldest = new Date().getTime() - config.getMaximumAgeMs();
      Date oldestDate = new Date(oldest);

      globeList.stream()
          .filter(it -> it.tags.contains("jenkins"))
          .filter(it ->
                      it.lastUpdate.before(oldestDate)
          ).forEach(
          this::destroyGlobe
      );
    }
  }

  private void destroyGlobe(Globe it) {
    DestroyCommand
        dc =
        new DestroyCommand(SnowGlobePluginConfiguration.get().getServerUrl(), it.id);
    try {
      dc.execute();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void removeGlobe(Globe it) {
    try {

        new DestroyCommand(SnowGlobePluginConfiguration.get().getServerUrl(), it.id).execute();
        new RemoveCommand (SnowGlobePluginConfiguration.get().getServerUrl(), it.id).execute();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public long getRecurrencePeriod() {
    return 60000L * 30; // half-hourly
  }
}
