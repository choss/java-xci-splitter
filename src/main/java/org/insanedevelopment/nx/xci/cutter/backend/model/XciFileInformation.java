package org.insanedevelopment.nx.xci.cutter.backend.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.Pair;

public class XciFileInformation {

	private List<Pair<String, Long>> physicalFileSizes = new ArrayList<>();
	private XciHeaderInformation headerInformation;

	public XciFileInformation(String firstFileName) {

		Pair<String, Long> firstFile = Pair.of(firstFileName, getFileSize(firstFileName));
		physicalFileSizes.add(firstFile);

		headerInformation = XciHeaderInformation.readFromFile(firstFileName);
		initializeForSplitFiles();
	}

	private Long getFileSize(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			return file.length();
		}
		return -1L;
	}

	public String getMainFileName() {
		return physicalFileSizes.get(0).getLeft();
	}

	private void initializeForSplitFiles() {
		String mainFileName = getMainFileName();

		if (!mainFileName.endsWith(".xc0")) {
			return;
		}

		String baseFileName = FilenameUtils.getFullPath(mainFileName) + FilenameUtils.getBaseName(mainFileName);
		int counter = 1;

		String nextFileName = baseFileName + ".xc" + counter;
		while (getFileSize(nextFileName) != -1) {
			physicalFileSizes.add(Pair.of(nextFileName, getFileSize(nextFileName)));
			counter++;
			nextFileName = baseFileName + ".xc" + counter;
		}

	}

	public long getFullFileSizeInBytes() {
		long result = 0;
		for (Pair<String, Long> pair : physicalFileSizes) {
			result += pair.getRight().longValue();
		}
		return result;
	}


	public boolean isSplit() {
		return physicalFileSizes.size() > 1;
	}

	public List<String> getAllFileNames() {
		return physicalFileSizes.stream().map(p -> p.getLeft()).collect(Collectors.toList());
	}

	public long getDataSizeInBytes() {
		return headerInformation.getDataFileSizeInBytes().longValue();
	}

	public long getCartSizeInBytes() {
		return headerInformation.getGamecardSize().getCartSizeInBytes();
	}

	@Override
	public String toString() {
		return "XciFileInformation [physicalFileSizes=" + physicalFileSizes + ", headerInformation=" + headerInformation + ", getFullFileSizeInBytes()="
				+ getFullFileSizeInBytes() + ", isSplit()=" + isSplit() + "]";
	}

}
