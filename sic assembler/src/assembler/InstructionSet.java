package assembler;

import java.util.HashMap;

public class InstructionSet {
	private static final HashMap<String, String> opTable = createMap();
	private static HashMap<String, String> createMap(){
		HashMap<String, String> opTable = new HashMap<String, String>();
		opTable.put("ADD", "18");
		opTable.put("AND", "40");
		opTable.put("COMP", "28");
		opTable.put("DIV", "24");
		opTable.put("J", "3C");
		opTable.put("JEQ", "30");
		opTable.put("JGT", "34");
		opTable.put("JLT", "38");
		opTable.put("JSUB", "48");
		opTable.put("LDA", "00");
		opTable.put("LDCH", "50");
		opTable.put("LDL", "08");
		opTable.put("LDX", "04");
		opTable.put("MUL", "20");
		opTable.put("OR", "44");
		opTable.put("RSUB", "4C");
		opTable.put("STA", "0C");
		opTable.put("STCH", "54");
		opTable.put("STL", "14");
		opTable.put("STSW", "E8");
		opTable.put("STX", "10");
		opTable.put("SUB", "1C");
		opTable.put("TD", "E0");
		opTable.put("TIX", "2C");
		opTable.put("WD", "DC");
		return opTable;
	}
	
	public static boolean ifFound(String s) {
		if (opTable.containsKey(s))
			return true;
		return false;
	}
}
