package com.nirima.snowglobe.jenkins;

import com.google.common.base.Strings;

import net.sf.json.JSONObject;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.Functions;
import hudson.Util;
import jenkins.model.GlobalConfiguration;

@Extension
public class SnowGlobePluginConfiguration extends GlobalConfiguration {

  private String serverUrl;

  public String maximumAge;

  public String maximumEmptyAge;

  public Integer maximumCount;

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

  public String getMaximumAge() {
    return maximumAge;
  }

  public void setMaximumAge(String maximumAge) {
    this.maximumAge = maximumAge;
  }

  public int getMaxCount() {
    if( maximumCount == null )
      return Integer.MAX_VALUE;
    return maximumCount;
  }

  public String getMaximumEmptyAge() {
    return maximumEmptyAge;
  }

  public void setMaximumEmptyAge(String maximumEmptyAge) {
    this.maximumEmptyAge = maximumEmptyAge;
  }

  public long getMaximumAgeMs() {

    if(Strings.isNullOrEmpty(this.maximumAge))
      return Long.MAX_VALUE;

    return parseDuration(this.maximumAge);
  }

  public long getMaximumEmptyAgeMs() {

    if(Strings.isNullOrEmpty(this.maximumEmptyAge))
      return Long.MAX_VALUE;

    return parseDuration(this.maximumEmptyAge);
  }


  public static Long parseDuration(String maximumAge) {
    PeriodFormatter formatter = new PeriodFormatterBuilder()
        .appendWeeks().appendSuffix("w")
        .appendDays().appendSuffix("d")
        .appendHours().appendSuffix("h")
        .appendMinutes().appendSuffix("m")
        .toFormatter();

    Period p = formatter.parsePeriod(maximumAge);

    return Long.valueOf(p.toStandardDuration().getMillis());
  }

  public Integer getMaximumCount() {
    return maximumCount;
  }

  public void setMaximumCount(Integer maximumCount) {
    this.maximumCount = maximumCount;
  }
}
