/**
 * Synopsys Coding Assignment
 */
package com.synopsys.codingassignment.commandLineCalculator;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.synopsys.codingassignment.commandLineCalculator.calculate.ExpressionEvaluator;
import com.synopsys.codingassignment.commandLineCalculator.dataType.Token;
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

	private final static Logger LOGGER = Logger.getLogger(CommandLineCalculator.class.getName());

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		if (args.length < 1) {
			printUsage();
			throw new CalculatorException("Incorrect Usage");
		}

		// setup logger
		configureLogger(args.length >= 2 ? args[1] : "WARNING");

		//setup timer for program execution
		Instant startTime = Instant.now();
		
		Expression exp = new Expression();

		// set the input
		exp.setInput(args[0]);
		LOGGER.info("Input Expression : " + exp.getInput());

		// tokenize & convert input to pre-fix notation
		exp.setTokens(InputParser.parseToPreFixNotation(exp));
		LOGGER.info("Pre-fix Notation : " + exp.getTokens());

		// validate Expression
		if (exp.getParenthesisCount() != 0) {
			LOGGER.severe("Parenthesis Mismatch");
			throw new CalculatorException("Mismatching parenthesis");
		}

		// compute variables -> Type-erasure
		if (exp.hasVariables()) {
			ExpressionEvaluator.evaluateVariables(exp.getTokens());
			LOGGER.info("Pre-fix Notation after type erasure : " + exp.getTokens());
		}

		// evaluate expression
		Token output = ExpressionEvaluator.evaluate(exp);
		LOGGER.info("Program completed in  : " + Duration.between(startTime, Instant.now()));
		LOGGER.info("Output : " + output.toString());
		System.out.println(output);
		
	}

	/**
	 * Configures Logger in the application<br>
	 * uses {@link java.util.logging.Logger}
	 * 
	 * @param logLevel log level
	 * @return Log level, defaults to WARNING
	 */
	private static void configureLogger(final String logLevel) {
		Level level;
		try {
			level = Level.parse(mapToJavaLoggingLevel(logLevel));

		} catch (NullPointerException | IllegalArgumentException exp) {
			level = Level.WARNING;
		}
		Logger rootLogger = LogManager.getLogManager().getLogger("");
		rootLogger.setLevel(level);
		for (Handler h : rootLogger.getHandlers()) {
			h.setLevel(level);
		}

	}

	/**
	 * Maps Log4j log levels to Java util logging<br>
	 * 
	 * @see {@link http://www.slf4j.org/apidocs/org/slf4j/bridge/SLF4JBridgeHandler.html}
	 * @see {@link https://stackoverflow.com/questions/20795373/how-to-map-levels-of-java-util-logging-and-slf4j-logger}
	 * @param logLevel
	 * @return compatable log level
	 */
	private static String mapToJavaLoggingLevel(String logLevel) {
		String mappedLevel = logLevel;
		switch (logLevel) {
		case "TRACE":
			mappedLevel = "FINEST";
			break;
		case "DEBUG":
			mappedLevel = "FINE";
			break;
		case "WARN":
			mappedLevel = "WARNING";
			break;
		case "ERROR":
			mappedLevel = "SEVERE";
			break;
		}
		return mappedLevel;
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
