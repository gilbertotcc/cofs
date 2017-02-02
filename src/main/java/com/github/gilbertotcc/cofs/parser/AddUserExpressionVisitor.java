package com.github.gilbertotcc.cofs.parser;

import com.github.gilbertotcc.cofs.antlr4.CofsBaseVisitor;
import com.github.gilbertotcc.cofs.antlr4.CofsParser.AddUserExpressionContext;
import com.github.gilbertotcc.cofs.bean.User;

public class AddUserExpressionVisitor extends CofsBaseVisitor<User> {

	@Override
	public User visitAddUserExpression(AddUserExpressionContext ctx) {
		String userId = ctx.userId.getText();
		int credit = ctx.credit.isEmpty() ? 0 : 0; // FIXME
		
		// TODO Auto-generated method stub
		
		return new User(userId, credit);
	}
}
