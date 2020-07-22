/**
 * Synopsys Coding Assignment
 */
package com.synopsys.codingassignment.commandLineCalculator.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.synopsys.codingassignment.commandLineCalculator.state.Expression;

/**
 * @author Amandeep Singh
 * @see <a href="https://www.linkedin.com/in/imamanrana/" target=
 *      "_blank">LinkedIn Profile</a>
 *
 */
public class InputReader {

	public List<Expression> readInput() throws IOException {

		List<Expression> input = new ArrayList<>();
		Expression exp;
		String line;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			while ((line = br.readLine()) != null) {
				exp = new Expression();
				exp.setInput(line);
				input.add(exp);
			}
		}
		return input;
	}

}
