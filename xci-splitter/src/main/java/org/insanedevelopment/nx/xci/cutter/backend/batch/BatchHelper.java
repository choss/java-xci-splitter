package org.insanedevelopment.nx.xci.cutter.backend.batch;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.insanedevelopment.nx.xci.cutter.backend.model.XciFileInformation;

public class BatchHelper {

	public static List<String> getAllXciImageFilesRecursively(String baseDir) {
		if (StringUtils.trimToNull(baseDir) == null) {
			return Collections.emptyList();
		}
		Collection<File> files = FileUtils.listFiles(new File(baseDir), new SuffixFileFilter(Arrays.asList(".xci", ".xc0")), TrueFileFilter.INSTANCE);
		List<String> result = files.stream().map(f -> f.getAbsolutePath()).collect(Collectors.toList());
		return result;
	}

	public static String getOutputFileName(XciFileInformation source, boolean isTrim) {
		String sourceFile = source.getMainFileName();
		String result = FilenameUtils.getFullPath(sourceFile) + FilenameUtils.getBaseName(sourceFile);
		String extension;
		result = result + "-" + (isTrim ? "cut" : "merge");

		if (isTrim) {
			extension = ".xc0";
		} else {
			extension = ".xci";
		}
		result = result + extension;
		return result;
	}

	public static Pair<XciFileInformation, String> generateInformationForSourceFile(String sourceFile, boolean isTrim) {
		XciFileInformation xciFileInformation = new XciFileInformation(sourceFile);
		String outputFileName = getOutputFileName(xciFileInformation, isTrim);
		return Pair.of(xciFileInformation, outputFileName);
	}

}
