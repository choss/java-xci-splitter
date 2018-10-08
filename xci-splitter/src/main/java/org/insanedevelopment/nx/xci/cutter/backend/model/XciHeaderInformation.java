package org.insanedevelopment.nx.xci.cutter.backend.model;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;

import org.apache.commons.lang3.ArrayUtils;

public class XciHeaderInformation {

	private static XciHeaderInformation EMPTY;

	private Integer gamecardHeaderVersion;
	private GameCardSize gamecardSize;
	private BigInteger dataFileSizeInBytes;

	private XciHeaderInformation() {
		// no public constructor, use static methods instead
	}

	/**
	 * Offsets from http://switchbrew.org/index.php?title=Gamecard_Format
	 *
	 * @param inputFile
	 * @return
	 * @throws IOException
	 */
	public static XciHeaderInformation readFromStream(RandomAccessFile inputFile) throws IOException {
		XciHeaderInformation result = new XciHeaderInformation();
		long oldPosition = inputFile.getFilePointer();

		inputFile.seek(0x100);
		String head = "";
		for (int i = 0; i < 4; i++) {
			head += (char) inputFile.readByte();
		}

		if (!"HEAD".equals(head)) {
			throw new InvalidXciFileException("Header marker not found, file seems invalid");
		}

		inputFile.seek(0x10E);
		result.gamecardHeaderVersion = (int) inputFile.readByte();

		inputFile.seek(0x118);
		byte[] lastValidAddress = new byte[8];
		inputFile.readFully(lastValidAddress);
		ArrayUtils.reverse(lastValidAddress);
		result.dataFileSizeInBytes = new BigInteger(1, lastValidAddress).add(BigInteger.ONE).multiply(BigInteger.valueOf(0x200));

		inputFile.seek(0x10D);
		result.gamecardSize = GameCardSize.fromMagicByte(inputFile.readByte());

		inputFile.seek(oldPosition);
		return result;
	}

	public static XciHeaderInformation readFromFile(String firstFileName) {
		try (RandomAccessFile inputFile = new RandomAccessFile(firstFileName, "r")) {
			XciHeaderInformation result = readFromStream(inputFile);
			return result;
		} catch (IOException e) {
			return XciHeaderInformation.EMPTY;
		}
	}

	@Override
	public String toString() {
		return "XciHeader [gamecardHeaderVersion=" + gamecardHeaderVersion + ", gamecardSize=" + gamecardSize + ", dataFileSizeInBytes=" + dataFileSizeInBytes
				+ "]";
	}

	public Integer getGamecardHeaderVersion() {
		return gamecardHeaderVersion;
	}

	public GameCardSize getGamecardSize() {
		return gamecardSize;
	}

	public BigInteger getDataFileSizeInBytes() {
		return dataFileSizeInBytes;
	}

	static {
		EMPTY = new XciHeaderInformation();
		EMPTY.dataFileSizeInBytes = BigInteger.ZERO;
		EMPTY.gamecardHeaderVersion = Integer.valueOf(-1);
		EMPTY.gamecardSize = GameCardSize.UNKNOWN;
	}

}
