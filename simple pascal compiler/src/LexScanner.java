import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class LexScanner {
	private ArrayList<String> tokens = new ArrayList<>();
	private Map<String, Integer> map = createMap();
	private String line;
	private StringTokenizer st;

	private Map<String, Integer> createMap() {
		map = new HashMap<>();
		map.put("PROGRAM", 1);
		map.put("VAR", 2);
		map.put("BEGIN", 3);
		map.put("END", 4);
		map.put("END.", 5);
		map.put("FOR", 6);
		map.put("READ", 7);
		map.put("WRITE", 8);
		map.put("TO", 9);
		map.put("DO", 10);
		map.put(";", 11);
		map.put(":=", 12);
		map.put("+", 13);
		map.put("FOR", 14);
		map.put("(", 15);
		map.put(")", 16);
		map.put("id", 17);
		map.put("*", 18);
		return map;
	}

	public void trace() {
		// use parse tree
	}

	public boolean ifKeyWord(String token) {
		if (map.containsKey(token)) {
			trace();
			// do something
			return true;
		}
		// do something
		return false;
	}
	
	public void tokenize() {
		int i = 0;
		st = new StringTokenizer(line, ",()", true);
		while (st.hasMoreTokens()) {
			tokens.add(st.nextToken());
			System.out.println(tokens.get(i++));
		}
	}

	public void clearTokens() {
		tokens.clear();
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getLine() {
		return line;
	}
}