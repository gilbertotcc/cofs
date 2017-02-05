package com.github.gilbertotcc.cofs;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.github.gilbertotcc.cofs.bean.User;
import com.github.gilbertotcc.cofs.commandline.CommandLineParameters;
import com.github.gilbertotcc.cofs.core.CofsCore;
import com.github.gilbertotcc.cofs.core.CofsCoreException;

public class CofsLauncher {
	
	private static final Logger LOG = Logger.getLogger(CofsLauncher.class.getName());
	
	private CofsLauncher() {
		// ...
	}

	public static void main(String[] args) {
		CommandLineParameters params = new CommandLineParameters();
		JCommander commander = new JCommander(params);
		
		try {
			commander.parse(args);
			
			CofsCore core = new CofsCore();
			List<User> users = core.loadUsersFromFile(params.getInputFile());
			//List<User> scheduledUsers = core.schedule(users);
			printUsers(users);
			
			System.exit(0);
		} catch (ParameterException e) {
			LOG.log(Level.FINEST, "Wrong/Missing parameters are given", e);
			commander.usage();
		} catch (CofsCoreException e) {
			LOG.log(Level.FINEST, "Error occured: " + e.getMessage(), e);
			JCommander.getConsole().println("Error occured: " + e.getMessage());
		} finally {
			System.exit(1);
		}
	}
	
	private static void printUsers(List<User> users) {
		JCommander.getConsole().println("Users:");
		users.forEach(u -> JCommander.getConsole().println(u.toString()));
	}
}
