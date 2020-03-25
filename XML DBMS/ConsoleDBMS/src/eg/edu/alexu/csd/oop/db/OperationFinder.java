package eg.edu.alexu.csd.oop.db;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperationFinder {
	private final Pattern createTable = Pattern.compile("(?i)create\\s+table\\s+\\w*\\s+\\(([^()]|)*\\);");
    private final Pattern dropTable = Pattern.compile("(?i)drop\\s+table\\s+(.*);");
    private final Pattern select = Pattern.compile("(?i)select\\s+(.*)\\s+from\\s+(.*)\\s+where\\s+(.*);");
    private final Pattern deleteFrom = Pattern.compile("(?i)delete\\s+from\\s+(.*)\\s+where\\s+(.*);");
    private final Pattern insertInto = Pattern.compile("(?i)insert\\s+into\\s+\\w*\\s+\\(([^()]|)*\\)\\s+values\\s+\\(([^()]|)*\\);");
    private Pattern placeHolder;
    private Matcher matcher;
    private ArrayList<String>extract;
    private static OperationFinder ss;
    private OperationFinder(){}
    public static OperationFinder getInstance(){
    	if (ss==null){ss = new OperationFinder();}
    	return ss;
    }
    
    public String determine(String query){
    	matcher = createTable.matcher(query);
    	if (matcher.lookingAt()) return "create";
    	matcher = dropTable.matcher(query);
    	if (matcher.lookingAt()) return "drop";
    	matcher = insertInto.matcher(query);
    	if (matcher.lookingAt()) return "insert";
    	matcher = deleteFrom.matcher(query);
    	if (matcher.lookingAt()) return "delete";
    	matcher = select.matcher(query);
    	if (matcher.lookingAt()) return "select";
    	return null;
    }
}
