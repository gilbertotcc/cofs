package com.github.gilbertotcc.cofs.cli;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.vavr.control.Validation;
import org.junit.Test;

public class CommandLineInterfaceTest {

    @Test
    public void thatParseSuccessfullyGetsHelpCommand() {
        CommandLineInterface cli = CommandLineInterface.newCommandLineInterface();

        Validation<CliErrors, CliArguments> maybeCliArguments = cli.parse("--help");

        assertTrue(maybeCliArguments.get().isHelp());
        assertTrue(maybeCliArguments.get().getFilename().isEmpty());
    }

    @Test
    public void thatParseSuccessfullyGetsFileArgument() {
        CommandLineInterface cli = CommandLineInterface.newCommandLineInterface();

        Validation<CliErrors, CliArguments> maybeCliArguments = cli.parse("--input", "fileA.cofs");

        assertTrue(maybeCliArguments.get().getFilename().isDefined());
        assertFalse(maybeCliArguments.get().isHelp());
    }

    @Test
    public void thatParseFailsWithMoreThanSingleFileArgument() {
        CommandLineInterface cli = CommandLineInterface.newCommandLineInterface();

        Validation<CliErrors, CliArguments> maybeCliArguments = cli.parse("fileA.cofs", "fileB.cofs");

        assertTrue(maybeCliArguments.isInvalid());
    }

    @Test
    public void thatParseFailsWithUnknownParameter() {
        CommandLineInterface cli = CommandLineInterface.newCommandLineInterface();

        Validation<CliErrors, CliArguments> maybeCliArguments = cli.parse("--xxx");

        assertTrue(maybeCliArguments.isInvalid());
    }
}
