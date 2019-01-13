package com.github.gilbertotcc.cofs.cli;

import io.vavr.control.Option;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CliArguments {

    private boolean help;

    private Option<String> filename;
}
