package org.insanedevelopment.nx.xci.cutter.backend;

public interface WorkflowStepPercentageObserver {

	public void updatePercentage(double percentage, long readSize, long totalSize);

	public void setWorkflowStep(WorkflowStep step);

}
