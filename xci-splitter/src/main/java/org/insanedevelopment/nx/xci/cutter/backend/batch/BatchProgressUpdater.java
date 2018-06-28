package org.insanedevelopment.nx.xci.cutter.backend.batch;

public interface BatchProgressUpdater {

	public void updateBatchProgress(String itemName, double percentage, int currentItemNo, int totalItems);

}
