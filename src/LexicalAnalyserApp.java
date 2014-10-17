
/**
 * @author David Kelly 17/10/2014
 *
 * This is the main class of the application.
 */
public class LexicalAnalyserApp {
	
	public static void main(String[] args){
		if(args.length != 1){
			System.out.println("Please enter one input string argument.");
			return;
		}
		String input = args[0];
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
