import java.util.*;

public class LexicalToken {
	
	public enum TokenClass {INTEGER, OCTAL, HEXADECIMAL};
	public static final String MAX_INTEGER_VALUE = String.valueOf(Integer.MAX_VALUE);
	public static final String MIN_INTEGER_VALUE = String.valueOf(Integer.MIN_VALUE);
	public static final String MAX_OCTAL_VALUE = "37777777777";
	public static final String MAX_HEX_VALUE = "FFFFFFFF";
	
	private TokenClass tClass;
	private String tValue;
	private int inttValue;
	
	public LexicalToken(TokenClass tClass, String tValue){
		this.tClass = tClass;
		this.tValue = tValue;
		if(!this.hasOverflow())
			inttValue = toInt();
		else
			System.out.println("OVERFLOW!!!");
	}
	
	public TokenClass gettClass(){
		return tClass;
	}
	
	public String gettValue(){
		return tValue;
	}
	
	public int getInttValue(){
		return inttValue;
	}
	
	public String toString(){
		return "(" + tClass.toString() + ", " + tValue + 
				") Integer Representation: " + inttValue;
	}
	
	/**
	 * Checks if the tValue will overflow when converted to 32-bit integer.
	 * @return True if overflow. False otherwise.
	 */
	private boolean hasOverflow(){
		assert(tValue != "");
		assert(!this.hasLeadingZeros());
		char[] inputChars = tValue.toCharArray();
		if(tClass == TokenClass.INTEGER){
			if(inputChars[0] == '+' || inputChars[0] != '-'){ // positive integer
				if(inputChars[0] == '+'){
					//remove plus sign
					char[] tempArray = new char[inputChars.length -1];
					System.arraycopy(inputChars, 1, tempArray, 0, tempArray.length);
					inputChars = tempArray;
				}
				// check if length is greater than max length
				if(inputChars.length > MAX_INTEGER_VALUE.length())
					return true; // overflow
				else if(inputChars.length == MAX_INTEGER_VALUE.length()){
					//check further
					char[] maxIntChars = MAX_INTEGER_VALUE.toCharArray();
					for(int i = 0; i < inputChars.length; i++){
						if(inputChars[i] > maxIntChars[i])
							return true; // overflow
						else if(inputChars[i] < maxIntChars[i])
							return false; // no overflow
						else
							continue;
					}
				}
				else //cant be overflow
					return false;
			}
			else{ // negative integer
				// remove negative sign
				char[] tempArray = new char[inputChars.length -1];
				System.arraycopy(inputChars, 1, tempArray, 0, tempArray.length);
				inputChars = tempArray;
				char[] minIntChars = MIN_INTEGER_VALUE.replace("-", "").toCharArray();
				// if length is greater than max length, there is an overflow
				if(inputChars.length > minIntChars.length)
					return true; // definitely overflow
				else if(inputChars.length == minIntChars.length){ // check further for overflow
					for(int i = 0; i < inputChars.length; i++){
						if(inputChars[i] > minIntChars[i])
							return true; // overflow
						else if(inputChars[i] < minIntChars[i])
							return false; // no overflow
						else
							continue;
					}
				}
				else	// cant be overflow
					return false;
			}
		}
		else if(tClass == LexicalToken.TokenClass.OCTAL){
			String tempInput = tValue.replace("b", "");
			inputChars = tempInput.replace("B", "").toCharArray();
			// overflow if input is greater length than max octal value
			if(inputChars.length > MAX_OCTAL_VALUE.length())
				return true;
			else if(inputChars.length == MAX_OCTAL_VALUE.length()){
				if(inputChars[0] > MAX_OCTAL_VALUE.toCharArray()[0])
					return true;
			}
			else
				return false;
		}
		else if(tClass == LexicalToken.TokenClass.HEXADECIMAL){
			String tempInput = tValue.replace("h", "");
			inputChars = tempInput.replace("H", "").toCharArray();
			// overflow if input is greater length than max hex value
			if(inputChars.length > MAX_HEX_VALUE.length())
				return true;
			else
				return false;
		}
		return false;
	}
	
	/**
	 * Checks if the tValue has leading zeros.
	 * @return True if tValue has leading zeros. False otherwise.
	 */
	private boolean hasLeadingZeros(){
		assert(tValue != "");
		char[] inputChars = tValue.toCharArray();
		if(tClass == TokenClass.INTEGER){
			if((inputChars.length > 2) && (inputChars[0] == '-' || inputChars[0] == '+') && (inputChars[1] == '0'))
				return true;
			else if(inputChars.length > 1 && inputChars[0] == '0')
				return true;
			else return false;
		}
		else{
			if(inputChars.length > 1 && inputChars[0] == '0')
				return true;
			else return false;
		}
	}
	
	/**
	 * Converts the tValue to an integer.
	 * @return Integer representation of the tValue.
	 */
	private int toInt(){
		int value = 0;
		assert(tValue != "");
		char[] inputChars = tValue.toCharArray();
		Stack<Character> stack = new Stack<Character>();
		for(int i = 0; i < inputChars.length; i++){
			stack.push(inputChars[i]);
		}
		if(tClass == TokenClass.INTEGER){
			char ch;
			for(int multiplier = 1; !stack.isEmpty() ; multiplier *= 10){
				ch = stack.pop();
				if(ch == '+')
					return value;
				else if(ch == '-')
					return -value;
				else
					value += Character.getNumericValue(ch) * multiplier;
			}
			return value;
		}
		else if(tClass == TokenClass.OCTAL){
			//pop off the b
			char ch = stack.pop();
			for(int multiplier = 1; !stack.isEmpty() ; multiplier *= 8){
				ch = stack.pop();
				value += Character.getNumericValue(ch) * multiplier;
			}
			return value;
		}
		else if(tClass == TokenClass.HEXADECIMAL){
			//pop off the h
			char ch = stack.pop();
			for(int multiplier = 1; !stack.isEmpty() ; multiplier *= 16){
				ch = stack.pop();
				value += Character.getNumericValue(ch) * multiplier;
			}
			return value;
		}
		else
			return value;
	}
}
