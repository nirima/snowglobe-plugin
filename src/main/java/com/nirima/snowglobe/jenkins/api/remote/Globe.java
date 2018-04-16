package com.nirima.snowglobe.jenkins.api.remote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Globe implements Serializable {

  public String id;
  public String type;

  public String name;
  public String description;

  public Date lastUpdate;
  public Date created;

  public List<String> tags;

  public List<String> configFiles = new ArrayList<>();

}
