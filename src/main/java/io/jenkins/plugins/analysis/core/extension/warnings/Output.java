package io.jenkins.plugins.analysis.core.extension.warnings;

import java.io.PrintStream;

import hudson.model.AbstractDescribableImpl;
import hudson.model.Describable;
import hudson.ExtensionList;
import hudson.ExtensionPoint;

import edu.hm.hafner.analysis.Issue;
import hudson.model.Run;
import io.jenkins.plugins.analysis.core.model.AnalysisResult;
import jenkins.model.Jenkins;

public abstract class Output extends AbstractDescribableImpl<Output> implements ExtensionPoint, Describable<Output>  { 

    volatile private PrintStream logger;
    volatile private Run<?, ?> run;
    /**
     * All registered {@link Output}s.
     */
    public static ExtensionList<Output> all() {
        return Jenkins.getInstance().getExtensionList(Output.class); 
    }

    public final void doOutput( final Run<?, ?> run, final PrintStream logger, final AnalysisResult report) {
        this.logger = logger;
        this.run = run;

        if(report.getNewIssues().size() + report.getOutstandingIssues().size() + report.getFixedIssues().size() > 0) {
            if (!prepare(run)) {
                return;
            }
        }

        if( report.getNewIssues().size() > 0) {
            newIssuePrepare(report.getNewIssues().size());
            for (Issue issue : report.getNewIssues()) {
                newIssue(issue.getMessage(), issue.getFileName(), issue.getLineStart());
            }
        }
        
        if( report.getOutstandingIssues().size() > 0) {
            outstandingIssuePrepare(report.getOutstandingIssues().size());
            for (Issue issue : report.getOutstandingIssues()) {
                outstandingIssue(issue.getMessage(), issue.getFileName(), issue.getLineStart());
            }
        }

        if( report.getFixedIssues().size() > 0) {
            fixedIssuePrepare(report.getFixedIssues().size());
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
    public boolean prepare(final Run<?, ?> run) { return true; }
    public void newIssuePrepare(final int size) { }
    public void outstandingIssuePrepare(final int size) { }
    public void fixedIssuePrepare(final int size) { }

    public abstract void newIssue(final String message, final String filename, final int lineStart);
    public abstract void outstandingIssue(final String message, final String filename, final int lineStart);
    public abstract void fixedIssue(final String message, final String filename, final int lineStart);
}