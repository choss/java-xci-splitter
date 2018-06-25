package org.insanedevelopment.nx.xci.cutter.backend;

public enum WorkflowStep {
	MERGING, PADDING, CHECK_PADDING, TRIMMING_AND_SPLITTING;

	public String getDisplayString() {
		//TODO proper messages
		return this.toString();
	}

}
