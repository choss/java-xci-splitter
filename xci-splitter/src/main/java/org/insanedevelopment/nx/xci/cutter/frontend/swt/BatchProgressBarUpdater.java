package org.insanedevelopment.nx.xci.cutter.frontend.swt;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.insanedevelopment.nx.xci.cutter.backend.batch.BatchProgressUpdater;

public class BatchProgressBarUpdater implements BatchProgressUpdater {

	private Label lblFileName;
	private Label lblOverallStatus;
	private ProgressBar progressBar;

	public BatchProgressBarUpdater(Label lblFileName, Label lblOverallStatus, ProgressBar progressBar) {
		this.lblFileName = lblFileName;
		this.lblOverallStatus = lblOverallStatus;
		this.progressBar = progressBar;
	}

	@Override
	public void updateBatchProgress(String itemName, double percentage, int currentItemNo, int totalItems) {
		Display.getDefault().asyncExec(() -> progressBar.setSelection((int) percentage));
		Display.getDefault().asyncExec(() -> lblFileName.setText(itemName));
		Display.getDefault().asyncExec(() -> lblOverallStatus.setText("Processing File " + currentItemNo + " of " + totalItems));
	}

}
