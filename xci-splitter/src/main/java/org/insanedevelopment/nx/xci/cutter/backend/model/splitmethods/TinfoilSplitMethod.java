package org.insanedevelopment.nx.xci.cutter.backend.model.splitmethods;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

public class TinfoilSplitMethod implements SplitMethod {

	private final String fileExtension;

	public TinfoilSplitMethod() {
		this(".nsp");
	}

	public TinfoilSplitMethod(String extension) {
		this.fileExtension = StringUtils.prependIfMissing(extension, ".");
	}

	@Override
	public List<String> getAllSplitFiles(String mainFileName) {
		if (mainFileName.endsWith(fileExtension)) {
			return Arrays.asList(mainFileName);
		}

		List<String> result = new ArrayList<>();

		String baseFileName = FilenameUtils.getFullPath(mainFileName);
		int counter = 0;

		String nextFileName = baseFileName + "/" + StringUtils.leftPad(Integer.toString(counter), 2, '0');
		while (isFilePresent(nextFileName)) {
			result.add(nextFileName);
			counter++;
			nextFileName = baseFileName + "/" + StringUtils.leftPad(Integer.toString(counter), 2, '0');
		}
		return result;

	}

	private boolean isFilePresent(String fileName) {
		File file = new File(fileName);
		return (file.exists() && file.isFile());
	}

	@Override
	public String getOutputFileNameNoSplitting(String baseOutputFileName) {
		return baseOutputFileName + fileExtension;
	}

	@Override
	public String getOutputFileNameSplitting(String baseOutputFileName, int counter) {
		return baseOutputFileName + fileExtension + "/" + StringUtils.leftPad(Integer.toString(counter), 2, '0');
	}

}
