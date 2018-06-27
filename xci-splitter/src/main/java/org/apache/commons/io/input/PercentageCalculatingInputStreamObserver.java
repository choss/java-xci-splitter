package org.apache.commons.io.input;

import java.io.IOException;

import org.insanedevelopment.nx.xci.cutter.backend.WorkflowStepPercentageObserver;

public class PercentageCalculatingInputStreamObserver extends ObservableInputStream.Observer {

	private final long totalSize;
	private long readSize;
	private WorkflowStepPercentageObserver observer;

	public PercentageCalculatingInputStreamObserver(long totalSize, WorkflowStepPercentageObserver observer) {
		this.totalSize = totalSize;
		this.observer = observer;
	}

	@Override
	void data(final int pByte) throws IOException {
		readSize++;
		updatePercentage();
	}

	@Override
	void data(final byte[] pBuffer, final int pOffset, final int pLength) throws IOException {
		readSize += pLength;
		updatePercentage();
	}

	private void updatePercentage() {
		double percentageRead = (double)  readSize / (double) totalSize * 100.0d;
		observer.updatePercentage(percentageRead, readSize, totalSize);
	}

	
	
}
