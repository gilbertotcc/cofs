package com.github.gilbertotcc.cofs.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.gilbertotcc.cofs.antlr4.CofsBaseVisitor;
import com.github.gilbertotcc.cofs.antlr4.CofsParser.CofsProgramContext;
import com.github.gilbertotcc.cofs.antlr4.CofsParser.StatementContext;
import com.github.gilbertotcc.cofs.bean.Transaction;
import com.github.gilbertotcc.cofs.bean.User;

public class CofsProgramVisitor extends CofsBaseVisitor<List<User>> {
	
	private static final Logger LOG = Logger.getLogger(CofsProgramVisitor.class.getName());
	
	private enum StatementTypeEnums {
		ADD_USER_STATEMENT, ADD_TRANSACTION_STATEMENT, UNKNOWN_STATEMENT;
	}

	@Override
	public List<User> visitCofsProgram(CofsProgramContext ctx) {
		if (ctx.statement() == null || ctx.statement().isEmpty()) {
			return Collections.emptyList();
		}
		
		List<User> users = new ArrayList<>();
		
		for (StatementContext stmt : ctx.statement()) {
			switch (getStatementType(stmt)) {
			case ADD_USER_STATEMENT:
				User u = stmt.accept(new AddUserExpressionVisitor());
				users = addUser(u, users);
				break;
			case ADD_TRANSACTION_STATEMENT:
				Transaction t = stmt.accept(new AddTransactionExpressionVisitor(users));
				users = commitTransaction(t);
				break;
			default:
				throw new ParserException("Uknown statement found");
			}
		}
		
		return users;
	}
	
	private static StatementTypeEnums getStatementType(StatementContext stmt) {
		if (stmt.add_user_expression() != null) {
			return StatementTypeEnums.ADD_USER_STATEMENT;
		}
		if (stmt.add_transaction_expression() != null) {
			return StatementTypeEnums.ADD_TRANSACTION_STATEMENT;
		}
		return StatementTypeEnums.UNKNOWN_STATEMENT;
	}
	
	private static List<User> addUser(User u, List<User> userList) {
		LOG.log(Level.INFO, "Add user: {0}", u.toString());
		assertIsNewUser(u, userList);
		userList.add(u);
		return userList;
	}
	
	private static List<User> commitTransaction(Transaction t) {
		LOG.log(Level.INFO, "Commit transaction: {0}", t.toString());
		return t.commit();
	}
	
	private static void assertIsNewUser(User u, List<User> userList) {
		String uId = u.getUserId();
		if (userList.stream().filter(user -> user.getUserId().equals(uId)).count() != 0) {
			throw new ParserException("Duplicated user: " + uId);
		}
	}
}
