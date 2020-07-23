/**
 * Synopsys Coding Assignment
 */
package com.synopsys.codingassignment.commandLineCalculator.parser;

import static com.synopsys.codingassignment.commandLineCalculator.utils.Constants.ADD;
import static com.synopsys.codingassignment.commandLineCalculator.utils.Constants.ADD_EXPRESSION;
import static com.synopsys.codingassignment.commandLineCalculator.utils.Constants.DIVISION;
import static com.synopsys.codingassignment.commandLineCalculator.utils.Constants.DIV_EXPRESSION;
import static com.synopsys.codingassignment.commandLineCalculator.utils.Constants.EQUALS;
import static com.synopsys.codingassignment.commandLineCalculator.utils.Constants.LEFT_PARENTHESIS;
import static com.synopsys.codingassignment.commandLineCalculator.utils.Constants.LET_EXPRESSION;
import static com.synopsys.codingassignment.commandLineCalculator.utils.Constants.MULTIPLY;
import static com.synopsys.codingassignment.commandLineCalculator.utils.Constants.MULT_EXPRESSION;
import static com.synopsys.codingassignment.commandLineCalculator.utils.Constants.RIGHT_PARENTHESIS;
import static com.synopsys.codingassignment.commandLineCalculator.utils.Constants.SUBSTRACT;
import static com.synopsys.codingassignment.commandLineCalculator.utils.Constants.SUB_EXPRESSION;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.synopsys.codingassignment.commandLineCalculator.dataType.Token;
import com.synopsys.codingassignment.commandLineCalculator.state.Expression;
import com.synopsys.codingassignment.commandLineCalculator.utils.TokenType;

/**
 * Parses the input to pre-fix notation for evaluation
 * 
 * @author Amandeep Singh
 * @see <a href="https://www.linkedin.com/in/imamanrana/" target=
 *      "_blank">LinkedIn Profile</a>
 *
 */
public class InputParser {

	/**
	 * Parses input
	 * 
	 * @param expression input expression
	 * @return list of tokens in pre-fix notation
	 * @throws IOException
	 */
	public static List<Token> parseToPreFixNotation(final Expression expression) throws IOException {

		Reader reader = new StringReader(expression.getInput());
		StreamTokenizer st = new StreamTokenizer(reader);
		List<Token> tokens = new ArrayList<>();
		Token operator;
		while (st.nextToken() != StreamTokenizer.TT_EOF) {

			switch (st.ttype) {

			case StreamTokenizer.TT_NUMBER:
				tokens.add(new Token(Integer.valueOf((int) st.nval), TokenType.NUMBER));
				break;
			case StreamTokenizer.TT_WORD:
				operator = mapToOperator(st.sval);

				if ("=".equals(operator.getValue().toString())) {
					// fetch the variable name
					operator = readVariable(st);
					tokens.add(operator);
					expression.getVariables().add(operator.getValue().toString());

				} else {
					tokens.add(operator);
				}
				break;
			case LEFT_PARENTHESIS:
				expression.setParenthesisCount(expression.getParenthesisCount() + 1);
				break;
			case RIGHT_PARENTHESIS:
				expression.setParenthesisCount(expression.getParenthesisCount() - 1);
				break;
			}
		}
		return tokens;
	}

	/**
	 * Maps input word to {@link Token}
	 * @param word
	 * @return mapped Token
	 */
	private static Token mapToOperator(final String word) {

		Token token = null;
		switch (word.toLowerCase()) {
		case ADD_EXPRESSION:
			token = new Token(ADD, TokenType.OPERATOR);
			break;
		case SUB_EXPRESSION:
			token = new Token(SUBSTRACT, TokenType.OPERATOR);
			break;
		case MULT_EXPRESSION:
			token = new Token(MULTIPLY, TokenType.OPERATOR);
			break;
		case DIV_EXPRESSION:
			token = new Token(DIVISION, TokenType.OPERATOR);
			break;
		case LET_EXPRESSION:
			token = new Token(EQUALS, TokenType.OPERATOR);
			break;
		default:
			token = new Token(word, TokenType.CHARACTER);
			break;
		}

		return token;
	}

	/**
	 * 
	 * @param st
	 * @return
	 * @throws IOException
	 */
	private static Token readVariable(final StreamTokenizer st) throws IOException {
		String variable = null;
		if (st.nextToken() == LEFT_PARENTHESIS && st.nextToken() == StreamTokenizer.TT_WORD) {
			variable = st.sval;
		}

		return new Token(variable, TokenType.CHARACTER);
	}

}
