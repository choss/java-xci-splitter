package org.insanedevelopment.nx.xci.cutter.frontend;

import org.apache.commons.lang3.StringUtils;
import org.insanedevelopment.nx.xci.cutter.backend.FileExtensionUtils;
import org.insanedevelopment.nx.xci.cutter.backend.model.SwitchGameFileInformation;
import org.insanedevelopment.nx.xci.cutter.backend.model.nsp.NspFileInformation;
import org.insanedevelopment.nx.xci.cutter.backend.model.splitmethods.SplitMethodEnum;
import org.insanedevelopment.nx.xci.cutter.backend.model.xci.XciFileInformation;

public class ModelHelper {

	public static SwitchGameFileInformation getFileInformation(String sourceFile, SplitMethodEnum splitMethod) {
		if (StringUtils.endsWithAny(sourceFile.toLowerCase(), FileExtensionUtils.NSP_FILE_DETECTION_STRING)) {
			return new NspFileInformation(sourceFile, splitMethod);
		}
		return new XciFileInformation(sourceFile, splitMethod);
	}

}
