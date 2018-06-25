package org.insanedevelopment.nx.xci.cutter.backend.ui.swt;

import java.math.BigInteger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.internal.databinding.provisional.swt.SWTUtil;
import org.insanedevelopment.nx.xci.cutter.backend.XciFileMerger;
import org.insanedevelopment.nx.xci.cutter.backend.XciFileSplitter;
import org.insanedevelopment.nx.xci.cutter.backend.model.XciFileInformation;

public class SwtGuiModel {

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
	
	public void splitAndTrim() {
//		XciFileSplitter.splitAndTrimFile(xciFileInformation, targetFile);
	}
	
	public void merge() {
//		XciFileMerger.mergeSplitFiles(xciFileInformation, targetFile);
	}
	
}
