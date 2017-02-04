package com.github.gilbertotcc.cofs;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import com.github.gilbertotcc.cofs.antlr4.CofsLexer;
import com.github.gilbertotcc.cofs.antlr4.CofsParser;
import com.github.gilbertotcc.cofs.parser.CreditExpressionVisitor;
import com.github.gilbertotcc.cofs.parser.ParserErrorListener;
import com.github.gilbertotcc.cofs.parser.ParserException;

import junit.framework.TestCase;

public class CreditExpressionVisitorTest extends TestCase {

	public static final String WELLFORMED_CREDIT_EXPRESSION = "(-1)";
	public static final String MALFORMED_CREDIT_EXPRESSION = "mlf";

	@Test
	public void testParseShouldSuccess() {
		CofsLexer lexer = new CofsLexer(new ANTLRInputStream(WELLFORMED_CREDIT_EXPRESSION));
		lexer.removeErrorListeners();
		lexer.addErrorListener(new ParserErrorListener());
		CofsParser parser = new CofsParser(new CommonTokenStream(lexer));
		parser.removeErrorListeners();
		parser.addErrorListener(new ParserErrorListener());

		ParseTree tree = parser.credit_expression();

		Integer credit = new CreditExpressionVisitor().visit(tree);
		assertEquals(credit, Integer.valueOf(-1));
	}

	@Test
	public void testParseShouldFail() {
		CofsLexer lexer = new CofsLexer(new ANTLRInputStream(MALFORMED_CREDIT_EXPRESSION));
		lexer.removeErrorListeners();
		lexer.addErrorListener(new ParserErrorListener());
		CofsParser parser = new CofsParser(new CommonTokenStream(lexer));
		parser.removeErrorListeners();
		parser.addErrorListener(new ParserErrorListener());
		
		try {
			parser.credit_expression();
			fail();
		} catch(ParserException e) {
			assertTrue(true);
		}
	}
}
