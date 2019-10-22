package org.insanedevelopment.nx.xci.cutter.backend.batch;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.insanedevelopment.nx.xci.cutter.backend.FileExtensionUtils;
import org.insanedevelopment.nx.xci.cutter.backend.model.SwitchGameFileInformation;
import org.insanedevelopment.nx.xci.cutter.backend.model.splitmethods.SplitMethodEnum;
import org.insanedevelopment.nx.xci.cutter.frontend.ModelHelper;

public class BatchHelper {

	public static List<String> getAllXciImageFilesRecursively(String baseDir) {
		if (StringUtils.trimToNull(baseDir) == null) {
			return Collections.emptyList();
		}
		IOFileFilter fileFilter = new SuffixFileFilter(Arrays.asList(FileExtensionUtils.ALL_EXTENSIONS));
		Collection<File> files = FileUtils.listFiles(new File(baseDir), fileFilter, TrueFileFilter.INSTANCE);
		List<String> result = files.stream().map(f -> f.getAbsolutePath()).collect(Collectors.toList());
		return result;
	}

	private static String getOutputFileName(SwitchGameFileInformation source, boolean isTrim) {
		String suffix = "-" + (isTrim ? "cut" : "merge");
		String result = source.getTargetFileNameProposal(suffix);
		return result;
	}

	public static Pair<SwitchGameFileInformation, String> generateInformationForSourceFile(String sourceFile, boolean isTrim, SplitMethodEnum splitMethod) {
		SwitchGameFileInformation xciFileInformation = ModelHelper.getFileInformation(sourceFile, splitMethod);
		String outputFileName = getOutputFileName(xciFileInformation, isTrim);
		return Pair.of(xciFileInformation, outputFileName);
	}

}
