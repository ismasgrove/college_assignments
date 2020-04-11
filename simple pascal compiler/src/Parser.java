
public class Parser {

	private boolean found = false;
	private Classifier classifier = new Classifier();
	private LexScanner lexScanner = new LexScanner();

	public boolean read() {
		if (classifier.regex(lexScanner.getLine()) == 4) {
			// advance
		}

		return true;
	}

	public boolean idlist() {
		//obtain id-list string from code
		
		return true;
	}
	
	public boolean assign() {
		
		return true;
	}
	
	public boolean exp() {
		
		return true;
	}
	
	public boolean term() {
	
		return true;
	}
	
	public boolean factor() {
		
		return true;
	}
	
	//...
}
