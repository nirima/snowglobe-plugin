package com.nirima.snowglobe.jenkins.step;

import com.nirima.snowglobe.jenkins.SnowGlobePluginConfiguration;
import com.nirima.snowglobe.jenkins.actions.SnowGlobeAction;
import com.nirima.snowglobe.jenkins.api.remote.CloneCommand;

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

    //    public static final boolean ignoreSslErrors = HttpRequest.DescriptorImpl.ignoreSslErrors;
//    public static final HttpMode httpMode                  = HttpRequest.DescriptorImpl.httpMode;
//    public static final String   httpProxy                 = HttpRequest.DescriptorImpl.httpProxy;
//    public static final String   validResponseCodes        = HttpRequest.DescriptorImpl.validResponseCodes;
//    public static final String   validResponseContent      = HttpRequest.DescriptorImpl.validResponseContent;
//    public static final MimeType acceptType                = HttpRequest.DescriptorImpl.acceptType;
//    public static final MimeType contentType               = HttpRequest.DescriptorImpl.contentType;
//    public static final int      timeout                   = HttpRequest.DescriptorImpl.timeout;
//    public static final Boolean  consoleLogResponseBody    = HttpRequest.DescriptorImpl.consoleLogResponseBody;
//    public static final Boolean  quiet                     = HttpRequest.DescriptorImpl.quiet;
//    public static final String   authentication            = HttpRequest.DescriptorImpl.authentication;
//    public static final String   requestBody               = HttpRequest.DescriptorImpl.requestBody;
//    public static final List <HttpRequestNameValuePair> customHeaders = Collections.<HttpRequestNameValuePair>emptyList();
//    public static final String outputFile = "";
//    public static final ResponseHandle responseHandle = ResponseHandle.STRING;
//
    public DescriptorImpl() {
      super(Execution.class);
    }

    //
    @Override
    public String getFunctionName() {
      return "snowglobe_clone";
    }
//
//    @Override
//    public String getDisplayName() {
//      return "Perform an HTTP Request and return a response object";
//    }
//
//    public ListBoxModel doFillHttpModeItems() {
//      return HttpMode.getFillItems();
//    }
//
//    public ListBoxModel doFillAcceptTypeItems() {
//      return MimeType.getContentTypeFillItems();
//    }
//
//    public ListBoxModel doFillContentTypeItems() {
//      return MimeType.getContentTypeFillItems();
//    }
//
//    public ListBoxModel doFillResponseHandleItems() {
//      ListBoxModel items = new ListBoxModel();
//      for (ResponseHandle responseHandle : ResponseHandle.values()) {
//        items.add(responseHandle.name());
//      }
//      return items;
//    }
//
//    public ListBoxModel doFillAuthenticationItems(@AncestorInPath Item project,
//                                                  @QueryParameter String url) {
//      return HttpRequest.DescriptorImpl.fillAuthenticationItems(project, url);
//    }
//
//    public FormValidation doCheckValidResponseCodes(@QueryParameter String value) {
//      return HttpRequest.DescriptorImpl.checkValidResponseCodes(value);
//    }

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
      return "OK";
    }

    private static final long serialVersionUID = 1L;


    public Item getProject() {
      return run.getParent();
    }
  }
}