package org.insanedevelopment.nx.xci.cutter.backend.batch;

import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.tuple.Pair;
import org.insanedevelopment.nx.xci.cutter.backend.WorkflowStepPercentageObserver;
import org.insanedevelopment.nx.xci.cutter.backend.XciFileMerger;
import org.insanedevelopment.nx.xci.cutter.backend.XciFileSplitter;
import org.insanedevelopment.nx.xci.cutter.backend.model.XciFileInformation;

public class BatchProcessor {

	public static void splitAndTrim(List<Pair<XciFileInformation, String>> files, BatchProgressUpdater batchProgressUpdater,
			WorkflowStepPercentageObserver workflowObserver) {

		Consumer<Pair<XciFileInformation, String>> action = pair -> XciFileSplitter.splitAndTrimFile(pair.getLeft(), pair.getRight(), workflowObserver);
		executeForEachFile(files, batchProgressUpdater, action);

	}

	public static void merge(List<Pair<XciFileInformation, String>> files, BatchProgressUpdater batchProgressUpdater,
			WorkflowStepPercentageObserver workflowObserver) {

		Consumer<Pair<XciFileInformation, String>> action = pair -> XciFileMerger.mergeSplitFiles(pair.getLeft(), pair.getRight(), workflowObserver);
		executeForEachFile(files, batchProgressUpdater, action);
	}

	public static void trim(List<Pair<XciFileInformation, String>> files, BatchProgressUpdater batchProgressUpdater, WorkflowStepPercentageObserver workflowObserver) {

		Consumer<Pair<XciFileInformation, String>> action = pair -> XciFileSplitter.trimFile(pair.getLeft(), pair.getRight(), workflowObserver);
		executeForEachFile(files, batchProgressUpdater, action);
	}

	private static void executeForEachFile(List<Pair<XciFileInformation, String>> files, BatchProgressUpdater batchProgressUpdater,
			Consumer<Pair<XciFileInformation, String>> action) {

		int totalFiles = files.size();
		int currentFileCount = 1;
		for (Pair<XciFileInformation, String> pair : files) {
			batchProgressUpdater.updateBatchProgress(pair.getRight(), (double)  currentFileCount / (double) totalFiles * 100.0d, currentFileCount++, totalFiles);
			action.accept(pair);
		}
	}

}
