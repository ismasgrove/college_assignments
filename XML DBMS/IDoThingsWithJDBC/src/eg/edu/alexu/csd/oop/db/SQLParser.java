package eg.edu.alexu.csd.oop.db;



import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SQLParser {					//WHILE IT IS THE MAIN CLASS SINCE IT'S CALLED 'SQLPARSER'
											//IT'S PRETTY EMPTY, LOL
											//WHAT IS MISSING IS MOSTLY THINGS TO DO WITH
											//INTERACTION WITH THE XML READING AND WRITING CLASSES
											//I WOULD SAY THAT I LEFT IT OUT BECAUSE I WANT TO ENSURE SMOOTH INTEGRATION
											//BUT IF THAT WAS TRUE I'D DO IT MYSELF
											//I ACTUALLY WANT TO SLEEP
											//IMPORT ISMAIL IS ACTUALLY REALLY LAZY
											//TOO LAZY TO EVEN TURN OFF CAPS LOCK
	
		private static SQLParser servant;
		private ArrayList<Query>queries;
		private StringBuilder sb;
		public static SQLParser getInstance(){
			if (servant==null){
				servant = new SQLParser();
			}
			return servant;
		}
		//THIS FUNCTION SEPARATES DIFFERENT STATEMENT FOR INDEPENDANT PROCESSING
		//EDITORPANE TEXT IS SPLIT USING SEMICOLONS AS DELIMITERS
		public void constructQueries(String editorField){
			if (sb==null){sb=new StringBuilder();}
			queries=new ArrayList<>();
			for (String statement: editorField.split(";")) {
				sb.append(statement);
				sb.append(";");
				Query query = new Query();
				query.setQuery(sb.toString());
				queries.add(query);
				sb.setLength(0);
				}
		}
		//USELESS FUNCTION FOR TESTING PURPOSES
		public void displayQueries(){
			for (Query q:queries){
				q.display();
			}
		}
		//FUNCTION FROM QUERY.JAVA ON MULTIPLE ARRAYS
		//THE INFORMATION LEAVING THIS FUNCTION GOES INTO THE XML FILES
		//THIS IS WHERE WE SHOULD CONNECT XMLPARSING AND SQLPARSING CLASSES
		public List< List <String> > processQueries(){
			List<List<String>>ret=null;
			for (Query q:queries){
				ret=q.processQuery();				//THIS FUNCTION RETURNS A LIST OF GENERIC TYPES
												//IT CAN ONLY BE LIST<STRING> OR LIST<LIST<STRING>>
						         				//ORDER OF ITEMS IS WRITTEN INSIDE FUNCTION
			}
			return ret;
		}
}