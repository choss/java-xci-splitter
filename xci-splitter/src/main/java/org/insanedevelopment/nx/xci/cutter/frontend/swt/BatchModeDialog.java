package org.insanedevelopment.nx.xci.cutter.frontend.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class BatchModeDialog {

	protected Shell shlBatchOperations;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BatchModeDialog window = new BatchModeDialog();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlBatchOperations.open();
		shlBatchOperations.layout();
		while (!shlBatchOperations.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlBatchOperations = new Shell(SWT.SHELL_TRIM & (~SWT.RESIZE));
		shlBatchOperations.setSize(679, 568);
		shlBatchOperations.setText("Batch operations");
		shlBatchOperations.setLayout(null);

		List list = new List(shlBatchOperations, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		list.setBounds(10, 10, 643, 249);

	}
}
