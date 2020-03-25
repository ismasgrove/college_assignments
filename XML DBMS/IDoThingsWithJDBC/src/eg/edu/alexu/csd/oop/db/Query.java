package eg.edu.alexu.csd.oop.db;



import eg.edu.alexu.csd.oop.db.QueryMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Query {
	private String query;
	private String tableName;
	private String condition;
	private List<String> columns;
	private List<String> element;
	private List<List<String>> elements;
	private StringBuilder sb;
	private StringTokenizer st;
	
	public void setQuery(String query){
		this.query=query;
	}
	public void display(){
		System.out.println(query);
	}
	
	//CONSTRUCTS STRINGS INTO A CERTAIN FORMAT FOR EASIER PROCESSING
	//CERTAIN WAY -> ONE LINE FOR EACH QUERY
	private String format(){
		if (st==null) {st=new StringTokenizer(query);} //STRINGTOKENIZER: CREATES TOKENS OUT OF STRING, SEPARATES WORDS OR INDIVIDUAL CONSTRUCTS
		if (sb==null) {sb=new StringBuilder();}        //STRINGBUILDER: A TOOL FOR EFFICIENT STRING MANIPULATION
		while (st.hasMoreTokens()){
			sb.append(st.nextToken());
			sb.append(" ");
		}
		return sb.toString();
	}
	
	//THIS FUNCTION IS USED TO PARSE INPUT BETWEEN PARENTHESIS
	//THIS IS FOR USER INPUT WITH VARIABLE LENGTH
	//LIKE "TABLENAME(COLUMN-1, COLUMN-2, ... COLUMN-N)"
	//NEEDS ORDER
	//AS OF NOW, FOR CREATE, TABLENAME INDEX 0, DATATYPE INDEX 1, SO ON
	//WHICH NEEDS TO CHANGE
	private List<String> extractValues(String stripped){
		List<String>list = new ArrayList<>();
		st = new StringTokenizer(stripped);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		for(int i=0;i+1<list.size();i++){
			if(list.get(i).endsWith(",")==false){
				return null;
			}
			else{
				list.set(i,list.get(i).substring(0, list.get(i).length()-1));
			}
                        if(list.size()==0)return null;
		}
                if(list.size()==0)return null;
		if(list.get(list.size()-1).endsWith(",")){
			return null;
		}
		return list;
	}
	private List<String> extractValuesCreate(String stripped){
	List<String>list = new ArrayList<>();
	st = new StringTokenizer(stripped);
	while (st.hasMoreTokens()) {
		list.add(st.nextToken());
	}
	for(int i=1;i+2<list.size();i+=2){
		if(list.get(i).endsWith(",")==false){
			return null;
		}
		else{
			list.set(i,list.get(i).substring(0, list.get(i).length()-1));
		}
	}
	if(list.get(list.size()-1).endsWith(",")){
		return null;
	}
	return list;
}

	//THIS SHOULD NOT BE VOID
	//IT SHOULD RETURN STORED 'THINGS TO DO' FOR EACH QUERY
	//HAVEN'T EFFICIENTLY LAID OUT A DATA DISTRUBITION SYSTEM YET
	public List<List<String> > processQuery(){
		query=format();
		String determine = QueryMatcher.getInstance().determine(query);
		if ("create".equalsIgnoreCase(determine)) {								//CREATE TABLE
			tableName = QueryMatcher.getInstance().extract(query, "(?U)(?i)(?<=\\bcreate table\\s)(\\w+)");
																		//EACH EXTRACT IS ACCOMPANIED BY BEFITTING REGEX PATTERN
			
			columns = extractValuesCreate(query.substring(query.indexOf('('), query.indexOf(')')).replaceAll("[^a-zA-Z0-9_,\\s]", ""));
																		//THIS EFFECTIVELY EXTRACTS PARENTHESIS STRING
																		//AND STRIPS IT FROM IT'S PARENTHESIS AND COMMAS
																		//LEAVING ONLY WORDS FOR STRINGTOKENIZER TO DISASSEMBLE

			if(columns==null){
				System.out.println("InValiD Format !);");
				return null;
			}
			element = new ArrayList<String>();
			elements = new ArrayList<List<String>>();
			element.add("CREATE");
			element.add(tableName);
			elements.add(element);
			element=new ArrayList<String>();
			for (int i=0; i<columns.size(); i++) {
	            element.add(columns.get(i));
			}
		    elements.add(element);
			return elements;	//AN ARRAYLIST OF ELEMENTS, EACH ELEMENT CONTAINS 3 ITEMS: (TABLENAME, COLUMNNAME, DATATYPE)
			
		} else if ("select".equalsIgnoreCase(determine)) {						//UNFINISHED AS FAR AS EXTRACTING VALUES GOES
																		//MOST DIFFICULT ONE TO MAKE
																		//BECAUSE NO PARENTHESIS/DELIMITER FOR GIVEN VALUES
																		//AND NO CLEAR PATTERN FOR A REGEX
																		//WILL FIND A WAY ASAP
			columns = extractValues(QueryMatcher.getInstance().extract(query, "(?i)(?U)(?<=select)(.*)(?=from)").replaceAll("[^a-zA-Z0-9_,\\s]", ""));
			if(columns==null){
				System.out.println("InValiD Format !);");
				return null;
			}
			tableName = QueryMatcher.getInstance().extract(query, "(?U)(?i)(?<=\\bfrom\\s)(\\w+\\b)");
			condition = QueryMatcher.getInstance().extract(query, "(?i)(?U)(?<=where\\s)(.*)(?=;)");
			element = new ArrayList<String>();
			elements = new ArrayList<List<String>>();
			element.add("SELECT");
			element.add(tableName);
			element.add(condition);
            elements.add(element);
            elements.add(columns);
			 
			return elements;	//AN ARRAYLIST OF ELEMENTS, EACH ELEMENT CONTAINS THREE ITEMS: (TABLENAME, COLUMNNAME, CONDITION)
			
		} else if ("delete".equalsIgnoreCase(determine)) {						//DELETE
			condition = QueryMatcher.getInstance().extract(query, "(?i)(?U)(?<=where\\s)(.*)(?=;)");
			tableName = QueryMatcher.getInstance().extract(query, "(?U)(?i)(?<=\\bdelete from\\s)(\\w+)\\b");
			element = new ArrayList<String>();
			elements = new ArrayList<List<String>>();
			element.add("DELETE");
			element.add(tableName);
			element.add(condition);
			elements.add(element);
			return elements;		//AN ARRAYLIST OF TWO ELEMENTS: (TABLENAME, CONDITION)
			
		} else if ("insert".equalsIgnoreCase(determine)) {						//INSERT
			tableName = QueryMatcher.getInstance().extract(query, "(?U)(?i)(?<=\\binsert into\\s)(\\w+)\\b");
			columns = extractValues(query.substring(query.indexOf('('), query.indexOf(')')).replaceAll("[^a-zA-Z0-9_,\\s]", ""));
			List<String> values = extractValues(query.substring(query.lastIndexOf('('), query.lastIndexOf(')')).replaceAll("[^a-zA-Z0-9_,\\s]", ""));
																		//ONE FOR INSERT FROM
																		//ONE FOR VALUES
			if(columns==null||values==null||values.size()!=columns.size()){
				System.out.println("InValiD Format !);");
				return null;
			}
			element = new ArrayList<String>();
			elements = new ArrayList<List<String>>();
			element.add("INSERT");
			element.add(tableName);
			elements.add(element);
			element = new ArrayList<String>();
	
			for (int i=0; i<values.size(); i++) {
					element.add(columns.get(i));
					element.add(values.get(i));
			}
			 elements.add(element);
			return elements; //AN ARRAYLIST OF ELEMENTS, EACH ELEMENT CONTAINS THREE ITEMS: (TABLENAME, COLUMN, VALUE)
			
		} else if ("drop".equalsIgnoreCase(determine)) {
			tableName = QueryMatcher.getInstance().extract(query, "(?i)(?U)(?<=table\\s)(.*)(?=;)");
																		//DROP IS SO NICE
																		//I LOVE DROP
																		//IF ONLY OTHER COMMANDS WERE LIKE DROP
																		//WHY CAN'T WE BE ALL LIKE DROP
																		//THE WORLD WOULD BE BETTER IF ALL OF OUR NAMES WERE
																		//DROP TABLE
			element = new ArrayList<String>();
			element.add("DROP");
			element.add(tableName);
			elements = new ArrayList<List<String>>();
			elements.add(element);
			return elements; //ARRAYLIST OF STRINGS CONTAINING ONE ELEMENT: TABLENAME
		}
		
		return null;
	}
}