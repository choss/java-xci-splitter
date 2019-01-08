package org.insanedevelopment.nx.xci.cutter.backend;

import org.apache.commons.lang3.ArrayUtils;

public class FileExtensionUtils {

	public static final String[] NSP_EXTENSIONS = { ".nsp", ".ns0" };
	public static final String[] XCI_EXTENSIONS = { ".xci", ".xc0" };
	public static final String[] ALL_EXTENSIONS = ArrayUtils.addAll(XCI_EXTENSIONS, NSP_EXTENSIONS);
	public static final String[] SELECT_FILE_FILTER = new String[] { "*.xci;*.xc0;*.nsp;*.ns0", "*.xci", "*.xc0", "*.nsp", "*.ns0"};
	public static final String[] SAVE_FILE_FILTER = new String[] { "*.xc0;*.xci;*.nsp;*.ns0","*.xci", "*.xc0", "*.nsp", "*.ns0" };
	public static final String[] NSP_FILE_DETECTION_STRING = ArrayUtils.addAll(NSP_EXTENSIONS, ".nsp/00", ".nsp\\00");

	private FileExtensionUtils() {
		// prevent instantiation
	}

}
