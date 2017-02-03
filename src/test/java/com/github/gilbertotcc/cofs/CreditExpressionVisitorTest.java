package com.github.gilbertotcc.cofs;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import com.github.gilbertotcc.cofs.antlr4.CofsLexer;
import com.github.gilbertotcc.cofs.antlr4.CofsParser;
import com.github.gilbertotcc.cofs.parser.CreditExpressionVisitor;
import com.github.gilbertotcc.cofs.parser.ParsingException;

import junit.framework.TestCase;

public class CreditExpressionVisitorTest extends TestCase {

	public static final String WELLFORMED_CREDIT_EXPRESSION = "(-1)";
	public static final String MALFORMED_CREDIT_EXPRESSION = "mlf";

	@Test
	public void testParseShouldSuccess() {
		CofsLexer lexer = new CofsLexer(new ANTLRInputStream(WELLFORMED_CREDIT_EXPRESSION));
		CofsParser parser = new CofsParser(new CommonTokenStream(lexer));

		ParseTree tree = parser.credit_expression();

		Integer credit = new CreditExpressionVisitor().visit(tree);
		assertEquals(credit, Integer.valueOf(-1));
	}

	@Test
	public void testParseShouldFail() {
		CofsLexer lexer = new CofsLexer(new ANTLRInputStream(MALFORMED_CREDIT_EXPRESSION));
		CofsParser parser = new CofsParser(new CommonTokenStream(lexer));

		ParseTree tree = parser.credit_expression();
		
		try {
			new CreditExpressionVisitor().visit(tree);
			fail();
		} catch (Throwable t) {
			assertTrue(t instanceof ParsingException);
			assertEquals(
					((ParsingException) t).getErrorCode(),
					ParsingException.ErrorEnums.MALFORMED_CREDIT.code()
					);
		}
	}
}
