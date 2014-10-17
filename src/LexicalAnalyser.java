/**
 * @author David Kelly 17/10/2014
 * 
 * This class takes care of converting a string input into a Lexical Token using the analyseString method.
 */
public class LexicalAnalyser {
	
	public static final char END_MARKER = '.';
	public static final int MAX_INPUT_LENGTH = 20;
	
	/**
	 * Performs a lexical analysis of a string and returns the correct lexical token.
	 * @param input
	 * @return Returns lexical token if valid input or null otherwise.
	 */
	public static LexicalToken analyseString(String input) throws Exception{
		/*
	
	The state machine uses the following transition table:
	
	____|_OCT_|_DEC_|_bB_|_HEX_|_sign_|_hH_|_-1_|
	  1 |  2  |  3  |    |  4  |  5   |    |    |
	---------------------------------------------
	  2 |  2  |  3  |  7 |  4  |      |  8 |DEC |
	---------------------------------------------
	  3 |     |  3  |    |  4  |      |  8 |DEC |
	---------------------------------------------
	  4 |     |  4  |    |  4  |      |  8 |    |
	---------------------------------------------
	  5 |     |  6  |    |     |      |    |    |
	---------------------------------------------
	  6 |     |  6  |    |     |      |    |DEC |
	---------------------------------------------
	  7 |     |  4  |    |  4  |      |  8 |OCT |
	---------------------------------------------
	  8 |     |     |    |     |      |    |HEX |
	---------------------------------------------
		 */
		if(input.length() > MAX_INPUT_LENGTH)
			throw new Exception("Exceeds maximum length of input.");
		//append end marker
		if(input.contains(END_MARKER + ""))
			throw new Exception("Invalid Input.");//throw exception
		String processInput = input + END_MARKER;
		int machineState = 1;
		int nextState = 1;
		char[] inChars = processInput.toCharArray();
		char current_char;
		for(int i = 0; i < inChars.length; i++){
			current_char = inChars[i];
			switch(machineState){
			case 1:
				if(isOct(current_char))
					nextState = 2;
				else if(isDec(current_char))
					nextState = 3;
				else if(isHex(current_char))
					nextState = 4;
				else if(isSign(current_char))
					nextState = 5;
				else
					throw new Exception("No input.");
				break;
			case 2:
				if(isOct(current_char))
					nextState = 2;
				else if(isDec(current_char))
					nextState = 3;
				else if(isB(current_char))
					nextState = 7;
				else if(isHex(current_char))
					nextState = 4;
				else if(isH(current_char))
					nextState = 8;
				else if(isEndMarker(current_char))
					return new LexicalToken(LexicalToken.TokenClass.INTEGER, input);
				else
					throw new Exception("Invalid Input.");
				break;
			case 3:
				if(isDec(current_char))
					nextState = 3;
				else if(isHex(current_char))
					nextState = 4;
				else if(isH(current_char))
					nextState = 8;
				else if(isEndMarker(current_char))
					return new LexicalToken(LexicalToken.TokenClass.INTEGER, input);
				else
					throw new Exception("Invalid Input.");
				break;
			case 4:
				if(isDec(current_char) || isHex(current_char))
					nextState = 4;
				else if(isH(current_char))
					nextState = 8;
				else
					throw new Exception("Invalid Input.");
				break;
			case 5:
				if(isDec(current_char))
					nextState = 6;
				else 
					return null;
				break;
			case 6:
				if(isDec(current_char))
					nextState = 6;
				else if(isEndMarker(current_char))
					return new LexicalToken(LexicalToken.TokenClass.INTEGER, input);
				else
					throw new Exception("Invalid Input.");
				break;
			case 7:
				if(isDec(current_char) || isHex(current_char))
					nextState = 4;
				else if(isH(current_char))
					nextState = 8;
				else if(isEndMarker(current_char))
					return new LexicalToken(LexicalToken.TokenClass.OCTAL, input);
				else 
					throw new Exception("Invalid Input.");
				break;
			case 8:
				if(isEndMarker(current_char))
					return new LexicalToken(LexicalToken.TokenClass.HEXADECIMAL, input);
				else
					throw new Exception("Invalid Input.");
			default:
				throw new Exception("Invalid Input.");
			}
			machineState = nextState;
		}
		return null;
	}
	
	/**
	 * Checks if a character is an octal number.
	 * @param c
	 * @return True if it is an octal number, false otherwise.
	 */
	private static boolean isOct(char c){
		int val = Character.getNumericValue(c);
		if(val >= 0 && val <= 7)
			return true;
		else return false;
	}
	
	/**
	 * Checks if a character is a decimal value.
	 * @param c
	 * @return True if decimal value. False otherwise.
	 */
	private static boolean isDec(char c){
		int val = Character.getNumericValue(c);
		if(val >= 0 && val <= 9)
			return true;
		else
			return false;
	}

	/**
	 * Checks if a character is a hexadecimal.
	 * @param c
	 * @return True if hex. False otherwise.
	 */
	private static boolean isHex(char c){
		if(isDec(c))
			return true;
		else if((c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'))
			return true;
		else
			return false;
	}

	/**
	 * Checks if character is a 'b' or a 'B'
	 * @param c
	 * @return
	 */
	private static boolean isB(char c){
		if(c == 'b' || c == 'B')
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if a character is a 'h' or a 'H'.
	 * @param c
	 * @return
	 */
	private static boolean isH(char c){
		if(c == 'h' || c == 'H')
			return true;
		else
			return false;
	}

	/**
	 * Checks if a character is a '-' or a '+'.
	 * @param c
	 * @return
	 */
	private static boolean isSign(char c){
		if(c == '-' || c == '+')
			return true;
		else
			return false;
	}

	/**
	 * Checks for end marker.
	 * @param c
	 * @return
	 */
	private static boolean isEndMarker(char c){
		return(c == END_MARKER);
	}
}
