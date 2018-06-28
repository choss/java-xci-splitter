package org.insanedevelopment.nx.xci.cutter.frontend.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.insanedevelopment.nx.xci.cutter.backend.batch.BatchHelper;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Label;

public class BatchModeDialog {

	protected Shell shlBatchOperations;
	private List listBatchItems;
	private Button btnAddFile;

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
		shlBatchOperations.setSize(582, 665);
		shlBatchOperations.setText("Batch operations");
		shlBatchOperations.setLayout(null);

		listBatchItems = new List(shlBatchOperations, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		listBatchItems.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == 127) {
					int[] indices = listBatchItems.getSelectionIndices();
					listBatchItems.remove(indices);
				}
			}
		});
		listBatchItems.setBounds(10, 10, 556, 445);

		Button btnSelectFolder = new Button(shlBatchOperations, SWT.NONE);
		btnSelectFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(shlBatchOperations);
				String baseDir = dialog.open();
				java.util.List<String> files = BatchHelper.getAllXciImageFilesRecursively(baseDir);
				for (String file : files) {
					listBatchItems.add(file);
				}
				listBatchItems.add(files.toString());
			}
		});
		btnSelectFolder.setBounds(10, 461, 75, 25);
		btnSelectFolder.setText("Add Folder");

		btnAddFile = new Button(shlBatchOperations, SWT.NONE);
		btnAddFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(shlBatchOperations, SWT.OPEN);
				fileDialog.setFilterExtensions(new String[] { "*.xci;*.xc0", "*.xci", "*.xc0" });
				String file = fileDialog.open();
				if (file != null) {
					listBatchItems.add(file);
				}
			}
		});
		btnAddFile.setBounds(10, 492, 75, 25);
		btnAddFile.setText("Add File");
		
		ProgressBar progressBarSingleFile = new ProgressBar(shlBatchOperations, SWT.NONE);
		progressBarSingleFile.setBounds(10, 609, 556, 17);
		
		Label lblSingleFileStatus = new Label(shlBatchOperations, SWT.NONE);
		lblSingleFileStatus.setBounds(10, 588, 556, 15);
		
		ProgressBar progressBarFiles = new ProgressBar(shlBatchOperations, SWT.NONE);
		progressBarFiles.setBounds(10, 565, 556, 17);
		
		Label lblFileStatus = new Label(shlBatchOperations, SWT.NONE);
		lblFileStatus.setBounds(10, 544, 556, 15);
		
		Button btnNewButton = new Button(shlBatchOperations, SWT.NONE);
		btnNewButton.setBounds(91, 461, 75, 56);
		btnNewButton.setText("Trim");
		
		Button btnSplitTrim = new Button(shlBatchOperations, SWT.NONE);
		btnSplitTrim.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnSplitTrim.setText("Split &&Trim");
		btnSplitTrim.setBounds(172, 461, 75, 56);
		
		Button btnMerge = new Button(shlBatchOperations, SWT.NONE);
		btnMerge.setText("Merge");
		btnMerge.setBounds(253, 461, 75, 56);
		
		Button checkDeleteFilesAfter = new Button(shlBatchOperations, SWT.CHECK);
		checkDeleteFilesAfter.setBounds(344, 481, 85, 16);
		checkDeleteFilesAfter.setText("delete files");
		
		Label lblFileName = new Label(shlBatchOperations, SWT.NONE);
		lblFileName.setBounds(10, 523, 556, 15);

	}
}
