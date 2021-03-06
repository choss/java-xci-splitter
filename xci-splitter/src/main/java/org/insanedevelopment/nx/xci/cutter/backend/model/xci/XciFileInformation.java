package org.insanedevelopment.nx.xci.cutter.backend.model.xci;

import org.insanedevelopment.nx.xci.cutter.backend.model.AbstractSwitchGameFileInformation;
import org.insanedevelopment.nx.xci.cutter.backend.model.splitmethods.SplitMethodEnum;

public class XciFileInformation extends AbstractSwitchGameFileInformation {

	private XciHeaderInformation headerInformation;

	public XciFileInformation(String firstFileName, SplitMethodEnum splitMethodEnum) {
		super(firstFileName, splitMethodEnum.buildSplitMethod("xci"));
		headerInformation = XciHeaderInformation.readFromFile(firstFileName);
	}

	@Override
	public long getDataSizeInBytes() {
		return headerInformation.getDataFileSizeInBytes().longValue();
	}

	@Override
	public long getCartSizeInBytes() {
		return headerInformation.getGamecardSize().getCartSizeInBytes();
	}

	@Override
	public String toString() {
		return "XciFileInformation [headerInformation=" + headerInformation + ", getFullFileSizeInBytes()="
				+ getFullFileSizeInBytes() + ", isSplit()=" + isSplit() + "]";
	}

}
