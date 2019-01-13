package com.github.gilbertotcc.cofs;

import static java.lang.String.format;

import java.util.List;

import com.github.gilbertotcc.cofs.bean.User;
import com.github.gilbertotcc.cofs.cli.CliArguments;
import com.github.gilbertotcc.cofs.cli.CommandLineInterface;
import com.github.gilbertotcc.cofs.cli.CliErrors;
import com.github.gilbertotcc.cofs.core.CofsCore;
import com.github.gilbertotcc.cofs.core.CofsCoreException;
import io.vavr.control.Validation;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class CofsLauncher {

	public static void main(String[] args) {

		CommandLineInterface cli = CommandLineInterface.newCommandLineInterface();
		Validation<CliErrors, CliArguments> maybeCliArguments = cli.parse(args);

		if (maybeCliArguments.isInvalid()) {
			cli.printlnMessage(format("Error occurred while parsing arguments (%s)", maybeCliArguments.getError().getMessage()));
			cli.showUsage();
			System.exit(1);
		}
		
		try {
			CofsCore core = new CofsCore();
			List<User> users = core.loadUsersFromFile(maybeCliArguments.get().getFilename().get()); // FIXME List vs String
			List<User> scheduledUsers = core.schedule(users);
			printUsers(scheduledUsers, cli);
			
			System.exit(0);
		} catch (CofsCoreException e) {
			log.error("Error occurred: " + e.getMessage(), e);
			cli.printlnMessage("Error occured: " + e.getMessage());
			System.exit(1);
		}
	}

	private static void printUsers(List<User> users, CommandLineInterface commandLineInterface) {
		commandLineInterface.printlnMessage("Users:");
		users.stream()
				.map(User::toString)
				.forEach(commandLineInterface::printlnMessage);
	}
}
