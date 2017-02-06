package com.github.gilbertotcc.cofs.commandline;

import java.util.List;

import com.beust.jcommander.Parameter;

public class CommandLineParameters {

	@Parameter(description = "<input file>", required = true)
	private List<String> inputFile;

//	@Parameter(names = { "--output", "-o" }, description = "COFS source output file")
//	private String outputFile;

	@Parameter(names = { "--help", "-h" }, description = "Show this help output", help = true)
	private boolean isHelp = false;

	public List<String> getInputFile() {
		return inputFile;
	}

//	public String getOutputFile() {
//		return outputFile;
//	}

	public boolean isHelp() {
		return isHelp;
	}
}
