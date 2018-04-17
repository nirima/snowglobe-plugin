package com.nirima.snowglobe.jenkins.step;

import com.google.common.collect.Sets;

import com.nirima.snowglobe.jenkins.SnowGlobePluginConfiguration;
import com.nirima.snowglobe.jenkins.actions.SnowGlobeAction;
import com.nirima.snowglobe.jenkins.api.remote.AddTagCommand;
import com.nirima.snowglobe.jenkins.api.remote.CloneCommand;
import com.nirima.snowglobe.jenkins.api.remote.SetTagsCommand;

import org.jenkinsci.plugins.workflow.steps.AbstractStepDescriptorImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractStepImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractSynchronousNonBlockingStepExecution;
import org.jenkinsci.plugins.workflow.steps.StepContextParameter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.util.Set;

import javax.inject.Inject;

import hudson.Extension;
import hudson.model.Item;
import hudson.model.Run;
import hudson.model.TaskListener;

public final class CloneStep extends AbstractStepImpl {

  String sourceId;
  String targetId;
  boolean createAction;

  @DataBoundConstructor
  public CloneStep() {

  }

  public String getSourceId() {
    return sourceId;
  }

  @DataBoundSetter
  public void setSourceId(String sourceId) {
    this.sourceId = sourceId;
  }

  public String getTargetId() {
    return targetId;
  }

  @DataBoundSetter
  public void setTargetId(String targetId) {
    this.targetId = targetId;
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
      return "snowglobe_clone";
    }


  }

  public static final class Execution extends AbstractSynchronousNonBlockingStepExecution<String> {

    @Inject
    private transient CloneStep step;

    @StepContextParameter
    private transient Run<?, ?> run;
    @StepContextParameter
    private transient TaskListener listener;

    @Override
    protected String run() throws Exception {

      String baseUrl = SnowGlobePluginConfiguration.get().getServerUrl();
      new CloneCommand(baseUrl, step.sourceId, step.targetId).execute();

      if (step.isCreateAction()) {
        SnowGlobeAction action = new SnowGlobeAction(step.targetId);

        run.addAction(action);
      }

      // Also tag it with 'jenkins' so we know where it's come from.
      new AddTagCommand(baseUrl, step.targetId, "jenkins").execute();

      return "OK";
    }

    private static final long serialVersionUID = 1L;


    public Item getProject() {
      return run.getParent();
    }
  }
}