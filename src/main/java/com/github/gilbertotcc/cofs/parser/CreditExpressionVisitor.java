package com.github.gilbertotcc.cofs.parser;

import com.github.gilbertotcc.cofs.antlr4.CofsBaseVisitor;
import com.github.gilbertotcc.cofs.antlr4.CofsParser.Credit_expressionContext;

public class CreditExpressionVisitor extends CofsBaseVisitor<Integer> {

	@Override
	public Integer visitCredit_expression(Credit_expressionContext ctx) {
		try {
			String text = ctx.Int().getText();
			return Integer.parseInt(text);
		} catch (Throwable t) {
			throw new ParsingException(ParsingException.ErrorEnums.MALFORMED_CREDIT, t);
		}
	}
}
