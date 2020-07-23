/**
 * Synopsys Coding Assignment
 */
package com.synopsys.codingassignment.commandLineCalculator.exception;

/**
 * Custom class for handling calculator expceptions
 * 
 * @author Amandeep Singh
 * @see <a href="https://www.linkedin.com/in/imamanrana/" target=
 *      "_blank">LinkedIn Profile</a>
 *
 */
public class CalculatorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CalculatorException(String message) {
		super(message);
	}

}
