package org.insanedevelopment.nx.xci.cutter.backend.batch;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.insanedevelopment.nx.xci.cutter.backend.WorkflowStepPercentageObserver;
import org.insanedevelopment.nx.xci.cutter.backend.XciFileMerger;
import org.insanedevelopment.nx.xci.cutter.backend.XciFileSplitter;
import org.insanedevelopment.nx.xci.cutter.backend.model.XciFileInformation;

public class BatchProcessor {

	public static void splitAndTrim(List<Pair<XciFileInformation, String>> files, BatchProgressUpdater batchProgressUpdater,
			WorkflowStepPercentageObserver workflowObserver, boolean deleteProcessed) {

		Consumer<Pair<XciFileInformation, String>> action = pair -> XciFileSplitter.splitAndTrimFile(pair.getLeft(), pair.getRight(), workflowObserver);
		executeForEachFile(files, batchProgressUpdater, action, deleteProcessed);

	}

	public static void merge(List<Pair<XciFileInformation, String>> files, BatchProgressUpdater batchProgressUpdater,
			WorkflowStepPercentageObserver workflowObserver, boolean deleteProcessed) {

		Consumer<Pair<XciFileInformation, String>> action = pair -> XciFileMerger.mergeSplitFiles(pair.getLeft(), pair.getRight(), workflowObserver);
		executeForEachFile(files, batchProgressUpdater, action, deleteProcessed);
	}

	public static void trim(List<Pair<XciFileInformation, String>> files, BatchProgressUpdater batchProgressUpdater,
			WorkflowStepPercentageObserver workflowObserver, boolean deleteProcessed) {

		Consumer<Pair<XciFileInformation, String>> action = pair -> XciFileSplitter.trimFile(pair.getLeft(), pair.getRight(), workflowObserver);
		executeForEachFile(files, batchProgressUpdater, action, deleteProcessed);
	}

	private static void executeForEachFile(List<Pair<XciFileInformation, String>> files, BatchProgressUpdater batchProgressUpdater,
			Consumer<Pair<XciFileInformation, String>> action, boolean deleteProcessed) {

		int totalFiles = files.size();
		int currentFileCount = 1;
		for (Pair<XciFileInformation, String> pair : files) {
			batchProgressUpdater.updateBatchProgress(pair.getLeft().getMainFileName(), (double) currentFileCount / (double) totalFiles * 100.0d,
					currentFileCount++, totalFiles);
			action.accept(pair);
			if (deleteProcessed) {
				deleteSource(pair.getLeft());
			}
		}
	}

	private static void deleteSource(XciFileInformation fileInformation) {
		List<String> sourceFiles = fileInformation.getAllFileNames();
		for (String sourceFile : sourceFiles) {
			FileUtils.deleteQuietly(new File(sourceFile));
		}
	}

}
