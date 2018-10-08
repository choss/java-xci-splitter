package org.insanedevelopment.nx.xci.cutter.frontend;

import org.insanedevelopment.nx.xci.cutter.backend.model.NspFileInformation;
import org.insanedevelopment.nx.xci.cutter.backend.model.SwitchGameFileInformation;
import org.insanedevelopment.nx.xci.cutter.backend.model.XciFileInformation;

public class ModelHelper {

	public static SwitchGameFileInformation getFileInformation(String sourceFile) {
		if (sourceFile.endsWith("nsp") || sourceFile.endsWith("0")) {
			return new NspFileInformation(sourceFile);
		}
		return new XciFileInformation(sourceFile);
	}

}
