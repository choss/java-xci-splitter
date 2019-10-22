package org.insanedevelopment.nx.xci.cutter.frontend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.insanedevelopment.nx.xci.cutter.backend.WorkflowStepPercentageObserver;
import org.insanedevelopment.nx.xci.cutter.backend.batch.BatchHelper;
import org.insanedevelopment.nx.xci.cutter.backend.batch.BatchProcessor;
import org.insanedevelopment.nx.xci.cutter.backend.batch.BatchProgressUpdater;
import org.insanedevelopment.nx.xci.cutter.backend.model.SwitchGameFileInformation;
import org.insanedevelopment.nx.xci.cutter.backend.model.splitmethods.SplitMethodEnum;

public class GuiModelBatchMode {

	private List<String> inputFileNames = new ArrayList<>();
	private boolean useCustomOutputDirectory = false;
	private String outputDirectory = "";

	private ExecutorService executor = Executors.newFixedThreadPool(1, new ThreadFactory() {
		public Thread newThread(Runnable r) {
			Thread t = Executors.defaultThreadFactory().newThread(r);
			t.setDaemon(true);
			return t;
		}
	});

	public void setFiles(String[] fileNames) {
		inputFileNames.clear();
		inputFileNames.addAll(Arrays.asList(fileNames));
	}

	public void splitAndTrim(BatchProgressUpdater batchProgressUpdater, WorkflowStepPercentageObserver progressBarUpdater, boolean deleteProcessed) {
		List<Pair<SwitchGameFileInformation, String>> files = generateFiles(true);
		executor.submit(() -> BatchProcessor.splitAndTrim(files, batchProgressUpdater, progressBarUpdater, deleteProcessed));
	}

	public void merge(BatchProgressUpdater batchProgressUpdater, WorkflowStepPercentageObserver progressBarUpdater, boolean deleteProcessed) {
		List<Pair<SwitchGameFileInformation, String>> files = generateFiles(false);
		executor.submit(() -> BatchProcessor.merge(files, batchProgressUpdater, progressBarUpdater, deleteProcessed));
	}

	public void trim(BatchProgressUpdater batchProgressUpdater, WorkflowStepPercentageObserver progressBarUpdater, boolean deleteProcessed) {
		List<Pair<SwitchGameFileInformation, String>> files = generateFiles(true);
		executor.submit(() -> BatchProcessor.trim(files, batchProgressUpdater, progressBarUpdater, deleteProcessed));
	}

	private List<Pair<SwitchGameFileInformation, String>> generateFiles(boolean isTrim) {
		List<Pair<SwitchGameFileInformation, String>> result = new ArrayList<>(inputFileNames.size());
		for (String fileName : inputFileNames) {
			Pair<SwitchGameFileInformation, String> informationForSourceFile = BatchHelper.generateInformationForSourceFile(fileName, isTrim, SplitMethodEnum.SX);
			informationForSourceFile = adjustInformationIfneeded(informationForSourceFile);
			result.add(informationForSourceFile);
		}
		return result;
	}

	private Pair<SwitchGameFileInformation, String> adjustInformationIfneeded(Pair<SwitchGameFileInformation, String> informationForSourceFile) {
		if (!useCustomOutputDirectory) {
			return informationForSourceFile;
		}
		
		String fullFileName = informationForSourceFile.getLeft().getTargetFileNameProposal("");
		String fileName = FilenameUtils.getName(fullFileName);
		String outputFileName = FilenameUtils.concat(outputDirectory, fileName);
		return Pair.of(informationForSourceFile.getLeft(), outputFileName);
	}

	public void setTargetDirectory(String file) {
		this.outputDirectory = file;
	}

	public void setUseCustomOutputDirectory(boolean selection) {
		this.useCustomOutputDirectory = selection;
	}

}
