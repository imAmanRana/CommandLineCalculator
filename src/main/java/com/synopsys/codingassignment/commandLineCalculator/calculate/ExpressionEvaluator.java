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
import com.synopsys.codingassignment.commandLineCalculator.exception.CalculatorException;
import com.synopsys.codingassignment.commandLineCalculator.state.Expression;
import com.synopsys.codingassignment.commandLineCalculator.utils.TokenType;

/**
 * Expression Evaluator class.
 * 
 * @author Amandeep Singh
 * @see <a href="https://www.linkedin.com/in/imamanrana/" target=
 *      "_blank">LinkedIn Profile</a>
 *
 */
public class ExpressionEvaluator {

	/**
	 * <p>
	 * Performs input evaluation.<br>
	 * All the variables has been computed and inserted at their proper place.
	 * </p>
	 * 
	 * @param exp - input expression in pre-fix notation with variables type-erased
	 * @return evaluated value
	 */
	public static Token evaluate(final Expression exp) {

		List<Token> tokens = exp.getTokens();
		int counter = 0;
		Token result = null;
		while (counter < tokens.size()) {

			/*
			 * We only have Operators (+,-,*,/) at this point
			 */
			if (Objects.isNull(tokens.get(counter)) || tokens.get(counter).getTokenType() != TokenType.OPERATOR) {
				// skip, do nothing
			} else {
				result = evaluateOperator(tokens.get(counter), counter, tokens, 0);
			}

			counter++;

		}
		return result;
	}

