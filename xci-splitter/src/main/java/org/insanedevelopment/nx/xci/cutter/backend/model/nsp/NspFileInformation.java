package org.insanedevelopment.nx.xci.cutter.backend.model.nsp;

import org.apache.commons.io.FileUtils;
import org.insanedevelopment.nx.xci.cutter.backend.model.AbstractSwitchGameFileInformation;
import org.insanedevelopment.nx.xci.cutter.backend.model.splitmethods.SplitMethodEnum;

public class NspFileInformation extends AbstractSwitchGameFileInformation {

	private static long SPLIT_FILE_SIZE = (4 * FileUtils.ONE_GB) - (64 * FileUtils.ONE_KB);

	public NspFileInformation(String firstFileName, SplitMethodEnum splitMethodEnum) {
		super(firstFileName, splitMethodEnum.buildSplitMethod("nsp"));
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
		// returning own splitsize here, because it works. It is the same as for the tinfoil method
		return SPLIT_FILE_SIZE;
	}

}
