
public class LexicalAnalyserApp {
	
	public static void main(String[] args){
		String test = "2345";
		LexicalToken lToken = LexicalAnalyser.analyseString(test);
		System.out.println(lToken.toString() + "\n");
	}
	
}