	/**
	 * Returns the first token starting from <code>startIndex</code> which is not
	 * null
	 * 
	 * @param tokens     - input list of pre-fix notation tokens
	 * @param startIndex - starting index to search from
	 * @return the first non null token index
	 */
	private static int fetchNonNullNextTokenIndex(final List<Token> tokens, final int startIndex) {
		int index = -1;
		for (int i = startIndex; i < tokens.size(); i++) {
			if (Objects.nonNull(tokens.get(i))) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * Evaluates the variables in expression and updates the expression's token list
	 * 
	 * @param tokens - input list of pre-fix notation tokens
	 */
	public static void evaluateVariables(final List<Token> tokens) {

		int counter = 0;
		while (counter < tokens.size()) {

			/*
			 * Only deals with variables at this point
			 */
			if (Objects.isNull(tokens.get(counter)) || tokens.get(counter).getTokenType() != TokenType.CHARACTER) {
				// do nothing
			} else {
				computeVariableValue(tokens.get(counter), counter, tokens);
			}
			counter++;
		}
	}

	/**
	 * Recursively computes the variable value
	 * 
	 * @param variable   - variable to compute
	 * @param startIndex - index to start looking into the list
	 * @param tokenList  - pre-fix notation token list
	 * @return computed value of <code>variable</code>
	 */
	private static Token computeVariableValue(final Token variable, final int startIndex, final List<Token> tokenList) {

		Token value = null;
		int counter = startIndex + 1;

		if (Objects.isNull(tokenList.get(counter))) {
			counter++;
		} else {
			switch (tokenList.get(counter).getTokenType()) {
			case NUMBER:
				value = tokenList.get(counter);
				tokenList.set(counter, null);
				break;
			case CHARACTER:
				value = computeVariableValue(tokenList.get(counter), counter, tokenList);
				value = checkAndComputeEffectiveValue(counter, value, tokenList);
				break;
			case OPERATOR:
				value = evaluateOperator(tokenList.get(counter), counter, tokenList, 0);
				break;
			}
		}

		if (Objects.nonNull(value)) {
			replaceVariableInExpression(tokenList, startIndex, variable, value);
		}
		tokenList.set(startIndex, null);

		return value;
	}

	/**
	 * Handles cases like <br>
	 * <code>let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))</code><br>
	 * In this example, the value of 'a' variable is 20 NOT 10
	 * 
	 * @param startIndex    - index to start looking into the list
	 * @param computedValue - computed value of token
	 * @param tokenList     - pre-fix notation token list
	 * @return effective value of variable
	 */
	private static Token checkAndComputeEffectiveValue(final int startIndex, final Token computedValue,
			final List<Token> tokenList) {

		int index = fetchNonNullNextTokenIndex(tokenList, startIndex + 1);
		Token effectiveValue = null;
		Token operand1;
		if (index != -1) {
			switch (tokenList.get(index).getTokenType()) {
			case OPERATOR:
				if (index + 2 < tokenList.size()) {
					operand1 = tokenList.get(index + 1);

					if (operand1.getTokenType() != TokenType.CHARACTER) {
						effectiveValue = evaluateOperator(tokenList.get(index), index, tokenList, 0);
					}

				}
				break;
			default:
				break;
			}
		}

		return Objects.nonNull(effectiveValue) ? effectiveValue : computedValue;
	}

	/**
	 * Evaluates the operators (+,-,*,/)
	 * 
	 * @param operator   - operator to evaluate
	 * @param startIndex - index to start looking into the list
	 * @param tokenList  - pre-fix notation token list
	 * @param offset     - displacement from startIndex
	 * @return computed value of operator
	 */
	private static Token evaluateOperator(final Token operator, final int startIndex, final List<Token> tokenList,
			int offset) {

		if (startIndex + offset + 2 > tokenList.size()) {
			throw new CalculatorException("Inconsistant Program State");
		}
		Token operand, op1 = null, op2 = null, result;
		for (int i = 1; i < 3; i++) {
			operand = null;
			if (Objects.isNull(tokenList.get(startIndex + i + offset))) {
				continue;
			} else {
				switch (tokenList.get(startIndex + i + offset).getTokenType()) {

				case NUMBER:
					operand = tokenList.get(startIndex + i + offset);
					tokenList.set(startIndex + i + offset, null);
					break;
				case CHARACTER:
					operand = computeVariableValue(tokenList.get(startIndex + i + offset), startIndex + i, tokenList);
					break;
				case OPERATOR:
					operand = evaluateOperator(tokenList.get(startIndex + i + offset), startIndex + i, tokenList,
							offset);
					offset += 2;
					break;
				}

				if (i == 1) {
					op1 = operand;
				} else {
					op2 = operand;
				}

			}

		}

		tokenList.set(startIndex + offset, null);
		result = evaluateExpression(operator, op1, op2);
		return result;
	}

	/**
	 * Type-erasure<br>
	 * Replaces the variable with its calculated value
	 * 
	 * @param tokenList  - pre-fix notation token list
	 * @param startIndex - index to start looking into the list
	 * @param variable   - variable to replace with <code>value</code>
	 * @param value      - computed value of the variable
	 */
	private static void replaceVariableInExpression(final List<Token> tokenList, final int startIndex,
			final Token variable, final Token value) {
		int counter = startIndex;
		boolean isSameVariableDefinedAgain = false;
		while (!isSameVariableDefinedAgain && counter < tokenList.size()) {

			if (Objects.nonNull(tokenList.get(counter)) && tokenList.get(counter).equals(variable)) {

				if ((counter + 1) < tokenList.size() && Objects.nonNull(tokenList.get(counter + 1))
						&& tokenList.get(counter + 1).getTokenType() == TokenType.NUMBER && (counter - 1) >= 0
						&& tokenList.get(counter - 1).getTokenType() != TokenType.OPERATOR) {
					isSameVariableDefinedAgain = true;
				} else {
					tokenList.set(counter, value);
				}
			}

			counter++;
		}
	}

	/**
	 * Performs the arithmetic calculations
	 * 
	 * @param operator /,*,-,+
	 * @param operand1 first parameter
	 * @param operand2 second parameter
	 * @return computed value of operator
	 */
	private static Token evaluateExpression(Token operator, Token operand1, Token operand2) {
		Long result = null;
		switch (operator.getValue().toString()) {
		case ADD:
			result = Long.valueOf(operand1.getValue().toString()) + Long.valueOf(operand2.getValue().toString());
			break;
		case SUBSTRACT:
			result = Long.valueOf(operand1.getValue().toString()) - Long.valueOf(operand2.getValue().toString());
			break;
		case MULTIPLY:
			result = Long.valueOf(operand1.getValue().toString()) * Long.valueOf(operand2.getValue().toString());
			break;
		case DIVISION:
			result = Long.valueOf(operand1.getValue().toString()) / Long.valueOf(operand2.getValue().toString());
			break;
		}

		return new Token(result, TokenType.NUMBER);
	}

}
