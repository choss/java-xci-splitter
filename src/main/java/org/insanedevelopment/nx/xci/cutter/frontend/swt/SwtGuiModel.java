package org.insanedevelopment.nx.xci.cutter.frontend.swt;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.insanedevelopment.nx.xci.cutter.backend.XciFileMerger;
import org.insanedevelopment.nx.xci.cutter.backend.XciFileSplitter;
import org.insanedevelopment.nx.xci.cutter.backend.model.XciFileInformation;

public class SwtGuiModel {

	private ExecutorService executor = Executors.newFixedThreadPool(1,  new ThreadFactory() {
        public Thread newThread(Runnable r) {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        }
    });

	private XciFileInformation xciFileInformation;
	private String sourceFile;
	private String targetFile;

	public SwtGuiModel() {
		super();
	}

	public String getFullFileSizeString() {
		return byteCountToDisplaySize(xciFileInformation.getFullFileSizeInBytes());
	}

	public String getIsSplitString() {
		if (xciFileInformation == null) {
			return "";
		}
		return xciFileInformation.isSplit() ? "yes" : "no";
	}

	public String getAmountOfPartsString() {
		if (xciFileInformation == null) {
			return "";
		}
		return Integer.toString(xciFileInformation.getAllFileNames().size());
	}

	public String getDataSizeString() {
		if (xciFileInformation == null) {
			return "";
		}
		return byteCountToDisplaySize(xciFileInformation.getDataSizeInBytes());
	}

	public String getCartSizeString() {
		if (xciFileInformation == null) {
			return "";
		}
		return byteCountToDisplaySize(xciFileInformation.getCartSizeInBytes());
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
		this.xciFileInformation = new XciFileInformation(sourceFile);
	}

	public String getTargetFile() {
		return targetFile;
	}

	public void setTargetFile(String targetFile) {
		this.targetFile = targetFile;
	}

	public String getTargetFileNameProposal() {
		if (StringUtils.trimToNull(sourceFile) == null) {
			return null;
		}
		String result = FilenameUtils.getFullPath(sourceFile) + FilenameUtils.getBaseName(sourceFile) + "-output.xc0";
		return result;
	}

	public void splitAndTrim(ProgressBarUpdater progressBarUpdater) {
		executor.submit(() -> XciFileSplitter.splitAndTrimFile(xciFileInformation, targetFile, progressBarUpdater));
	}

	public void merge(ProgressBarUpdater progressBarUpdater) {
		executor.submit(() -> XciFileMerger.mergeSplitFiles(xciFileInformation, targetFile, progressBarUpdater));
	}

	public void trim(ProgressBarUpdater progressBarUpdater) {
		executor.submit(() -> XciFileSplitter.trimFile(xciFileInformation, targetFile, progressBarUpdater));
	}

}
