package org.insanedevelopment.nx.xci.cutter.backend.ui.swt;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.insanedevelopment.nx.xci.cutter.backend.WorkflowStep;
import org.insanedevelopment.nx.xci.cutter.backend.WorkflowStepPercentageObserver;

public class ProgressBarUpdater implements WorkflowStepPercentageObserver {

	private Label lblWorkflowStep;
	private ProgressBar progressBarWorkflowProgress;
	private Button[] buttons;

	public ProgressBarUpdater(Label lblWorkflowStep, ProgressBar progressBarWorkflowProgress, Button... buttons) {
		this.lblWorkflowStep = lblWorkflowStep;
		this.progressBarWorkflowProgress = progressBarWorkflowProgress;
		this.buttons = buttons;
	}

	@Override
	public void updatePercentage(double percentage, long readSize, long totalSize) {
		final int percentageInt = (int) percentage;
		Display.getDefault().asyncExec(() -> progressBarWorkflowProgress.setSelection(percentageInt));
	}

	@Override
	public void setWorkflowStep(WorkflowStep step) {
		if (step == null) {
			step = WorkflowStep.UNKNOWN;
		}
		boolean isDone = step == WorkflowStep.DONE;
		Display.getDefault().asyncExec(() -> setButtonState(isDone));
		final String workflowStepString = step.getDisplayString();
		Display.getDefault().asyncExec(() -> lblWorkflowStep.setText(workflowStepString));
	}

	private void setButtonState(boolean isDone) {
		for (Button button : buttons) {
			button.setEnabled(isDone);
		}
	}

}
