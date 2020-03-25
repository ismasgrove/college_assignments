package eg.edu.alexu.csd.oop.db;



import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryMatcher {		//THIS CLASS IN GENERAL IS MEANT TO WORK LIKE A COMPILER
								//IT 'UNDERSTANDS' SQL COMMANDS
	
								//PATTERN IS A REGEX IMPORT
								//PATTERN.COMPILE("HERE GOES YOUR STRING'S CONDITIONS & MATCHING REQUIREMENTS")
								//REGEX101 WILL EXPLAIN REGEX MUCH BETTER THAN I CAN
								//THESE FINAL PATTERNS REPRESENT OUR SUPPORTED COMMANDS
	    /*private final Pattern createTable = Pattern.compile("(?i)^(create\\stable\\s[a-zA-Z]\\w*)\\s\\(",Pattern.CASE_INSENSITIVE);
	    private final Pattern dropTable = Pattern.compile("(?i)^(drop\\stable\\s[a-zA-Z]\\w*)",Pattern.CASE_INSENSITIVE);
	    private final Pattern select = Pattern.compile("(?i)(select\\s\\w*)",Pattern.CASE_INSENSITIVE);
	    private final Pattern deleteFrom = Pattern.compile("(?i)(delete\\sfrom\\s[a-zA-Z])\\w*\\s(where)\\s",Pattern.CASE_INSENSITIVE);
	    private final Pattern insertInto = Pattern.compile("(?i)(insert\\sinto\\s[a-zA-Z]\\w*)",Pattern.CASE_INSENSITIVE);*/
	    private final Pattern createTable = Pattern.compile("(?i)create\\s+table\\s+\\w*\\s+\\(([^()]|)*\\);");
	    private final Pattern dropTable = Pattern.compile("(?i)drop\\s+table\\s+(.*);");
	    private final Pattern select = Pattern.compile("(?i)select\\s+(.*)\\s+from\\s+(.*)\\s+where\\s+(.*);");
	    private final Pattern deleteFrom = Pattern.compile("(?i)delete\\s+from\\s+(.*)\\s+where\\s+(.*);");
	    private final Pattern insertInto = Pattern.compile("(?i)insert\\s+into\\s+\\w*\\s+\\(([^()]|)*\\)\\s+values\\s+\\(([^()]|)*\\);");
	    private Pattern placeHolder;
	    private Matcher matcher;
	    private ArrayList<String>extract;
	    private static QueryMatcher qm;
	    private QueryMatcher(){}
	    public static QueryMatcher getInstance(){
	    	if (qm==null){qm = new QueryMatcher();}
	    	return qm;
	    	
	    }
	    
	    //LOOKINGAT BEGINS MATCHING FROM THE BEGINNING OF THE STRING AND STOPS WHEN THE PATTERN IS FINISHED
	    //THERE IS ANOTHER FUNCTION CALLED 'MATCHES' BUT IT MATCHES THE ENTIRE STRING
	    //THIS FUNCTION IS USED TO DETERMINE THE OPERATION FOR EACH QUERY
	    //THESE STRINGS ARE LIKE KEYS AND WILL ONLY MATTER INSIDE THE PROGRAM
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
	    
	    //THIS FUNCTION IS USED TO EXTRACT DATA, WORKS ON DELETE, AND DROP
	    //DATA MEANS VARIABLE NAMES
	    //TABLE NAMES, COLUMN NAMES ASSOCIATED WITH EACH OPERATION
	    //EXTRACT RECEIVES QUERY TO EXTRACT FROM AND REGEX TO EXTRACT **FIRST** MATCHING ELEMENT
	    public String extract(String query, String regex){
	    //	System.out.println(query);
	    	placeHolder = Pattern.compile(regex);
	    	matcher = placeHolder.matcher(query);
	    	if (matcher.find())
	     		return matcher.group();
	    	return "oops";
	    }
}