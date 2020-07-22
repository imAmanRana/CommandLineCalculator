/**
 * Synopsys Coding Assignment
 */
package com.synopsys.codingassignment.commandLineCalculator.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.synopsys.codingassignment.commandLineCalculator.dataType.Token;
import com.synopsys.codingassignment.commandLineCalculator.state.Expression;
import com.synopsys.codingassignment.commandLineCalculator.utils.Constants;
import com.synopsys.codingassignment.commandLineCalculator.utils.TokenType;

/**
 * @author Amandeep Singh
 * @see <a href="https://www.linkedin.com/in/imamanrana/" target=
 *      "_blank">LinkedIn Profile</a>
 *
 */
public class InputParser {

	public static List<Token> parseToPreFixNotation(final Expression expression) throws IOException {

		Reader reader = new StringReader(expression.getInput());
		StreamTokenizer st = new StreamTokenizer(reader);
		List<Token> tokens = new ArrayList<>();
		Token operator;
		while (st.nextToken() != StreamTokenizer.TT_EOF) {

			switch (st.ttype) {

			case StreamTokenizer.TT_NUMBER:
				tokens.add(new Token(Integer.valueOf((int) st.nval), TokenType.INTEGER));
				break;
			case StreamTokenizer.TT_WORD:
				operator = mapToOperator(st.sval);

				if ("=".equals(operator.getValue().toString())) {
					operator = readVariable(st);
					tokens.add(operator);
					expression.getVariables().add(operator.getValue().toString());
				} else {
					tokens.add(operator);
				}
				break;
			case Constants.LEFT_PARENTHESIS:
				expression.setParenthesisCount(expression.getParenthesisCount() + 1);
				break;
			case Constants.RIGHT_PARENTHESIS:
				expression.setParenthesisCount(expression.getParenthesisCount() - 1);
				break;
			case Constants.COMMA:
				break;

			}

		}

		return tokens;
	}

	/**
	 * @param st
	 * @return
	 * @throws IOException
	 */
	private static Token readVariable(final StreamTokenizer st) throws IOException {
		String variable = null;
		if (st.nextToken() == Constants.LEFT_PARENTHESIS && st.nextToken() == StreamTokenizer.TT_WORD) {
			variable = st.sval;
		}

		return new Token(variable, TokenType.CHARACTER);
	}

	/**
	 * @param sval
	 * @return
	 */
	private static Token mapToOperator(final String word) {

		Token token = null;
		switch (word.toLowerCase()) {
		case Constants.ADD_EXPRESSION:
			token = new Token("+", TokenType.OPERATOR);
			break;
		case Constants.SUB_EXPRESSION:
			token = new Token("-", TokenType.OPERATOR);
			break;
		case Constants.MULT_EXPRESSION:
			token = new Token("*", TokenType.OPERATOR);
			break;
		case Constants.DIV_EXPRESSION:
			token = new Token("/", TokenType.OPERATOR);
			break;
		case Constants.LET_EXPRESSION:
			token = new Token("=", TokenType.OPERATOR);
			break;
		default:
			token = new Token(word, TokenType.CHARACTER);
			break;
		}

		return token;
	}

}
