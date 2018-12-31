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
    public final void newIssuePrepare(final int size) {
        log("New %d", size);
    }

    @Override
    public final void newIssue(final String message, final String filename, final int lineStart){
        log("Issue '%s','%s','%d'", message, filename, lineStart);
    }

    @Override
    public final void outstandingIssuePrepare(final int size) {
        log("Outstanding %d", size);
    }

    @Override
    public final void outstandingIssue(final String message, final String filename, final int lineStart){
        log("Issue '%s','%s','%d'", message, filename, lineStart);
    }

    @Override
    public final void fixedIssuePrepare(final int size) {
        log("Fixed %d", size);
    }

    @Override
    public final void fixedIssue(final String message, final String filename, final int lineStart){
        log("Issue '%s','%s','%d'", message, filename, lineStart);
    }

    @Symbol("dumpIssues")
    @Extension
    public static final class DescriptorImpl extends OutputDescriptor{
        @Override public String getDisplayName() {
            return "Dump Issues";
        }

    }
}