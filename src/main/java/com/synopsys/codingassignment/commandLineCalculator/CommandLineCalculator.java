/**
 * Synopsys Coding Assignment
 */
package com.synopsys.codingassignment.commandLineCalculator;

import java.io.IOException;
import java.util.List;

import com.synopsys.codingassignment.commandLineCalculator.calculate.ExpressionEvaluator;
import com.synopsys.codingassignment.commandLineCalculator.parser.InputParser;
import com.synopsys.codingassignment.commandLineCalculator.reader.InputReader;
import com.synopsys.codingassignment.commandLineCalculator.state.Expression;

/**
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

		InputReader ir = new InputReader();
		List<Expression> expression = ir.readInput();

		// System.out.println(input);

		expression.stream().forEach(exp -> {
			try {
				exp.setTokens(InputParser.parseToPreFixNotation(exp));
				System.out.println(exp.getTokens());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		

		// validate Expressions
		// TODO

		// compute variables
		ExpressionEvaluator.evaluateVariables(expression.get(0).getTokens());
		System.out.println(expression.get(0).getTokens());
		// evaluate expression
		//System.out.println(ExpressionEvaluator.evaluate(expression.get(0)));

	}

}
