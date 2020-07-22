package com.synopsys.codingassignment.commandLineCalculator.toDelete;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Test {

	private static final int QUOTE_CHARACTER = '\'';
	private static final int DOUBLE_QUOTE_CHARACTER = '"';

	public static void main(String[] args) throws IOException {
		String line;
		
		long l = Integer.MAX_VALUE+(long)Integer.MAX_VALUE;
		System.out.println(l);
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));) {
			
			while((line=br.readLine())!=null) {
				System.out.println(streamTokenizerWithDefaultConfiguration(new StringReader(line)));
			}
		}

	}

	public static List<Object> streamTokenizerWithDefaultConfiguration(Reader reader) throws IOException {
		StreamTokenizer streamTokenizer = new StreamTokenizer(reader);
		List<Object> tokens = new ArrayList<Object>();

		int currentToken;
		while ((currentToken=streamTokenizer.nextToken()) != StreamTokenizer.TT_EOF) {

			if (streamTokenizer.ttype == StreamTokenizer.TT_NUMBER) {
				tokens.add(streamTokenizer.nval);
			} else if (streamTokenizer.ttype == StreamTokenizer.TT_WORD || streamTokenizer.ttype == QUOTE_CHARACTER
					|| streamTokenizer.ttype == DOUBLE_QUOTE_CHARACTER) {
				tokens.add(streamTokenizer.sval);
			} else {
				tokens.add((char) currentToken);
			}

			currentToken = streamTokenizer.nextToken();
		}
		return tokens;
	}

}
