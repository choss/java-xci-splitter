package org.insanedevelopment.nx.xci.cutter.frontend.swt;

import org.apache.commons.lang3.SystemUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.insanedevelopment.nx.xci.cutter.backend.FileExtensionUtils;
import org.insanedevelopment.nx.xci.cutter.backend.model.splitmethods.SplitMethodEnum;
import org.insanedevelopment.nx.xci.cutter.frontend.GuiModelSingleFile;

public class XciSplitterSwtGui {

	protected Shell shlXciSplitter;
	private Text inputFile;
	private GuiModelSingleFile model = new GuiModelSingleFile();
	private Text cartSize;
	private Text dataSize;
	private Text targetFile;
	private Text xciSize;
	private Label lblSplit;
	private Label lblParts;
	private Text isSplit;
	private Text parts;
	private Button btnTrim;
	private Button btnSplitAndTrim;
	private Menu menu_1;
	private MenuItem mntmExit;
	private MenuItem mntmBatchProcessing;
	private Label lblWorkflowStep;
	private ProgressBar progressBarWorkflowProgress;
	private Button btnMerge;

	private BatchModeDialog batchModeDialog = new BatchModeDialog();

	/**
	 * Launch the application.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			XciSplitterSwtGui window = new XciSplitterSwtGui();
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
		shlXciSplitter.open();
		shlXciSplitter.layout();
		while (!shlXciSplitter.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlXciSplitter = new Shell(SWT.SHELL_TRIM);
		shlXciSplitter.setSize(615, 426);
		shlXciSplitter.setText("Xci Splitter");

		Button btnSelectSource = new Button(shlXciSplitter, SWT.NONE);
		btnSelectSource.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fileDialog = new FileDialog(shlXciSplitter, SWT.OPEN);
				fileDialog.setFilterExtensions(FileExtensionUtils.SELECT_FILE_FILTER);
				String file = fileDialog.open();
				if (file != null) {
					inputFile.setText(file);
					model.setSourceFile(file);
					updateInformationFields();
				}
			}

		});
		btnSelectSource.setBounds(10, 48, 75, 25);
		btnSelectSource.setText("Source");

		inputFile = new Text(shlXciSplitter, SWT.BORDER);
		inputFile.setEnabled(false);
		inputFile.setBounds(91, 50, 506, 21);

		cartSize = new Text(shlXciSplitter, SWT.BORDER);
		cartSize.setEditable(false);
		cartSize.setBounds(71, 155, 76, 21);

		Label lblCartSize = new Label(shlXciSplitter, SWT.NONE);
		lblCartSize.setBounds(10, 158, 55, 15);
		lblCartSize.setText("Cart Size");

		Label lblDataSize = new Label(shlXciSplitter, SWT.NONE);
		lblDataSize.setBounds(10, 185, 55, 15);
		lblDataSize.setText("Data Size");

		dataSize = new Text(shlXciSplitter, SWT.BORDER);
		dataSize.setEditable(false);
		dataSize.setBounds(71, 182, 76, 21);

		Button btnTarget = new Button(shlXciSplitter, SWT.NONE);
		btnTarget.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(shlXciSplitter, SWT.SAVE);
				fileDialog.setFilterExtensions(FileExtensionUtils.SAVE_FILE_FILTER);
				fileDialog.setFileName(model.getTargetFileNameProposal());
				String file = fileDialog.open();
				if (file != null) {
					if (SystemUtils.IS_OS_MAC_OSX) {
						String[] fileSplit = file.split(":");
						file = fileSplit[0] + fileSplit[fileSplit.length -1];
					}
					targetFile.setText(file);
					model.setTargetFile(file);
				}
			}
		});
		btnTarget.setBounds(10, 75, 75, 25);
		btnTarget.setText("Target");

		targetFile = new Text(shlXciSplitter, SWT.BORDER);
		targetFile.setBounds(91, 77, 506, 21);

		xciSize = new Text(shlXciSplitter, SWT.BORDER);
		xciSize.setEditable(false);
		xciSize.setBounds(71, 209, 76, 21);

		Label lblXciSize = new Label(shlXciSplitter, SWT.NONE);
		lblXciSize.setBounds(10, 212, 55, 15);
		lblXciSize.setText("File Size");

		lblSplit = new Label(shlXciSplitter, SWT.NONE);
		lblSplit.setBounds(167, 158, 39, 15);
		lblSplit.setText("is Split?");

		lblParts = new Label(shlXciSplitter, SWT.NONE);
		lblParts.setBounds(167, 185, 39, 15);
		lblParts.setText("Parts");

		isSplit = new Text(shlXciSplitter, SWT.BORDER);
		isSplit.setEditable(false);
		isSplit.setBounds(216, 155, 76, 21);

		parts = new Text(shlXciSplitter, SWT.BORDER);
		parts.setEditable(false);
		parts.setBounds(216, 185, 76, 21);

		btnTrim = new Button(shlXciSplitter, SWT.NONE);
		btnTrim.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				model.setTargetFile(targetFile.getText());
				model.trim(createProgressBarUpdater());
			}
		});
		btnTrim.setBounds(329, 151, 76, 79);
		btnTrim.setText("Trim");

		btnSplitAndTrim = new Button(shlXciSplitter, SWT.NONE);
		btnSplitAndTrim.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				model.setTargetFile(targetFile.getText());
				model.splitAndTrim(createProgressBarUpdater());
			}
		});
		btnSplitAndTrim.setBounds(411, 151, 75, 79);
		btnSplitAndTrim.setText("Split && Trim");

		Menu mainMenu = new Menu(shlXciSplitter, SWT.BAR);
		shlXciSplitter.setMenuBar(mainMenu);

		MenuItem mntmFile = new MenuItem(mainMenu, SWT.CASCADE);
		mntmFile.setText("File");

		menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		mntmBatchProcessing = new MenuItem(menu_1, SWT.NONE);
		mntmBatchProcessing.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				batchModeDialog.open();
			}
		});
		mntmBatchProcessing.setText("Batch processing...");

		mntmExit = new MenuItem(menu_1, SWT.NONE);
		mntmExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlXciSplitter.close();
			}
		});
		mntmExit.setText("Exit");

		btnMerge = new Button(shlXciSplitter, SWT.NONE);
		btnMerge.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				model.setTargetFile(targetFile.getText());
				model.merge(createProgressBarUpdater());
			}
		});
		btnMerge.setBounds(492, 151, 75, 79);
		btnMerge.setText("Merge");

		progressBarWorkflowProgress = new ProgressBar(shlXciSplitter, SWT.NONE);
		progressBarWorkflowProgress.setBounds(10, 287, 587, 17);

		lblWorkflowStep = new Label(shlXciSplitter, SWT.NONE);
		lblWorkflowStep.setBounds(10, 266, 587, 15);

		Button btnSX = new Button(shlXciSplitter, SWT.RADIO);
		btnSX.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnSX.getSelection()) {
					model.setSplitMethod(SplitMethodEnum.SX);
				}
			}
		});
		btnSX.setBounds(91, 342, 39, 16);
		btnSX.setText("SX");

		Button btnNX = new Button(shlXciSplitter, SWT.RADIO);
		btnNX.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnNX.getSelection()) {
					model.setSplitMethod(SplitMethodEnum.NX);
				}
			}
		});
		btnNX.setBounds(136, 342, 39, 16);
		btnNX.setText("NX");

		Label lblSplitMethod = new Label(shlXciSplitter, SWT.NONE);
		lblSplitMethod.setBounds(10, 342, 75, 15);
		lblSplitMethod.setText("Split method");

		SplitMethodEnum sm = model.getSplitMethod();
		switch (sm) {
		case NX:
			btnSX.setSelection(false);
			btnNX.setSelection(true);
		case SX:
			btnNX.setSelection(false);
			btnSX.setSelection(true);
		}
	}

	private void updateInformationFields() {
		cartSize.setText(model.getCartSizeString());
		dataSize.setText(model.getDataSizeString());
		xciSize.setText(model.getFullFileSizeString());
		isSplit.setText(model.getIsSplitString());
		parts.setText(model.getAmountOfPartsString());
	}

	private ProgressBarUpdater createProgressBarUpdater() {
		return new ProgressBarUpdater(lblWorkflowStep, progressBarWorkflowProgress, btnSplitAndTrim, btnMerge, btnTrim);
	}
}
