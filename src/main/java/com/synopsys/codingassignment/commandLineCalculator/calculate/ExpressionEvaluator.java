/**
 * Synopsys Coding Assignment
 */
package com.synopsys.codingassignment.commandLineCalculator.calculate;

import static com.synopsys.codingassignment.commandLineCalculator.utils.Constants.ADD;
import static com.synopsys.codingassignment.commandLineCalculator.utils.Constants.DIVISION;
import static com.synopsys.codingassignment.commandLineCalculator.utils.Constants.MULTIPLY;
import static com.synopsys.codingassignment.commandLineCalculator.utils.Constants.SUBSTRACT;

import java.util.List;
import java.util.Objects;

import com.synopsys.codingassignment.commandLineCalculator.dataType.Token;
import com.synopsys.codingassignment.commandLineCalculator.state.Expression;
import com.synopsys.codingassignment.commandLineCalculator.utils.TokenType;

/**
 * @author Amandeep Singh
 * @see <a href="https://www.linkedin.com/in/imamanrana/" target=
 *      "_blank">LinkedIn Profile</a>
 *
 */
public class ExpressionEvaluator {

	public static Token evaluate(final Expression exp) {

		List<Token> tokens = exp.getTokens();
		int counter = 0, tempCounter = 0;
		Token result = null;
		while (counter < tokens.size())
			switch (tokens.get(counter).getTokenType()) {

			case OPERATOR:
				tempCounter = fetchNonNullNextToken(tokens, counter + 1);
				Token operand1 = tokens.get(tempCounter);
				tempCounter = fetchNonNullNextToken(tokens, tempCounter + 1);
				Token operand2 = tokens.get(tempCounter);
				result = evaluateExpression(tokens.get(counter), operand1, operand2);
				counter = tempCounter + 1;
				break;
			default:
				counter++;
				break;

			}
		return result;
	}

	/**
	 * @param tokens
	 * @param counter
	 * @return
	 */
	private static int fetchNonNullNextToken(final List<Token> tokens, final int counter) {
		int index = -1;
		for (int i = counter; i < tokens.size(); i++) {
			if (Objects.nonNull(tokens.get(i))) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * @param list
	 */
	public static void evaluateVariables(final List<Token> list) {

		int counter = 0;
		Token value;
		while (counter < list.size()) {

			if (Objects.isNull(list.get(counter)) || list.get(counter).getTokenType() != TokenType.CHARACTER) {
				counter++;
			} else {
				value = computeVariableValue(list.get(counter), counter, list);
				replaceVariableInExpression(list, counter, list.get(counter), value);
				counter++;
			}
		}
	}

	private static Token computeVariableValue(final Token variable, final int currentIndex, final List<Token> list) {

		boolean isEvaluated = false;
		Token value = null;
		int counter = currentIndex + 1;

		while (!isEvaluated && counter < list.size()) {
			switch (list.get(counter).getTokenType()) {
			case OPERATOR:
				value = evaluateExpression(list.get(counter), list.get(counter + 1), list.get(counter + 2));
				list.set(currentIndex, null);
				list.set(counter, null);
				list.set(counter + 1, null);
				list.set(counter + 2, null);
				counter += 2;
				replaceVariableInExpression(list, counter, variable, value);
				break;
			case CHARACTER:
				value = computeVariableValue(list.get(counter), counter, list);
				break;
			case INTEGER:
				value = list.get(counter);
				list.set(currentIndex, null);
				list.set(counter, null);
				counter++;
				replaceVariableInExpression(list, counter, variable, value);
				break;
			}

			isEvaluated = true;
		}

		return value;
	}

	/**
	 * @param tokens
	 * @param variable
	 * @param value
	 */
	private static int replaceVariableInExpression(final List<Token> tokens, final int startIndex, final Token variable,
			final Token value) {
		int counter = startIndex;
		int lastIndexForToken = -1;
		boolean isSameVariableDefinedAgain = false;
		while (!isSameVariableDefinedAgain && counter < tokens.size()) {

			if (Objects.nonNull(tokens.get(counter)) && tokens.get(counter).equals(variable)) {

				if ((counter + 1) < tokens.size() && Objects.nonNull(tokens.get(counter + 1))
						&& tokens.get(counter + 1).getTokenType() == TokenType.INTEGER && (counter - 1) >= 0
						&& tokens.get(counter - 1).getTokenType() != TokenType.OPERATOR) {
					isSameVariableDefinedAgain = true;
				} else {
					tokens.set(counter, value);
					lastIndexForToken = counter;
				}
			}

			counter++;
		}

		return lastIndexForToken;
	}

	/**
	 * @param token
	 * @param token1
	 * @param token2
	 * @return
	 */
	private static Token evaluateExpression(Token token, Token token1, Token token2) {
		Long result = null;
		switch (token.getValue().toString()) {
		case ADD:
			result = Long.valueOf(token1.getValue().toString()) + Long.valueOf(token2.getValue().toString());
			break;
		case SUBSTRACT:
			result = Long.valueOf(token1.getValue().toString()) - Long.valueOf(token2.getValue().toString());
			break;
		case MULTIPLY:
			result = Long.valueOf(token1.getValue().toString()) * Long.valueOf(token2.getValue().toString());
			break;
		case DIVISION:
			result = Long.valueOf(token1.getValue().toString()) / Long.valueOf(token2.getValue().toString());
			break;
		}

		return new Token(result, TokenType.INTEGER);
	}

}
