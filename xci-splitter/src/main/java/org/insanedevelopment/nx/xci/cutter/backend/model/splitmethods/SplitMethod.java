package org.insanedevelopment.nx.xci.cutter.backend.model.splitmethods;

import java.util.List;

public interface SplitMethod {

	public List<String> getAllSplitFiles(String mainFileName);

	public String getOutputFileNameNoSplitting(String baseOutputFileName);

	public String getOutputFileNameSplitting(String baseOutputFileName, int counter);

	public long getSplitSizeRecommendation();

}