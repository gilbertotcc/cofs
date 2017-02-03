package com.github.gilbertotcc.cofs.parser;

import com.github.gilbertotcc.cofs.antlr4.CofsBaseVisitor;
import com.github.gilbertotcc.cofs.antlr4.CofsParser.Credit_expressionContext;

public class CreditExpressionVisitor extends CofsBaseVisitor<Integer> {
	
	@Override
	public Integer visitCredit_expression(Credit_expressionContext ctx) {
		return Integer.parseInt(ctx.Int().getText());
	}
}
