package com.github.gilbertotcc.cofs.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.gilbertotcc.cofs.antlr4.CofsBaseVisitor;
import com.github.gilbertotcc.cofs.antlr4.CofsParser.CofsProgramContext;
import com.github.gilbertotcc.cofs.bean.Transaction;
import com.github.gilbertotcc.cofs.bean.User;

public class CofsProgramVisitor extends CofsBaseVisitor<List<User>> {

	@Override
	public List<User> visitCofsProgram(CofsProgramContext ctx) {
		if (ctx.statement().isEmpty()) {
			return Collections.emptyList();
		}
		
		final List<User> users = new ArrayList<>();
		
		ctx.statement().forEach(stmt -> {
			if (stmt.add_user_expression() != null) {
				User newUser = stmt.accept(new AddUserExpressionVisitor());
				users.add(newUser);
			} else if (stmt.add_transaction_expression() != null) {
				Transaction newTransaction = stmt.accept(new AddTransactionExpressionVisitor(users));
				users.clear();
				users.addAll(newTransaction.commit());
			} else {
				throw new RuntimeException("Found unknown statement");
			}
		});
		
		return users;
	}
}
