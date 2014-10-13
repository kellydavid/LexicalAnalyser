
public class LexicalToken {
	
	public enum TokenClass {INTEGER, OCTAL, HEXADECIMAL};
	
	private TokenClass tClass;
	private String tValue;
	private int inttValue;
	
	public LexicalToken(TokenClass tClass, String tValue){
		this.tClass = tClass;
		this.tValue = tValue;
		inttValue = toInt(tClass, tValue);
	}
	
	public TokenClass gettClass(){
		return tClass;
	}
	
	public String gettValue(){
		return tValue;
	}
	
	public String toString(){
		return "(" + tClass.toString() + ", " + tValue + 
				") Integer Representation: " + inttValue;
	}
	
	private int toInt(TokenClass numType, String input){
		int value = 0;
		assert(input != "");
		char[] inputChars = input.toCharArray();
		if(numType == TokenClass.INTEGER){
			boolean negative = false;
			int i = 0;
			if(inputChars[0] == '-' || inputChars[0] == '+'){
				i = 1;
				negative = (inputChars[0] == '-' ? true:false);
			}
			int counter = 1;
			for(;i < inputChars.length; i++, counter *= 10){
				value += Character.getNumericValue(inputChars[i]) * counter;
			}
			if(negative)
				value *= -1;
		}
		else if(numType == TokenClass.OCTAL){
			
		}
		else if(numType == TokenClass.HEXADECIMAL){
			
		}
		else{
			
		}
		return value;
	}
}
