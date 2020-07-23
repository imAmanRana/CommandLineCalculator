/**
 * Synopsys Coding Assignment
 */
package com.synopsys.codingassignment.commandLineCalculator.parser;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.synopsys.codingassignment.commandLineCalculator.dataType.Token;
import com.synopsys.codingassignment.commandLineCalculator.state.Expression;
import com.synopsys.codingassignment.commandLineCalculator.utils.TokenType;

/**
 * @author Amandeep Singh
 * @see <a href="https://www.linkedin.com/in/imamanrana/" target=
 *      "_blank">LinkedIn Profile</a>
 *
 */
public class InputParserTest {

	private static Expression input;
	private static List<Token> actual = new ArrayList<>();

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		input = new Expression();
		input.setInput("let(a,30,div(a,10))");
		actual.add(new Token("a", TokenType.CHARACTER));
		actual.add(new Token(Integer.valueOf("30"), TokenType.NUMBER));
		actual.add(new Token("/", TokenType.OPERATOR));
		actual.add(new Token("a", TokenType.CHARACTER));
		actual.add(new Token(Integer.valueOf("10"), TokenType.NUMBER));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		input = null;
		actual = null;
	}

	/**
	 * Test method for
	 * {@link com.synopsys.codingassignment.commandLineCalculator.parser.InputParser#parseToPreFixNotation(com.synopsys.codingassignment.commandLineCalculator.state.Expression)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testParseToPreFixNotation() throws IOException {

		assertThat(actual, is(InputParser.parseToPreFixNotation(input)));
	}

}
