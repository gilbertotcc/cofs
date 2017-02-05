package com.github.gilbertotcc.cofs;

import java.io.StringReader;
import java.util.List;

import org.junit.Test;

import com.github.gilbertotcc.cofs.bean.User;
import com.github.gilbertotcc.cofs.parser.Parser;
import com.github.gilbertotcc.cofs.parser.ParserException;

import junit.framework.TestCase;

public class ParserTest extends TestCase {
	
	public static final String WELLFORMED_COFS_PROGRAM = new StringBuilder()
			.append("add user foo (10);")
			.append("add user bar;")
			.append("add transaction foo -> bar;")
			.toString();
	public static final String MALFORMED_COFS_PROGRAM = "mlf";
	
	@Test
	public void testParseShouldSuccess() {
		Parser parser = new Parser(new StringReader(WELLFORMED_COFS_PROGRAM));
		List<User> users = parser.parse();
		
		assertNotNull(users);
		assertTrue(users.size() == 2);
	}
	
	@Test
	public void testParseShouldFail() {
		Parser parser = new Parser(new StringReader(MALFORMED_COFS_PROGRAM));
		try {
			parser.parse();
			fail();
		} catch (ParserException e) {
			assertTrue(e instanceof ParserException);
		}
	}
}
