package org.insanedevelopment.nx.xci.cutter.backend.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.insanedevelopment.nx.xci.cutter.backend.model.splitmethods.SplitMethod;
import org.insanedevelopment.nx.xci.cutter.backend.model.splitmethods.SxSplitMethod;

public abstract class AbstractSwitchGameFileInformation implements SwitchGameFileInformation {

	// list of (Name, Size) combined with laziness to create a proper object
	private List<Pair<String, Long>> physicalFileSizes = new ArrayList<>();

	private SplitMethod splitMethod;

	public AbstractSwitchGameFileInformation(String firstFileName, String extension) {
		this.splitMethod = new SxSplitMethod(extension);
		initializeSizesIncludingSplitFiles(firstFileName);
	}

	private Long getFileSize(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			return file.length();
		}
		return -1L;
	}

	private void initializeSizesIncludingSplitFiles(String mainFileName) {
		List<String> allFileNames = splitMethod.getAllSplitFiles(mainFileName);
		physicalFileSizes = allFileNames.stream().map(f -> Pair.of(f, getFileSize(f))).collect(Collectors.toList());
	}

	@Override
	public boolean isSplit() {
		return physicalFileSizes.size() > 1;
	}

	@Override
	public List<String> getAllFileNames() {
		return physicalFileSizes.stream().map(p -> p.getLeft()).collect(Collectors.toList());
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
	public String getMainFileName() {
		return physicalFileSizes.get(0).getLeft();
	}

	@Override
	public long getSplitSize() {
		return splitMethod.getSplitSizeRecommendation();
	}

	@Override
	public String getTargetFileNameProposal(String suffix) {
		String sourceFile = getMainFileName();
		boolean isOutputSplitFile = getDataSizeInBytes() > getSplitSize() && !isSplit();
		String baseOutputFileName = FilenameUtils.getFullPath(sourceFile) + FilenameUtils.getBaseName(sourceFile) + suffix;
		return isOutputSplitFile ? splitMethod.getOutputFileNameSplitting(baseOutputFileName, 0) : splitMethod.getOutputFileNameNoSplitting(baseOutputFileName);
	}

	@Override
	public File createOutputFile(String baseOutputFileName, int counter, long chunkSize) {
		if (counter == 0 && this.getDataSizeInBytes() <= chunkSize) {
			return new File(splitMethod.getOutputFileNameNoSplitting(baseOutputFileName));
		} else {
			return new File(splitMethod.getOutputFileNameSplitting(baseOutputFileName, counter));
		}
	}
}
