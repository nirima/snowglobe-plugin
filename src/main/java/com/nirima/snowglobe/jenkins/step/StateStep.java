package com.nirima.snowglobe.jenkins.step;

import com.nirima.snowglobe.jenkins.SnowGlobePluginConfiguration;
import com.nirima.snowglobe.jenkins.actions.SnowGlobeAction;
import com.nirima.snowglobe.jenkins.api.remote.ApplyCommand;
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

public final class StateStep extends AbstractStepImpl {

  String globeId;

  boolean createAction;

  @DataBoundConstructor
  public StateStep() {

  }

  public String getGlobeId() {
    return globeId;
  }

  @DataBoundSetter
  public void setGlobeId(String sourceId) {
    this.globeId = sourceId;
  }


  public boolean isCreateAction() {
    return createAction;
  }
  @DataBoundSetter
  public void setCreateAction(boolean createAction) {
    this.createAction = createAction;
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
      return "snowglobe_state";
    }


  }

  public static final class Execution extends AbstractSynchronousNonBlockingStepExecution<String> {

    @Inject
    private transient StateStep step;

    @StepContextParameter
    private transient Run<?, ?> run;
    @StepContextParameter
    private transient TaskListener listener;

    @Override
    protected String run() throws Exception {

      String baseUrl = SnowGlobePluginConfiguration.get().getServerUrl();
      String data = new StateCommand(baseUrl, step.globeId).execute();

      if (step.isCreateAction()) {
        SnowGlobeAction action = new SnowGlobeAction(step.globeId);

        run.addAction(action);
      }
      return data;
    }

    private static final long serialVersionUID = 1L;


    public Item getProject() {
      return run.getParent();
    }
  }
}