package io.jenkins.plugins.analysis.core.extension.warnings;

import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;

public class Test1 extends Output {

    @DataBoundConstructor
    public Test1() {

    }

    @Symbol("test1")
    @Extension
    public static final class DescriptorImpl extends OutputDescriptor{
        // @Override public String getDisplayName() {
        //     return "Test One";
        // }
    }
}