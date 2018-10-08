package org.insanedevelopment.nx.xci.cutter.backend.model;

import java.io.File;
import java.util.List;

public interface SwitchGameFileInformation {

	/**
	 * Returns the name of the file, if unsplit. Name of the first file if split.
	 *
	 * @return name of the file
	 */
	public String getMainFileName();

	/**
	 * Returns the size of the physical file(s) on your drive
	 *
	 * @return size
	 */
	public long getFullFileSizeInBytes();

	/**
	 * Was the file split for FAT32 or not
	 *
	 * @return
	 */
	public boolean isSplit();

	/**
	 * Returns all filenames of the game (if split there is more than one)
	 *
	 * @return
	 */
	public List<String> getAllFileNames();

	/**
	 * Gets the size of the game data.
	 *
	 * @return
	 */
	public long getDataSizeInBytes();

	/**
	 * Gets the size of the "container" (e.g. XCI or NSP)
	 *
	 * @return
	 */
	public long getCartSizeInBytes();

	/**
	 * Size of the chunk the file should be split into
	 *
	 * @return
	 */
	public long getSplitSize();

	public File createOutputFile(String baseOutputFileName, int counter, long chunkSize);

	public String getTargetFileNameProposal(String suffix);

}