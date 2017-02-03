package com.github.gilbertotcc.cofs;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import com.github.gilbertotcc.cofs.antlr4.CofsLexer;
import com.github.gilbertotcc.cofs.antlr4.CofsParser;
import com.github.gilbertotcc.cofs.bean.TimeTick;
import com.github.gilbertotcc.cofs.bean.User;
import com.github.gilbertotcc.cofs.parser.AddUserExpressionVisitor;
import com.github.gilbertotcc.cofs.parser.ParsingException;

import junit.framework.TestCase;

public class AddUserExpressionVisitorTest extends TestCase {

	public static final String WELLFORMED_ADD_USER_EXPRESSION = "add user foo (10);";
	public static final String WELLFORMED_ADD_USER_EXPRESSION_SHORT = "add user foo";
	public static final String MALFORMED_ADD_USER_EXPRESSION = "mlf";

	@Test
	public void testParseShouldSuccess() {
		CofsLexer lexer = new CofsLexer(new ANTLRInputStream(WELLFORMED_ADD_USER_EXPRESSION));
		CofsParser parser = new CofsParser(new CommonTokenStream(lexer));

		ParseTree tree = parser.add_user_expression();

		TimeTick currentTime = TimeTick.getNext();

		User userTest = new AddUserExpressionVisitor().visit(tree);
		assertEquals(userTest.getUserId(), "foo");
		assertEquals(userTest.getCredit(), 10);
		assertEquals(userTest.getLastOffer().getTime(), currentTime.getTime() + 1);
	}

	@Test
	public void testParseShortShouldSuccess() {
		CofsLexer lexer = new CofsLexer(new ANTLRInputStream(WELLFORMED_ADD_USER_EXPRESSION_SHORT));
		CofsParser parser = new CofsParser(new CommonTokenStream(lexer));

		ParseTree tree = parser.add_user_expression();

		TimeTick currentTime = TimeTick.getNext();

		User userTest = new AddUserExpressionVisitor().visit(tree);
		assertEquals(userTest.getUserId(), "foo");
		assertEquals(userTest.getCredit(), 0);
		assertEquals(userTest.getLastOffer().getTime(), currentTime.getTime() + 1);
	}

	@Test
	public void testParseShouldFail() {
		CofsLexer lexer = new CofsLexer(new ANTLRInputStream(MALFORMED_ADD_USER_EXPRESSION));
		CofsParser parser = new CofsParser(new CommonTokenStream(lexer));

		ParseTree tree = parser.add_user_expression();

		try {
			User emptyUser = new AddUserExpressionVisitor().visit(tree);
			assertNull(emptyUser);
		} catch (Throwable t) {
			assertTrue(t instanceof ParsingException);
			assertEquals(((ParsingException) t).getErrorCode(), ParsingException.ErrorEnums.MALFORMED_ADD_USER.code());
		}
	}
}
