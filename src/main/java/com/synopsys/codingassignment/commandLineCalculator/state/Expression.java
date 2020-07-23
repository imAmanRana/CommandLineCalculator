/**
 * Synopsys Coding Assignment
 */
package com.synopsys.codingassignment.commandLineCalculator.state;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.synopsys.codingassignment.commandLineCalculator.dataType.Token;

/**
 * Holds the state of the program
 * @author Amandeep Singh
 * @see <a href="https://www.linkedin.com/in/imamanrana/" target=
 *      "_blank">LinkedIn Profile</a>
 *
 */
public class Expression {

	private String input;
	private int parenthesisCount = 0;
	private Set<String> variables;
	private List<Token> tokens;

	/**
	 * @return the input
	 */
	public String getInput() {
		return input;
	}

	/**
	 * @param input the input to set
	 */
	public void setInput(String input) {
		this.input = input;
	}

	/**
	 * @return the parenthesisCount
	 */
	public int getParenthesisCount() {
		return parenthesisCount;
	}

	/**
	 * @param parenthesisCount the parenthesisCount to set
	 */
	public void setParenthesisCount(int parenthesisCount) {
		this.parenthesisCount = parenthesisCount;
	}

	/**
	 * @return the variables
	 */
	public Set<String> getVariables() {
		if (Objects.isNull(variables)) {
			variables = new HashSet<>();
		}

		return variables;
	}

	/**
	 * @param variables the variables to set
	 */
	public void setVariables(Set<String> variables) {
		this.variables = variables;
	}

	/**
	 * @return the tokens
	 */
	public List<Token> getTokens() {
		return tokens;
	}

	/**
	 * @param tokens the tokens to set
	 */
	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public boolean hasVariables() {
		return Objects.nonNull(variables) && variables.size() > 0;
	}

}
