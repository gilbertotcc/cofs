package com.github.gilbertotcc.cofs.core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.gilbertotcc.cofs.bean.User;
import com.github.gilbertotcc.cofs.parser.Parser;
import com.github.gilbertotcc.cofs.parser.ParserException;

public class CofsCore {
	
	private static final Logger LOG = Logger.getLogger(CofsCore.class.getName());
	
	public CofsCore() {
		// ...
	}
	
	public List<User> loadUsersFromFile(String inputFile) throws CofsCoreException {
		try {
			LOG.log(Level.INFO, "Load users from file {0}", inputFile);
			FileReader reader = new FileReader(inputFile);
			return new Parser(reader).parse();
		} catch (FileNotFoundException | ParserException e) {
			LOG.log(Level.SEVERE, "Error occured while loading users", e);
			throw new CofsCoreException("Cannot load COFS file " + inputFile, e);
		}
	}
	
	public List<User> schedule(List<User> users) {
		return new CofsScheduler().schedule(users);
	}
}
