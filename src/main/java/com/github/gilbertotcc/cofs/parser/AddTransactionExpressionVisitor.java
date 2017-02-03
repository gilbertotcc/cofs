package com.github.gilbertotcc.cofs.parser;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.gilbertotcc.cofs.antlr4.CofsBaseVisitor;
import com.github.gilbertotcc.cofs.antlr4.CofsParser.AddTransactionExpressionContext;
import com.github.gilbertotcc.cofs.antlr4.CofsParser.Recipients_listContext;
import com.github.gilbertotcc.cofs.bean.Transaction;
import com.github.gilbertotcc.cofs.bean.User;

public class AddTransactionExpressionVisitor extends CofsBaseVisitor<Transaction> {

	private List<User> userList;

	public AddTransactionExpressionVisitor(List<User> userList) {
		this.userList = userList;
	}

	public AddTransactionExpressionVisitor() {
		this(Collections.emptyList());
	}

	@Override
	public Transaction visitAddTransactionExpression(AddTransactionExpressionContext ctx) {
		User offeror = findUserById(ctx.offeror.getText());
		List<User> recipients = parseRecipientList(ctx.recipients);
		return new Transaction(offeror, recipients);
	}

	private List<User> parseRecipientList(Recipients_listContext ctx) {
		Stream<User> recipientStream = ctx.UserId().stream().map(userIdToken -> findUserById(userIdToken.getText()));
		return recipientStream.collect(Collectors.toList());
	}

	private User findUserById(String userId) {
		User u = null;
		Iterator<User> iterator = userList.iterator();

		while (u == null && iterator.hasNext()) {
			User user = iterator.next();
			u = userId.equals(user.getUserId()) ? user : null;
		}

		return u;
	}
}
