package com.github.gilbertotcc.cofs.cli;

import com.beust.jcommander.Parameter;
import lombok.Getter;

@Getter
public class CliParameters {

	@Parameter(names = { "--input", "-i" }, description = "A COFS file", required = true)
	private String inputFile;

	@Parameter(names = { "--help", "-h" }, description = "Print this help message", help = true)
	private boolean help = false;
}
