package io.jenkins.plugins.analysis.core.extension.warnings;

import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import edu.hm.hafner.analysis.Issue;

import hudson.Extension;


public class Test2 extends Output {

    private String field1;
    private String field2;

    @DataBoundConstructor
    public Test2() { }

    public final String getField1() {
        return this.field1;
    }

    @DataBoundSetter
    public final void setField1(final String field1) {
        this.field1 = field1;
    }
    public final String getField2() {
        return this.field2;
    }

    @DataBoundSetter
    public final void setField2(final String field2) {
        this.field2 = field2;
    }

    @Override
    public final void newIssue(final String message, final String filename, final int lineStart){

    }
    @Override
    public final void outstandingIssue(final String message, final String filename, final int lineStart){

    }

    @Override
    public final void fixedIssue(final String message, final String filename, final int lineStart) {
    }


    @Symbol("test2")
    @Extension
    public static final class DescriptorImpl extends OutputDescriptor {
        @Override public String getDisplayName() {
            return "Test Two";
        }
    }
}