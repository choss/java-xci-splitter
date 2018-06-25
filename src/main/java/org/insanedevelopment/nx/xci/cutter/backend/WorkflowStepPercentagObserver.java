package org.insanedevelopment.nx.xci.cutter.backend;

public interface WorkflowStepPercentagObserver {

	public void updatePercentage(double percentage, long readSize, long totalSize);

	public void setWorkflowStep(WorkflowStep step);

}
