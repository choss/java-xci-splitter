package org.insanedevelopment.nx.xci.cutter.backend.model.splitmethods;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

public class NintendoSwitchSplitMethod implements SplitMethod {
	private static long SPLIT_FILE_SIZE = (4 * FileUtils.ONE_GB) - (64 * FileUtils.ONE_KB);

	private final String fileExtension;

	public NintendoSwitchSplitMethod() {
		this(".nsp");
	}

	public NintendoSwitchSplitMethod(String extension) {
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

	@Override
	public long getSplitSizeRecommendation() {
		return SPLIT_FILE_SIZE;
	}

}
