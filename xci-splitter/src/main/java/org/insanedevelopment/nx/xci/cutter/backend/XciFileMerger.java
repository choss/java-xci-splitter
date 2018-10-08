package org.insanedevelopment.nx.xci.cutter.backend;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ObservableInputStream;
import org.apache.commons.io.input.PercentageCalculatingInputStreamObserver;
import org.insanedevelopment.nx.xci.cutter.backend.model.SwitchGameFileInformation;

public class XciFileMerger {

	private static final int PADDING_PROGESS_UPDATE_CHUNK = 200;

	public static void mergeSplitFiles(SwitchGameFileInformation source, String target, WorkflowStepPercentageObserver calleeObserver) {
		try {
			mergeSplitFilesInternal(source, target, calleeObserver);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static void mergeSplitFilesInternal(SwitchGameFileInformation source, String target, WorkflowStepPercentageObserver calleeObserver) throws IOException {
		if (source.getCartSizeInBytes() == 0) {
			return;
		}
		File targetFile = new File(target);
		FileUtils.forceMkdirParent(targetFile);
		try (OutputStream outputStream = IOUtils.buffer(FileUtils.openOutputStream(targetFile))) {
			calleeObserver.setWorkflowStep(WorkflowStep.MERGING);
			PercentageCalculatingInputStreamObserver observer = new PercentageCalculatingInputStreamObserver(source.getFullFileSizeInBytes(), calleeObserver);

			for (String fileName : source.getAllFileNames()) {
				try (ObservableInputStream inputStream = new ObservableInputStream(FileUtils.openInputStream(new File(fileName)))) {
					inputStream.add(observer);
					IOUtils.copyLarge(inputStream, outputStream);
				}
			}

			calleeObserver.setWorkflowStep(WorkflowStep.PADDING);
			long amountOfPadding = source.getCartSizeInBytes() - source.getDataSizeInBytes();
			calleeObserver.updatePercentage(0, 0, amountOfPadding);
			long bytesPadded = 0;
			final long updateProgessThreshold = (amountOfPadding / PADDING_PROGESS_UPDATE_CHUNK) + 1; // adding 1 so result is never 0
			for (long i = source.getDataSizeInBytes(); i < source.getCartSizeInBytes(); i++) {
				outputStream.write(0xFF);
				bytesPadded++;
				if (bytesPadded % updateProgessThreshold == 0) {
					calleeObserver.updatePercentage((double) bytesPadded / (double) amountOfPadding * 100.0d, bytesPadded, amountOfPadding);
				}
			}
			calleeObserver.updatePercentage(100d, bytesPadded, amountOfPadding);
		}
		calleeObserver.setWorkflowStep(WorkflowStep.DONE);
	}

}
