package com.github.gilbertotcc.cofs;

import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import com.github.gilbertotcc.cofs.antlr4.CofsLexer;
import com.github.gilbertotcc.cofs.antlr4.CofsParser;
import com.github.gilbertotcc.cofs.bean.TimeTick;
import com.github.gilbertotcc.cofs.bean.Transaction;
import com.github.gilbertotcc.cofs.bean.User;
import com.github.gilbertotcc.cofs.parser.AddTransactionExpressionVisitor;
import com.github.gilbertotcc.cofs.parser.ParserErrorListener;
import com.github.gilbertotcc.cofs.parser.ParserException;

import edu.emory.mathcs.backport.java.util.Arrays;
import junit.framework.TestCase;

public class AddTransactionExpressionVisitorTest extends TestCase {

	public static final String WELLFORMED_ADD_TRANSACTION_EXPRESSION = "add transaction foo -> bar";
	public static final String MALFORMED_ADD_TRANSACTION_EXPRESSION = "mlf";

	@Test
	public void testParseShouldSuccess() {
		CofsLexer lexer = new CofsLexer(new ANTLRInputStream(WELLFORMED_ADD_TRANSACTION_EXPRESSION));
		lexer.removeErrorListeners();
		lexer.addErrorListener(new ParserErrorListener());
		CofsParser parser = new CofsParser(new CommonTokenStream(lexer));
		parser.removeErrorListeners();
		parser.addErrorListener(new ParserErrorListener());

		@SuppressWarnings("unchecked")
		List<User> testUsers = Arrays.asList(new User[] { new User("foo", 0), new User("bar", 0) });

		TimeTick currentTime = TimeTick.getNext();

		ParseTree tree = parser.add_transaction_expression();

		Transaction testTransaction = new AddTransactionExpressionVisitor(testUsers).visit(tree);
		assertEquals(testTransaction.getOfferor().getUserId(), "foo");
		assertEquals(testTransaction.getRecipients().size(), 1);
		assertEquals(testTransaction.getRecipients().get(0).getUserId(), "bar");
		assertEquals(testTransaction.getTime().getTime(), currentTime.getTime() + 1);
	}

	@Test
	public void testParseShouldFail() {
		CofsLexer lexer = new CofsLexer(new ANTLRInputStream(MALFORMED_ADD_TRANSACTION_EXPRESSION));
		lexer.removeErrorListeners();
		lexer.addErrorListener(new ParserErrorListener());
		CofsParser parser = new CofsParser(new CommonTokenStream(lexer));
		parser.removeErrorListeners();
		parser.addErrorListener(new ParserErrorListener());

		try {
			parser.add_transaction_expression();
			fail();
		} catch (ParserException e) {
			assertTrue(e instanceof ParserException);
		}
	}
}
