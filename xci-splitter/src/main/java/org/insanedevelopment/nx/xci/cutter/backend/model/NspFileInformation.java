package org.insanedevelopment.nx.xci.cutter.backend.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
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

		String nextFileName = baseFileName + "/" + StringUtils.leftPad(Integer.toString(counter), 2, '0');
		while (getFileSize(nextFileName) != -1) {
			physicalFileSizes.add(Pair.of(nextFileName, getFileSize(nextFileName)));
			counter++;
			nextFileName = baseFileName + "/" + StringUtils.leftPad(Integer.toString(counter), 2, '0');
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
		return getFullFileSizeInBytes();
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
			return new File(baseOutputFileName + ".nsp/" + StringUtils.leftPad(Integer.toString(counter), 2, '0'));
		}
	}

	@Override
	public String getTargetFileNameProposal(String suffix) {
		String sourceFile = getMainFileName();
		if (!isSplit()) {
			return FilenameUtils.getFullPath(sourceFile) + FilenameUtils.getBaseName(sourceFile) + suffix + ".nsp";
		} else {
			sourceFile = StringUtils.removeEnd(FilenameUtils.getFullPath(sourceFile), "\\");
			return FilenameUtils.getFullPath(sourceFile) + FilenameUtils.getBaseName(sourceFile) + suffix + ".nsp";
		}
	}

}
