package org.apache.commons.io.input;

import java.io.IOException;

public class PercentageCalculatingInputStreamObserver extends ObservableInputStream.Observer {

	private final long totalSize;
	private long readSize;

	public PercentageCalculatingInputStreamObserver(long totalSize) {
		this.totalSize = totalSize;
		System.out.println("---");
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
		System.out.print(percentageRead + "% " + readSize + " of " + totalSize + "\r");
	}

	
	
}
