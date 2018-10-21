package io.jenkins.plugins.analysis.core.extension.warnings;

import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;

public abstract class OutputDescriptor extends Descriptor<Output> {

}

// public ToolPropertyDescriptor getDescriptor() {
//     return (ToolPropertyDescriptor) Jenkins.getInstance().getDescriptorOrDie(getClass());
// }

// /**
//  * What is your 'T'?
//  */
// public abstract Class<T> type();

// /**
//  * Lists up all the registered {@link ToolPropertyDescriptor}s in the system.
//  *
//  * @see ToolDescriptor#getPropertyDescriptors() 
//  */
// public static DescriptorExtensionList<ToolProperty<?>,ToolPropertyDescriptor> all() {
//     return (DescriptorExtensionList) Jenkins.getInstance().getDescriptorList(ToolProperty.class);
// }