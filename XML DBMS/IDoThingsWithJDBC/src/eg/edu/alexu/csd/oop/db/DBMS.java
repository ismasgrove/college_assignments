package eg.edu.alexu.csd.oop.db;

import eg.edu.alexu.csd.oop.db.QueryHandler;
import eg.edu.alexu.csd.oop.db.SQLParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor.STRING;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class DBMS implements Database {
     // query validation is just checking on the name of the values
	private static DBMS dbms;
	private DBMS(){}
	public static DBMS getInstance(){
		if (dbms==null){dbms = new DBMS();}
		return dbms;
	}
	
	@Override	
	public boolean executeStructureQuery(String query) throws SQLException {/// create or drop
		SQLParser.getInstance().constructQueries(query);
		List<List<String>>data=SQLParser.getInstance().processQueries();
	    if(data==null)
		   return false;	  
		if(data.get(0).get(0).equalsIgnoreCase("CREATE")){//create
			   String tableName=data.get(0).get(1);
			   if(QueryHandler.getInstance().Create(tableName)==false)
				   return false;
			   List<String> element=new ArrayList<String>();
			   List<String> dataType=new ArrayList<String>();
			   for(int i=0;i<data.get(1).size();i++){
				   if(i%2==1){
					   dataType.add(data.get(1).get(i));
					   if(data.get(1).get(i).equals("varchar")==false&&data.get(1).get(i).equals("int")==false){
						   System.out.println("INVALID DATATYPE\n");
						   return false;
					   }
				   }
				   else
					   element.add(data.get(1).get(i));
				//   System.out.println(data.get(1).get(i));
			   }
			   QueryHandler.getInstance().CreateXSD(tableName, element, dataType);
			   return true;
		}
		else{//Drop
			   String fileName=QueryHandler.getInstance().getFileName(data.get(0).get(1));
			   String xsd=data.get(0).get(1)+".xsd";
			   File file=new File(fileName);
			   File file2=new File(xsd);
				  if(file.exists()){
		              file.delete();
				  }
				  if(file2.exists()){
		              file2.delete();
					  return true;
				  }
			   return false;
		}
	}
	@Override
	public Object[][] executeRetrievalQuery(String query) throws SQLException {/// select
		   SQLParser.getInstance().constructQueries(query);
		   List<List<String>>data=SQLParser.getInstance().processQueries();
		   if(data==null)
			   return null;	  
			List<String>elements=new ArrayList<String>();
			String tableName=data.get(0).get(1);
		    String condition=data.get(0).get(2);
		    String []s=condition.split("[\\s<>=';]+");
		    elements.add(s[0]);
		    elements.add(s[1]);
		    List<String>list=new ArrayList<String>();
		    list.add(s[0]);
		    if(!QueryHandler.getInstance().checkQueryValidity(tableName, list,0)){
		    	System.out.println("query not valid");
		    	return null;
		    }
			 if(query.contains("'"))
		        return	QueryHandler.getInstance().select(elements, 0,tableName ,data.get(1));
		        else if(query.contains("<"))
		        return	QueryHandler.getInstance().select(elements, 1,tableName, data.get(1));
		        else if(query.contains(">"))
		        return	QueryHandler.getInstance().select(elements, 2,tableName, data.get(1));
		        else if(query.contains("="))
		        return 	QueryHandler.getInstance().select(elements, 3,tableName, data.get(1));
		
		return null;
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		SQLParser.getInstance().constructQueries(query);
		List<List<String>>data=SQLParser.getInstance().processQueries();
	    if(data==null)
		   return 0;	  
		if(data.get(0).get(0).equalsIgnoreCase("DELETE")){//create
			/// validation is to check that the first element in the list is in the dtd file
			String tableName=data.get(0).get(1);
		    List<String>elements = new ArrayList<String>();
		    String condition=data.get(0).get(2);
		    String []s=condition.split("[;<>=']+");
		    elements.add(s[0]);
		    elements.add(s[1]);
		    List<String>list=new ArrayList<String>();
		    list.add(s[0]);
		    if(!QueryHandler.getInstance().checkQueryValidity(tableName, list,0)){
		    	System.out.println("query not valid");
		    	return 0;
		    }
		        if(query.contains("'"))
		        	return	QueryHandler.getInstance().delete(elements, 0, tableName);
		        else if(query.contains("<"))
		        	return	QueryHandler.getInstance().delete(elements, 1, tableName);
		        else if(query.contains(">"))
		        	return	QueryHandler.getInstance().delete(elements, 2, tableName);
		        else if(query.contains("="))
		        	return QueryHandler.getInstance().delete(elements, 3, tableName);
		}
		else {//insert
			/// assumes data is cool wl validation f queryhandler
			String tableName=data.get(0).get(1);
			    if(!QueryHandler.getInstance().checkQueryValidity(tableName, data.get(1),1)){
			    	System.out.println("query not valid");
			    	return 0;
			    }
			    
	        QueryHandler.getInstance().insert(data.get(1), tableName);
	    		if(QueryHandler.getInstance().ValidateXml(tableName)==1)
					return 1;
				else{
					try {
						QueryHandler.getInstance().useMyValidatePlease(tableName);
					} catch (IOException | XMLStreamException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }
		return 0;
	    }
		return 0;
	}
}
