package io.jenkins.plugins.analysis.core.extension.warnings;

import hudson.model.AbstractDescribableImpl;
import hudson.model.Describable;
import hudson.ExtensionList;
import hudson.ExtensionPoint;
import jenkins.model.Jenkins;

public abstract class Output extends AbstractDescribableImpl<Output> implements ExtensionPoint, Describable<Output>  { 

    // public abstract String getName();

    /**
     * All registered {@link Output}s.
     */
    public static ExtensionList<Output> all() {
        return Jenkins.getInstance().getExtensionList(Output.class); 
    }
}