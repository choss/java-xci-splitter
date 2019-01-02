package org.insanedevelopment.nx.xci.cutter.frontend;

import org.apache.commons.lang3.StringUtils;
import org.insanedevelopment.nx.xci.cutter.backend.FileExtensionUtils;
import org.insanedevelopment.nx.xci.cutter.backend.model.SwitchGameFileInformation;
import org.insanedevelopment.nx.xci.cutter.backend.model.nsp.NspFileInformation;
import org.insanedevelopment.nx.xci.cutter.backend.model.xci.XciFileInformation;

public class ModelHelper {

	public static SwitchGameFileInformation getFileInformation(String sourceFile) {
		if (StringUtils.endsWithAny(sourceFile.toLowerCase(), FileExtensionUtils.NSP_EXTENSIONS)) {
			return new NspFileInformation(sourceFile);
		}
		return new XciFileInformation(sourceFile);
	}

}
