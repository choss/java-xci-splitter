package org.insanedevelopment.nx.xci.cutter.backend.model.splitmethods;

import java.util.List;

public interface SplitMethod {

	List<String> getAllSplitFiles(String mainFileName);

	String getOutputFileNameNoSplitting(String baseOutputFileName);

	String getOutputFileNameSplitting(String baseOutputFileName, int counter);

}