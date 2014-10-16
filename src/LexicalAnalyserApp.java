
public class LexicalAnalyserApp {
	
	public static void main(String[] args){
		String input = "100000000000000000000000000000000000000000000000";
		LexicalToken lToken = null;
		try{
			lToken = LexicalAnalyser.analyseString(input);
			System.out.println(lToken.toString() + "\n");
		}
		catch(Exception e){
			System.out.println("Error: " + e.getMessage() + "\nFor input: " + input);
		}
	}
	
}
