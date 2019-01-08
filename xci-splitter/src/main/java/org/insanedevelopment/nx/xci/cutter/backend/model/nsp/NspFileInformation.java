package org.insanedevelopment.nx.xci.cutter.backend.model.nsp;

import org.apache.commons.io.FileUtils;
import org.insanedevelopment.nx.xci.cutter.backend.model.AbstractSwitchGameFileInformation;

public class NspFileInformation extends AbstractSwitchGameFileInformation {

	private static long SPLIT_FILE_SIZE = (4 * FileUtils.ONE_GB) - (64 * FileUtils.ONE_KB);

	public NspFileInformation(String firstFileName) {
		super(firstFileName, "nsp");
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
