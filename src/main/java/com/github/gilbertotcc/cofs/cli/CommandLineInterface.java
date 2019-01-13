package com.github.gilbertotcc.cofs.cli;

import static io.vavr.API.Invalid;
import static io.vavr.API.None;
import static io.vavr.API.Some;
import static io.vavr.API.Valid;

import java.util.function.Supplier;

import com.beust.jcommander.JCommander;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandLineInterface {

    private static final Validation.Valid<CliErrors, CliArguments> VALID_HELP_COMMAND = Valid(CliArguments.builder()
            .help(true)
            .filename(None())
            .build());

    private final CliParameters parameters;

    private final JCommander commander;

    private CommandLineInterface(final CliParameters parameters, final JCommander commander) {
        this.parameters = parameters;
        this.commander = commander;
    }

    public static CommandLineInterface newCommandLineInterface() {
        CliParameters parameters = new CliParameters();
        JCommander commander = JCommander.newBuilder()
                .addObject(parameters)
                .build();
        return new CommandLineInterface(parameters, commander);
    }

    public void printlnMessage(final String message) {
        JCommander.getConsole().println(message);
    }

    public void showUsage() {
        commander.usage();
    }

    public Validation<CliErrors, CliArguments> parse(final String ... arguments) {
        log.debug("Parse command line arguments: {}", List.of(arguments));
        return toValidCliArguments(() -> {
            commander.parse(arguments);

            return parameters.isHelp() ?
                    VALID_HELP_COMMAND :
                    Option.of(parameters.getInputFile())
                            .map(this::toValidCliArguments)
                            .getOrElse(Invalid(CliErrors.FILE_NOT_FOUND));
        });
    }

    private Validation<CliErrors, CliArguments> toValidCliArguments(final Supplier<Validation<CliErrors, CliArguments>> validator) {
        return Try.of(validator::get).getOrElse(Invalid(CliErrors.PARSING_ERROR));
    }

    private Validation<CliErrors, CliArguments> toValidCliArguments(final String filename) {
        return Valid(CliArguments.builder()
                .help(false)
                .filename(Some(filename))
                .build());
    }
}
