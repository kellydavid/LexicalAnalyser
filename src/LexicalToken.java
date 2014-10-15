import java.util.*;


public class LexicalToken {
	
	public enum TokenClass {INTEGER, OCTAL, HEXADECIMAL};
	
	private TokenClass tClass;
	private String tValue;
	private int inttValue;
	
	public LexicalToken(TokenClass tClass, String tValue){
		this.tClass = tClass;
		this.tValue = tValue;
		inttValue = toInt();
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
	private boolean checkOverflow(){
		assert(tValue != "");
		assert(!this.hasLeadingZeros());
		char[] inputChars = tValue.toCharArray();
		if(tClass == TokenClass.INTEGER){
			//check for min value
			int min = Integer.MIN_VALUE;
			if(inputChars[0] == '-'){
				
			}
		}
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
