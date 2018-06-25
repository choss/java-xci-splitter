package org.insanedevelopment.nx.xci.cutter.backend;

public enum WorkflowStep {
	MERGING("Merging files"),
	PADDING("Adding the removed padding..."),
	CHECK_PADDING("Checking the padding"),
	TRIMMING_AND_SPLITTING("Trimming and splitting files"),
	DONE("Done"),
	UNKNOWN("ERROR-UNKNOWN");

	private String displayString;

	private WorkflowStep(String displayString) {
		this.displayString = displayString;
	}

	public String getDisplayString() {
		return displayString;
	}

}
