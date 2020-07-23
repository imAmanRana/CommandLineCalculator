/**
 * Synopsys Coding Assignment
 */
package com.synopsys.codingassignment.commandLineCalculator.calculate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.synopsys.codingassignment.commandLineCalculator.dataType.Token;
import com.synopsys.codingassignment.commandLineCalculator.parser.InputParser;
import com.synopsys.codingassignment.commandLineCalculator.state.Expression;
import com.synopsys.codingassignment.commandLineCalculator.utils.TokenType;

/**
 * @author Amandeep Singh
 * @see <a href="https://www.linkedin.com/in/imamanrana/" target="_blank">LinkedIn Profile</a>
 *
 */
public class ExpressionEvaluatorTest {

	private Expression input;
	private List<Token> actual = new ArrayList<>();
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		input = new Expression();
		input.setInput("let(a,30,div(a,10))");
		actual.add(new Token("a", TokenType.CHARACTER));
		actual.add(new Token(Integer.valueOf("30"), TokenType.NUMBER));
		actual.add(new Token("/", TokenType.OPERATOR));
		actual.add(new Token("a", TokenType.CHARACTER));
		actual.add(new Token(Integer.valueOf("10"), TokenType.NUMBER));
		input.setTokens(InputParser.parseToPreFixNotation(input));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		input = null;
		actual = null;
	}

	/**
	 * Test method for {@link com.synopsys.codingassignment.commandLineCalculator.calculate.ExpressionEvaluator#evaluate(com.synopsys.codingassignment.commandLineCalculator.state.Expression)}.
	 */
	@Test
	public final void testEvaluate() {
		actual.set(0, null);
		actual.set(1, null);
		actual.set(3, new Token(Integer.parseInt("30"),TokenType.NUMBER));
		Token result = new Token(Long.parseLong("3"), TokenType.NUMBER);
		ExpressionEvaluator.evaluateVariables(input.getTokens());
		Token t = ExpressionEvaluator.evaluate(input);
		
		assertThat(result, is(t));
	}

	/**
	 * Test method for {@link com.synopsys.codingassignment.commandLineCalculator.calculate.ExpressionEvaluator#evaluateVariables(java.util.List)}.
	 */
	@Test
	public final void testEvaluateVariables() {
		actual.set(0, null);
		actual.set(1, null);
		actual.set(3, new Token(Integer.parseInt("30"),TokenType.NUMBER));
		
		ExpressionEvaluator.evaluateVariables(input.getTokens());
		assertThat(actual, is(input.getTokens()));
	}

}
