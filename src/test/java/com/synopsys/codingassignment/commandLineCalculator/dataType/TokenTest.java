/**
 * Synopsys Coding Assignment
 */
package com.synopsys.codingassignment.commandLineCalculator.dataType;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.synopsys.codingassignment.commandLineCalculator.utils.TokenType;

/**
 * @author Amandeep Singh
 * @see <a href="https://www.linkedin.com/in/imamanrana/" target=
 *      "_blank">LinkedIn Profile</a>
 *
 */
public class TokenTest {

	Token t;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		t = new Token("a", TokenType.CHARACTER);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		t = null;
	}

	/**
	 * Test method for
	 * {@link com.synopsys.codingassignment.commandLineCalculator.dataType.Token#toString()}.
	 */
	@Test
	public final void testToString() {
		assertThat(t.toString(), is("a"));
	}

	/**
	 * Test method for
	 * {@link com.synopsys.codingassignment.commandLineCalculator.dataType.Token#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject() {
		assertThat(false, is(t.equals(null)));
		assertThat(true, is(t.equals(t)));
	}

}
