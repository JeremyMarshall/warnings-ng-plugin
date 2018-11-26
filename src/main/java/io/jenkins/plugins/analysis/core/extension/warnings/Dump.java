package io.jenkins.plugins.analysis.core.extension.warnings;

import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import edu.hm.hafner.analysis.Issue;
import hudson.Extension;


public class Dump extends Output {

    @DataBoundConstructor
    public Dump() {

    }

    @Override
    public final void newIssuePrepare() {
        log("New");
    }

    @Override
    public final void newIssue(final Issue issue){
        log("Issue '%s','%s','%d'", issue.getMessage(), issue.getFileName(), issue.getLineStart());
    }

    @Override
    public final void outstandingIssuePrepare() {
        log("Outstanding");
    }

    @Override
    public final void outstandingIssue(final Issue issue){
        log("Issue '%s','%s','%d'",  issue.getMessage(), issue.getFileName(), issue.getLineStart());
    }

    @Override
    public final void fixedIssuePrepare() {
        log("Fixed");
    }

    @Override
    public final void fixedIssue(final Issue issue){
         log("Issue '%s','%s','%d'", issue.getMessage(), issue.getFileName(), issue.getLineStart());
    }

    @Symbol("dumpIssues")
    @Extension
    public static final class DescriptorImpl extends OutputDescriptor{
        @Override public String getDisplayName() {
            return "Dump Issues";
        }

    }
}