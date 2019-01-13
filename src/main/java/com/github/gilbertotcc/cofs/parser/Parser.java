package com.github.gilbertotcc.cofs.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;

import com.github.gilbertotcc.cofs.antlr4.CofsLexer;
import com.github.gilbertotcc.cofs.antlr4.CofsParser;
import com.github.gilbertotcc.cofs.bean.User;

public class Parser {
	
	private final Reader reader;
	private final ParserErrorListener errorListener;
	
	public Parser(Reader reader) {
		this.reader = reader;
		this.errorListener = new ParserErrorListener();
	}
	
	/**
	 * 
	 * @return the list of users defined in the COFS program; their data are set
	 *         according to user and transaction declarations.
	 * @throws ParserException
	 *             if parsing fails.
	 */
	public List<User> parse() {
		try {
			CofsLexer lexer = new CofsLexer(new ANTLRInputStream(reader));
			setErrorListener(lexer, errorListener);
			
			CofsParser parser = new CofsParser(new CommonTokenStream(lexer));
			setErrorListener(parser, errorListener);
			
			ParseTree tree = parser.cofs_program(); // Retrieve the program root element

			List<User> users = new CofsProgramVisitor().visit(tree);
			return users;
		} catch (IOException e) {
			throw new ParserException("I/O error occured while parsing input", e);
		} catch (Exception e) {
			throw new ParserException("Not managed exception thrown", e);
		}
	}
	
	private <T extends Recognizer<?, ?>, U extends ANTLRErrorListener> void setErrorListener(T recognizer, U errorListener) {
		recognizer.removeErrorListeners();
		recognizer.addErrorListener(errorListener);
	}
}
