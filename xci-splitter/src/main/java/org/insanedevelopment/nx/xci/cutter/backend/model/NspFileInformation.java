package org.insanedevelopment.nx.xci.cutter.backend.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.Pair;

public class NspFileInformation implements SwitchGameFileInformation {

	private static long SPLIT_FILE_SIZE = (4 * FileUtils.ONE_GB) - (64 * FileUtils.ONE_KB);

	private List<Pair<String, Long>> physicalFileSizes = new ArrayList<>();

	public NspFileInformation(String firstFileName) {

		Pair<String, Long> firstFile = Pair.of(firstFileName, getFileSize(firstFileName));
		physicalFileSizes.add(firstFile);
		initializeForSplitFiles();
	}

	private void initializeForSplitFiles() {
		String mainFileName = getMainFileName();
		if (mainFileName.endsWith(".nsp")) {
			return;
		}

		String baseFileName = FilenameUtils.getFullPath(mainFileName);
		int counter = 1;

		String nextFileName = baseFileName + "/" + counter;
		while (getFileSize(nextFileName) != -1) {
			physicalFileSizes.add(Pair.of(nextFileName, getFileSize(nextFileName)));
			counter++;
			nextFileName = baseFileName + "/" + counter;
		}
	}

	private Long getFileSize(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			return file.length();
		}
		return -1L;
	}

	@Override
	public String getMainFileName() {
		return physicalFileSizes.get(0).getLeft();
	}

	@Override
	public long getFullFileSizeInBytes() {
		long result = 0;
		for (Pair<String, Long> pair : physicalFileSizes) {
			result += pair.getRight().longValue();
		}
		return result;
	}

	@Override
	public boolean isSplit() {
		if (physicalFileSizes.size() == 0) {
			return false;
		}
		return physicalFileSizes.size() > 1 || !physicalFileSizes.get(0).getLeft().endsWith(".nsp");
	}

	@Override
	public List<String> getAllFileNames() {
		return physicalFileSizes.stream().map(p -> p.getLeft()).collect(Collectors.toList());
	}

	@Override
	public long getDataSizeInBytes() {
		return getFullFileSizeInBytes();
	}

	@Override
	public long getCartSizeInBytes() {
		return 0;
	}

	@Override
	public long getSplitSize() {
		return SPLIT_FILE_SIZE;
	}

	@Override
	public File createOutputFile(String baseOutputFileName, int counter, long chunkSize) {
		if (counter == 0 && this.getDataSizeInBytes() <= chunkSize) {
			return new File(baseOutputFileName + ".nsp");
		} else {
			return new File(baseOutputFileName + ".nsp/" + counter);
		}
	}

	@Override
	public String getDefaultExtension() {
		return ".nsp";
	}

}
