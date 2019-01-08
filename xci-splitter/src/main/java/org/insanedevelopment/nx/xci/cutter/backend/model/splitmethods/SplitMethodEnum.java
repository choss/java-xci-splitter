package org.insanedevelopment.nx.xci.cutter.backend.model.splitmethods;

import java.util.function.Function;

public enum SplitMethodEnum {

	SX(s -> new SxSplitMethod(s)),
	NX(s -> new NintendoSwitchSplitMethod(s));

	private Function<String, SplitMethod> builder;

	private SplitMethodEnum(Function<String, SplitMethod> builder) {
		this.builder = builder;
	}

	public SplitMethod buildSplitMethod(String extension) {
		return builder.apply(extension);
	}
}
