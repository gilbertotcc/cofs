package com.github.gilbertotcc.cofs.cli;

public enum CliErrors {
    PARSING_ERROR("Cannot parse arguments"),
    FILE_NOT_FOUND("File not found");

    private final String message;

    CliErrors(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
