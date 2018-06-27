package org.insanedevelopment.nx.xci.cutter.backend;

public abstract class AbstractUpdateThrottelingWorkflowStepObserver implements WorkflowStepPercentageObserver {

	private int oldPercentage;

	@Override
	public final void updatePercentage(double percentage, long readSize, long totalSize) {
		final int percentageInt = (int) percentage;
		if (oldPercentage != percentageInt) {
			oldPercentage = percentageInt;
			updatePercentageInternal(percentage, percentageInt, readSize, totalSize);
		}
	}

	protected abstract void updatePercentageInternal(double percentage, int percentageInt, long readSize, long totalSize);

}
