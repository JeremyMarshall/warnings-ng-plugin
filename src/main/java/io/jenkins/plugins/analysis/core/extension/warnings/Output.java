package io.jenkins.plugins.analysis.core.extension.warnings;

import java.io.PrintStream;

import hudson.model.AbstractDescribableImpl;
import hudson.model.Describable;
import hudson.ExtensionList;
import hudson.ExtensionPoint;

import edu.hm.hafner.analysis.Issue;
import io.jenkins.plugins.analysis.core.model.AnalysisResult;
import jenkins.model.Jenkins;

public abstract class Output extends AbstractDescribableImpl<Output> implements ExtensionPoint, Describable<Output>  { 

    volatile private PrintStream logger;
    /**
     * All registered {@link Output}s.
     */
    public static ExtensionList<Output> all() {
        return Jenkins.getInstance().getExtensionList(Output.class); 
    }

    public final void doOutput(final PrintStream logger, final AnalysisResult report) {
        this.logger = logger;

        if(report.getNewIssues().size() + report.getOutstandingIssues().size() + report.getFixedIssues().size() > 0) {
            prepare();
        }

        if( report.getNewIssues().size() > 0) {
            newIssuePrepare();
            for (Issue issue : report.getNewIssues()) {
                newIssue(issue.getMessage(), issue.getFileName(), issue.getLineStart());
            }
        }
        
        if( report.getOutstandingIssues().size() > 0) {
            outstandingIssuePrepare();
            for (Issue issue : report.getOutstandingIssues()) {
                outstandingIssue(issue.getMessage(), issue.getFileName(), issue.getLineStart());
            }
        }

        if( report.getFixedIssues().size() > 0) {
            fixedIssuePrepare();
            for (Issue issue : report.getFixedIssues()) {
                fixedIssue(issue.getMessage(), issue.getFileName(), issue.getLineStart());
            }
        }
    }

    protected void log(String var1) {
        logger.println(var1);
    }

    protected void log(String var1, Object... var2) {
        logger.printf(var1, var2);
        logger.println();
    }
    public void prepare() { }
    public void newIssuePrepare() { }
    public void outstandingIssuePrepare() { }
    public void fixedIssuePrepare() { }

    public abstract void newIssue(final String message, final String filename, final int lineStart);
    public abstract void outstandingIssue(final String message, final String filename, final int lineStart);
    public abstract void fixedIssue(final String message, final String filename, final int lineStart);
}