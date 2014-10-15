
public class LexicalAnalyserApp {
	
	public static void main(String[] args){
		String test = "AE56h";
		LexicalToken lToken = LexicalAnalyser.analyseString(test);
		System.out.println(lToken.toString() + "\n");
	}
	
}
