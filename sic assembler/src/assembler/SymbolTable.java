import java.util.HashMap;

public class SymbolTable {
	private  HashMap<String, Integer> symTable ;
	public SymbolTable(){
		this.symTable = new HashMap<>();
	
	}
	
	public boolean addSymbol(String s, int loc) {
		if (symTable.containsKey(s))
			return false;
		
		symTable.put(s, loc);
		return true;
	}
	
	public int getSymbol(String s) {
		return symTable.get(s);
	}

}
