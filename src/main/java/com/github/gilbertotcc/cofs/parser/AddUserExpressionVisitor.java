package com.github.gilbertotcc.cofs.parser;

import com.github.gilbertotcc.cofs.antlr4.CofsBaseVisitor;
import com.github.gilbertotcc.cofs.antlr4.CofsParser.AddUserExpressionContext;
import com.github.gilbertotcc.cofs.bean.User;

public class AddUserExpressionVisitor extends CofsBaseVisitor<User> {

	@Override
	public User visitAddUserExpression(AddUserExpressionContext ctx) {
		String userId = ctx.userId.getText();

		if (ctx.credit == null) {
			return new User(userId, 0);
		} else {
			int credit = ctx.credit.accept(new CreditExpressionVisitor());
			return new User(userId, credit);
		}
	}
}
