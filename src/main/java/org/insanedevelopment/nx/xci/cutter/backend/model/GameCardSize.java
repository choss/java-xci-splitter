package org.insanedevelopment.nx.xci.cutter.backend.model;

public enum GameCardSize {
	_1GB(0xFA, 1), _2GB(0xF8, 2), _4GB(0xF0, 4), _8GB(0xE0, 8), _16GB(0xE1, 16), _32GB(0xE2, 32);

	private int magicNumber;
	private long cartSizeInBytes;

	private GameCardSize(int magicNumber, long cartSizeInBytes) {
		this.magicNumber = magicNumber;
		this.cartSizeInBytes = cartSizeInBytes * 998244352L; // nintendos version of a gigabyte?
	}

	public static GameCardSize fromMagicByte(byte readByte) {
		int magicNumber = ((int) readByte & 0xFF); // damn you java and your signed types
		for (GameCardSize size : GameCardSize.values()) {
			if (size.magicNumber == magicNumber) {
				return size;
			}
		}
		return null;
	}

	public long getCartSizeInBytes() {
		return cartSizeInBytes;
	}

	public String toString() {
		return this.name() + " (" + cartSizeInBytes + " bytes)";
	}
}
