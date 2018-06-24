package org.insanedevelopment.nx.xci.cutter.backend;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ObservableInputStream;
import org.apache.commons.io.input.PercentageCalculatingInputStreamObserver;
import org.insanedevelopment.nx.xci.cutter.backend.model.XciFileInformation;

public class XciFileMerger {

	public static void mergeSplitFiles(XciFileInformation source, String target) throws IOException {
		if (!source.isSplit()) {
			return;
		}
		File targetFile = new File(target);
		FileUtils.forceMkdirParent(targetFile);
		try (OutputStream outputStream = IOUtils.buffer(FileUtils.openOutputStream(targetFile))) {

			PercentageCalculatingInputStreamObserver observer = new PercentageCalculatingInputStreamObserver(source.getFullFileSize());

			for (String fileName : source.getAllFileNames()) {
				try (ObservableInputStream inputStream = new ObservableInputStream(FileUtils.openInputStream(new File(fileName)))) {
					inputStream.add(observer);
					IOUtils.copyLarge(inputStream, outputStream);
				}
			}

			for (long i = source.getDataSize(); i < source.getCartSize(); i++) {
				outputStream.write(0xFF);
			}
		}
	}

}
