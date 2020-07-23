/**
 * Synopsys Coding Assignment
 */
package com.synopsys.codingassignment.commandLineCalculator;

import java.io.IOException;

import com.synopsys.codingassignment.commandLineCalculator.calculate.ExpressionEvaluator;
import com.synopsys.codingassignment.commandLineCalculator.exception.CalculatorException;
import com.synopsys.codingassignment.commandLineCalculator.parser.InputParser;
import com.synopsys.codingassignment.commandLineCalculator.state.Expression;

/**
 * Starting point for Calculator
 * 
 * @author Amandeep Singh
 * @see <a href="https://www.linkedin.com/in/imamanrana/" target=
 *      "_blank">LinkedIn Profile</a>
 *
 */
public class CommandLineCalculator {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		if (args.length < 1) {
			printUsage();
			throw new CalculatorException("Incorrect Usage");
		}

		Expression exp = new Expression();

		// set the input
		exp.setInput(args[0]);

		// tokenize & convert input to pre-fix notation
		exp.setTokens(InputParser.parseToPreFixNotation(exp));
		System.out.println(exp.getTokens());

		// validate Expression
		if (exp.getParenthesisCount() != 0) {
			throw new CalculatorException("Missmatching parenthesis");
		}

		// compute variables -> Type-erasure
		if (exp.hasVariables()) {
			ExpressionEvaluator.evaluateVariables(exp.getTokens());
		}

		// evaluate expression
		System.out.println(ExpressionEvaluator.evaluate(exp));
	}

	// Prints program Usage
	public static void printUsage() {
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("+              Command Line Calculator                 +");
		System.out.println(
				"+ Usage : java com.synopsys.codingassignment.commandLineCalculator.CommandLineCalculator \"add(1,2)\"");
		System.out.println("+ Example Inputs:");
		System.out.println("+ 		add(1, mult(2, 3))");
		System.out.println("+ 		let(a, 5, add(a, a))");
		System.out.println("+ 		let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

}
