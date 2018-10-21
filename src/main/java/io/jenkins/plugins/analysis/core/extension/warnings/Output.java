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
import org.jenkinsci.Symbol;

public abstract class Output extends AbstractDescribableImpl<Output> implements ExtensionPoint, Describable<Output>  { 

    volatile private PrintStream logger;
    volatile private Run<?, ?> run;
    volatile private String logName;
    /**
     * All registered {@link Output}s.
     */
    public static ExtensionList<Output> all() {
        return Jenkins.getInstance().getExtensionList(Output.class); 
    }

    public final void doOutput( final Run<?, ?> run, final PrintStream logger, final AnalysisResult report) {
        this.logger = logger;
        this.run = run;
        OutputRunCache runCache;

        Symbol annotation = getDescriptor().getClass().getAnnotation(Symbol.class);
        if (annotation != null) {
            logName = annotation.value()[0];
        } else {
            logName = getClass().getSimpleName();
        }

        if(report.getNewIssues().size() + report.getOutstandingIssues().size() + report.getFixedIssues().size() > 0) {
            runCache = prepare(run);
            if (runCache == null) {
                return;
            }
        } else {
            log( "No issues to record");
            return;
        }

        if( report.getNewIssues().size() > 0) {
            newIssuePrepare(report.getNewIssues().size(), runCache);
            for (Issue issue : report.getNewIssues()) {
                newIssue(runCache, issue.getMessage(), issue.getFileName(), issue.getLineStart());
            }
        }
        
        if( report.getOutstandingIssues().size() > 0) {
            outstandingIssuePrepare(report.getOutstandingIssues().size(), runCache);
            for (Issue issue : report.getOutstandingIssues()) {
                outstandingIssue(runCache, issue.getMessage(), issue.getFileName(), issue.getLineStart());
            }
        }

        if( report.getFixedIssues().size() > 0) {
            fixedIssuePrepare(report.getFixedIssues().size(), runCache);
            for (Issue issue : report.getFixedIssues()) {
                fixedIssue(runCache, issue.getMessage(), issue.getFileName(), issue.getLineStart());
            }
        }

        complete(runCache);
    }

    protected void log(String var1) {
        log("%s", var1);
    }

    protected void log(String var1, Object... var2) {
        logger.printf("[%s] ", logName);
        logger.printf(var1, var2);
        logger.println();
    }

    public OutputRunCache prepare(final Run<?, ?> run) { return null; }

    public void newIssuePrepare(final int size, OutputRunCache runCache) { }
    public void outstandingIssuePrepare(final int size, OutputRunCache runCache) { }
    public void fixedIssuePrepare(final int size, OutputRunCache runCache) { }

    public abstract void newIssue(OutputRunCache runCache, final String message, final String filename, final int lineStart);
    public abstract void outstandingIssue(OutputRunCache runCache, final String message, final String filename, final int lineStart);
    public abstract void fixedIssue(OutputRunCache runCache, final String message, final String filename, final int lineStart);

    public void complete(OutputRunCache runCache) { }

}