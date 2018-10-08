package org.insanedevelopment.nx.xci.cutter.frontend;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.insanedevelopment.nx.xci.cutter.backend.WorkflowStepPercentageObserver;
import org.insanedevelopment.nx.xci.cutter.backend.XciFileMerger;
import org.insanedevelopment.nx.xci.cutter.backend.XciFileSplitter;
import org.insanedevelopment.nx.xci.cutter.backend.model.SwitchGameFileInformation;

public class GuiModelSingleFile {

	private ExecutorService executor = Executors.newFixedThreadPool(1,  new ThreadFactory() {
        public Thread newThread(Runnable r) {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        }
    });

	private SwitchGameFileInformation gameFileInformation;
	private String sourceFile;
	private String targetFile;

	public GuiModelSingleFile() {
		super();
	}

	public String getFullFileSizeString() {
		return byteCountToDisplaySize(gameFileInformation.getFullFileSizeInBytes());
	}

	public String getIsSplitString() {
		if (gameFileInformation == null) {
			return "";
		}
		return gameFileInformation.isSplit() ? "yes" : "no";
	}

	public String getAmountOfPartsString() {
		if (gameFileInformation == null) {
			return "";
		}
		return Integer.toString(gameFileInformation.getAllFileNames().size());
	}

	public String getDataSizeString() {
		if (gameFileInformation == null) {
			return "";
		}
		return byteCountToDisplaySize(gameFileInformation.getDataSizeInBytes());
	}

	public String getCartSizeString() {
		if (gameFileInformation == null) {
			return "";
		}
		return byteCountToDisplaySize(gameFileInformation.getCartSizeInBytes());
	}

	private String byteCountToDisplaySize(long cartSizeInBytes) {
		return byteCountToDisplaySize(BigInteger.valueOf(cartSizeInBytes));
	}

	private String byteCountToDisplaySize(BigInteger size) {
		return String.valueOf(size.divide(FileUtils.ONE_MB_BI)) + " MB";
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
		this.gameFileInformation = ModelHelper.getFileInformation(sourceFile);
	}

	public String getTargetFile() {
		return targetFile;
	}

	public void setTargetFile(String targetFile) {
		this.targetFile = targetFile;
	}

	public String getTargetFileNameProposal() {
		if (StringUtils.trimToNull(sourceFile) == null || gameFileInformation == null) {
			return null;
		}
		String result = gameFileInformation.getTargetFileNameProposal("-output");
		return result;
	}

	public void splitAndTrim(WorkflowStepPercentageObserver progressBarUpdater) {
		executor.submit(() -> XciFileSplitter.splitAndTrimFile(gameFileInformation, targetFile, progressBarUpdater));
	}

	public void merge(WorkflowStepPercentageObserver progressBarUpdater) {
		executor.submit(() -> XciFileMerger.mergeSplitFiles(gameFileInformation, targetFile, progressBarUpdater));
	}

	public void trim(WorkflowStepPercentageObserver progressBarUpdater) {
		executor.submit(() -> XciFileSplitter.trimFile(gameFileInformation, targetFile, progressBarUpdater));
	}

}
