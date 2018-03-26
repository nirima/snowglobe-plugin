package com.nirima.snowglobe.jenkins.step;

import com.nirima.snowglobe.jenkins.SnowGlobePluginConfiguration;
import com.nirima.snowglobe.jenkins.actions.SnowGlobeAction;
import com.nirima.snowglobe.jenkins.api.remote.DestroyCommand;
import com.nirima.snowglobe.jenkins.api.remote.RemoveCommand;
import com.nirima.snowglobe.jenkins.api.remote.StateCommand;

import org.jenkinsci.plugins.workflow.steps.AbstractStepDescriptorImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractStepImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractSynchronousNonBlockingStepExecution;
import org.jenkinsci.plugins.workflow.steps.StepContextParameter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import javax.inject.Inject;

import hudson.Extension;
import hudson.model.Item;
import hudson.model.Run;
import hudson.model.TaskListener;

public final class DestroyStep extends AbstractStepImpl {

  String globeId;
  boolean remove;


  public boolean isRemove() {
    return remove;
  }
  @DataBoundSetter
  public void setRemove(boolean remove) {
    this.remove = remove;
  }



  @DataBoundConstructor
  public DestroyStep() {

  }

  public String getGlobeId() {
    return globeId;
  }

  @DataBoundSetter
  public void setGlobeId(String sourceId) {
    this.globeId = sourceId;
  }


  @Override
  public DescriptorImpl getDescriptor() {
    return (DescriptorImpl) super.getDescriptor();
  }


  @Extension
  public static final class DescriptorImpl extends AbstractStepDescriptorImpl {

    public DescriptorImpl() {
      super(Execution.class);
    }

    //
    @Override
    public String getFunctionName() {
      return "snowglobe_destroy";
    }


  }

  public static final class Execution extends AbstractSynchronousNonBlockingStepExecution<String> {

    @Inject
    private transient DestroyStep step;

    @StepContextParameter
    private transient Run<?, ?> run;
    @StepContextParameter
    private transient TaskListener listener;

    @Override
    protected String run() throws Exception {

      String baseUrl = SnowGlobePluginConfiguration.get().getServerUrl();
      new DestroyCommand(baseUrl, step.globeId).execute();

      if (step.isRemove()) {
        new RemoveCommand(baseUrl, step.globeId).execute();


      }
      return "OK";
    }

    private static final long serialVersionUID = 1L;


    public Item getProject() {
      return run.getParent();
    }
  }
}