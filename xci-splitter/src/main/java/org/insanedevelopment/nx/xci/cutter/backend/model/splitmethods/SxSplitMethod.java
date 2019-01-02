package org.insanedevelopment.nx.xci.cutter.backend.model.splitmethods;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

public class SxSplitMethod implements SplitMethod {

	private final String splitFileExtensionPrefix;
	private final String firstSplitFileExtension;
	private final String fileExtension;

	public SxSplitMethod() {
		this(".xci");
	}

	public SxSplitMethod(String extension) {
		this.fileExtension = StringUtils.prependIfMissing(extension, ".");
		this.splitFileExtensionPrefix = StringUtils.substring(fileExtension, 0, fileExtension.length() - 1);
		this.firstSplitFileExtension = splitFileExtensionPrefix + "0";
	}

	@Override
	public List<String> getAllSplitFiles(String mainFileName) {

		if (!mainFileName.endsWith(firstSplitFileExtension)) {
			return Arrays.asList(mainFileName);
		}

		String baseFileName = FilenameUtils.getFullPath(mainFileName) + FilenameUtils.getBaseName(mainFileName);
		int counter = 0;

		String nextFileName = baseFileName + splitFileExtensionPrefix + counter;
		List<String> result = new ArrayList<>();
		while (isFilePresent(nextFileName)) {
			result.add(nextFileName);
			counter++;
			nextFileName = baseFileName + splitFileExtensionPrefix + counter;
		}
		return result;
	}

	private boolean isFilePresent(String fileName) {
		File file = new File(fileName);
		return (file.exists() && file.isFile());
	}

	@Override
	public String getOutputFileNameSplitting(String baseOutputFileName, int counter) {
		return baseOutputFileName + splitFileExtensionPrefix + counter;
	}

	@Override
	public String getOutputFileNameNoSplitting(String baseOutputFileName) {
		return baseOutputFileName + fileExtension;

	}

}