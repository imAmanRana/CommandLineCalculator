/**
 * Synopsys Coding Assignment
 */
package com.synopsys.codingassignment.commandLineCalculator.dataType;

import com.synopsys.codingassignment.commandLineCalculator.utils.TokenType;

/**
 * Single non-breakable unit of expression
 * 
 * @author Amandeep Singh
 * @see <a href="https://www.linkedin.com/in/imamanrana/" target=
 *      "_blank">LinkedIn Profile</a>
 *
 */
public class Token {

	private Object value;
	private TokenType tokenType;

	public Token(Object value, TokenType tokenType) {

		this.value = value;
		this.tokenType = tokenType;

	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @return the tt
	 */
	public TokenType getTokenType() {
		return tokenType;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (tokenType != other.tokenType)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
