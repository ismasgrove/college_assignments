package eg.edu.alexu.csd.oop.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class QueryHandler {
	/// insert mechanism
	/// read the file data and save it
	/// add to these data the data we want to insert
	/// add all these data to the file
  /*public static void main (String[] args){
	  List<String>a=new ArrayList<String>();
	  List<String>b=new ArrayList<String>();
      DropTable("Trial");
	  b.add("toto");/// random data to test insert method
	  b.add("kaboto");
	  insert(b, "Trial");
	  a.add("toto");/// random data to test insert method
	  a.add("kaboto");
	  select(a,0,"Trial"); 
  }*/
	private static QueryHandler qh;
	private QueryHandler(){}
	public static QueryHandler getInstance(){
		if (qh==null) {qh = new QueryHandler();}
		return qh;
	}
  public  void CreateXSD(String tableName,List<String>element,List<String>dataType){
      try{
          //creating xsd file 
      PrintWriter writer = new PrintWriter(tableName +".xsd", "UTF-8"); // bn3ml object tp write into the file
      // da w ana b7wl alsm el code 3shn mafish auto generation
       String x ="<xs:schema attributeFormDefault=\"unqualified\" elementFormDefault=\"qualified\" \n xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">";
       //bytb3 w b3den yaznl line gedid
       writer.println(x);
       
       //tany satr bktb table name 
       x="<xs:element name=\"" + tableName +"\">";
       writer.println(x);
       //baktb ma benhom \n 3shn ynzl satr gedid
       x ="<xs:complexType> \n <xs:sequence> \n <xs:element name=\"Element\" maxOccurs=\"unbounded\"> \n <xs:complexType> \n <xs:sequence> ";
       writer.println(x);
//      //to write columns
       for(int i=0;i<element.size();i++){
    	   if(dataType.get(i).equals("varchar"))
    		   dataType.set(i, "string");
           x = "<xs:element type=\"xs:" + dataType.get(i)+"\" name=\""+element.get(i)+"\"/>";
           writer.println(x);
       }
       x="</xs:sequence> \n </xs:complexType> \n </xs:element> \n </xs:sequence> \n </xs:complexType> \n </xs:element> \n </xs:schema> ";
       writer.println(x);
       writer.close(); //to close the file
 }
     catch (IOException e) {
      //  throws new Exception("File not found");
    }
  }
  public  boolean Create(String tableName){
	  File file=new File(getFileName(tableName));
	  if(file.exists())
		  return false;
	  /// create new xml file
	  String fileName=getFileName(tableName);
	  XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
      try{
         FileWriter fr=new FileWriter(fileName);
    	 XMLStreamWriter writer = xMLOutputFactory.createXMLStreamWriter(fr);
         writer.writeStartDocument();
         writer.writeStartElement(tableName);
    	 writer.writeEndElement();
         writer.writeEndDocument();
         writer.flush();
         writer.close();
         fr.close();
      }catch (XMLStreamException e){
    	  e.printStackTrace();
      }
      catch (IOException e){
    	  e.printStackTrace();
      }
      ///
	
	  return true;
  }
  public  String getFileName(String tableName){
	  String tmp=System.getProperty("user.dir");/// gets the location of the project
	  tmp+="/"+tableName;
	  String fileName = "";
	  for(int i=0;i<tmp.length();i++){
		  if(tmp.charAt(i)=='\\'){
			  fileName+="/";
		  }
		  else{
			  fileName+=tmp.charAt(i);
		  }
	  }
	  return fileName+".xml";
  }
  public  List<List<String> > getData(String fileName){///  this function returns a list of lists where each list is the data of each element individually
	  /// for illustration ->assume it has 3 lists
	  ///list 1 has ( "firstName" , "Ahmed , "LastName" ,"Morsy", "age" , "18")
	  ///list 2 has ( "firstName" , "Merna , "LastName" ,"teta", "age" , "1088")
	  ///list 3 has ( "firstName" , "Ahmed , "LastName" ,"isma3elo", "age" , "-18")
	  List<List<String> > data=new ArrayList<List<String> >();
	  List<String>cur=null;
	  XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();// creaating a new object of this class which provides methods to read from xml file
	   try{// just to catch errors msh aktr
		   
		   XMLEventReader xmlEventReader =xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
		   //the way stax work is by handling events this is an event reader
		   //by using it u can look at the next element and return its data
		   int anElementHasStarted=0;
		   while(xmlEventReader.hasNext()){// while i can read events
			   XMLEvent xmlEvent = xmlEventReader.nextEvent();// get the next event
			   if(xmlEvent.isStartElement()){// start element means <anyString>
				   StartElement startElement = xmlEvent.asStartElement();/// reads String content only
				   if(startElement.getName().getLocalPart().equals("Element")){
					   cur=new ArrayList<String>();
					   anElementHasStarted=1;
				   }
				   else if(anElementHasStarted==1){ 
					   cur.add(startElement.getName().getLocalPart());
					   xmlEvent = xmlEventReader.nextEvent();// get the next event which is the text after the start
					  if(xmlEvent.isCharacters()) 
					   cur.add(xmlEvent.asCharacters().getData());// converts the event into String and assigns it to a variable
					  else {
					   cur.add("");	  
					   if(xmlEvent.isEndElement()){// if this event is an end event </anyString>
						   EndElement  endElement = xmlEvent.asEndElement();/// end element is an object which has 3 components
						   ///one of them is localName which is the text part only between the 2 <>
						   if(endElement.getName().getLocalPart().equals("Element")){/// gets the string part of the end and compares
							   data.add(cur);
							   cur=null;
						   }
					   }
					  }
				   }
		      }
			  if(xmlEvent.isEndElement()){// if this event is an end event </anyString>
				   EndElement  endElement = xmlEvent.asEndElement();/// end element is an object which has 3 components
				   ///one of them is localName which is the text part only between the 2 <>
				   if(endElement.getName().getLocalPart().equals("Element")){/// gets the string part of the end and compares
					   data.add(cur);
					   cur=null;
				   }
			   }
		 }

		   xmlEventReader.close();
	   }catch(XMLStreamException | FileNotFoundException e){/// if file name is wrong or xml format is not well
		   System.out.println("No Such File\n");
		   e.printStackTrace();
	   }
	 return data;
  }
  public  int insert(List<String>element , String tableName){
	  	  /// insert these data to these elements in this table
		  String fileName=getFileName(tableName);/// this functions gets the path of the file which already exists
		  /// now we have the correct fileName we want to open it and write
		  ///assuming data is correct we will call parser class to get the data from the xml file
		  /// then we create a new file write the same data and add to it the data that we want to insert
		  /// the new file has the same name of the old file so it will overwrite it		  
	      /// data and the element lists are the element that i want to add components
		  /// ex if it was an employee then element will hold the column names likes firstName lastName age ...
		  /// and the next element in the list will be the value of the preceding object
		
		  List<List<String>>AllElements=getData(fileName);
		  File file=new File(fileName);
		  if(file.exists()==false){
			  System.out.println("NO Such File\n");
			  return 0;
		  }
		  try {
				List<String>all=getXsdElements(tableName);
				for(int i=0;i<all.size();i+=2){
					if(element.contains(all.get(i)))
						continue;
					element.add(i, all.get(i));
					if(all.get(i+1).equals("int"))
						element.add(i+1,"0");
					else
						element.add(i+1,"");
				}
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (XMLStreamException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		  file.delete();
		  AllElements.add(element);
		  XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
	      try{
	         FileWriter fr=new FileWriter(fileName);
	    	 XMLStreamWriter writer = xMLOutputFactory.createXMLStreamWriter(fr);
	         writer.writeStartDocument();
	         writer.writeStartElement(tableName);
	         for(int i=0;i<AllElements.size();i++){
	        	 List<String>cur=AllElements.get(i);
	        	 writer.writeStartElement("Element");
	        	 for(int j=0;j<cur.size();j+=2){
		        	 writer.writeStartElement(cur.get(j));
		        	 writer.writeCharacters(cur.get(j+1));
		        	 writer.writeEndElement();
		         }
	        	 writer.writeEndElement();
	         }
        	 writer.writeEndElement();
	         writer.writeEndDocument();
	         writer.flush();
	         writer.close();
	         fr.close();
	      }catch (XMLStreamException e){
	    	  e.printStackTrace();
	      }
	      catch (IOException e){
	    	  e.printStackTrace();
	      }
          return 1;
  }
  

  public  List<Integer>getDataForDeleteAndInsertStringVersion(String fileName,List<String>wanted){
	  ///returns idx of elements in the XML file which i want to delete or select in case the condition is a String
	  List <Integer> Data=new ArrayList<Integer>();
	  XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();// creating a new object of this class which provides methods to read from xml file
	   try{// just to catch errors msh aktr
		   XMLEventReader xmlEventReader =xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
		   //the way stax work is by handling events this is an event reader
		   //by using it u can look at the next element and return its data
		   int anElementHasStarted=0,idx=0;
		   while(xmlEventReader.hasNext()){// while i can read events
			   XMLEvent xmlEvent = xmlEventReader.nextEvent();// get the next event
			   if(xmlEvent.isStartElement()){// start element means <anyString>
				   StartElement startElement = xmlEvent.asStartElement();/// reads String content only
				   if(startElement.getName().getLocalPart().equals("Element")){
					   anElementHasStarted=1;
				   }
				   else if(anElementHasStarted==1){ 
					   if(startElement.getName().getLocalPart().equals(wanted.get(0))){
						   xmlEvent = xmlEventReader.nextEvent();// get the next event which is the text after the start
						   if(xmlEvent.isCharacters()){
						     if(xmlEvent.asCharacters().getData().equals(wanted.get(1))){
						     	   Data.add(idx);
						     }
						   }

						  if(xmlEvent.isEndElement()){// if this event is an end event </anyString>
							   EndElement  endElement = xmlEvent.asEndElement();/// end element is an object which has 3 components
							   ///one of them is localName which is the text part only between the 2 <>
							   if(endElement.getName().getLocalPart().equals("Element")){/// gets the string part of the end and compares
								   idx++;
							   }
						   }						   
					   }
					   else
					    xmlEvent = xmlEventReader.nextEvent();// get the next event which is the text after the start
					 }
		      }
			  if(xmlEvent.isEndElement()){// if this event is an end event </anyString>
				   EndElement  endElement = xmlEvent.asEndElement();/// end element is an object which has 3 components
				   ///one of them is localName which is the text part only between the 2 <>
				   if(endElement.getName().getLocalPart().equals("Element")){/// gets the string part of the end and compares
					   idx++;
				   }
			   }
		 }
		   xmlEventReader.close();
	   }catch(XMLStreamException | FileNotFoundException e){/// if file name is wrong or xml format is not well
		   e.printStackTrace();
	   }
	 return Data;
  }
  public  List<Integer>getDataForDeleteAndInsertIntegerVersion(String fileName,List<String>wanted,int relation){
	  ///returns idx of elements in the XML file which i want to delete or select in case the condition is an INT
	  /// relation equals 1-> if i want all elements with values smaller than me
	  /// 2 if smaller
	  List <Integer> Data=new ArrayList<Integer>();
	  XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();// creaating a new object of this class which provides methods to read from xml file
	   try{// just to catch errors msh aktr
		   XMLEventReader xmlEventReader =xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
		   //the way stax work is by handling events this is an event reader
		   //by using it u can look at the next element and return its data
		   int anElementHasStarted=0,idx=0;
		   while(xmlEventReader.hasNext()){// while i can read events
			   XMLEvent xmlEvent = xmlEventReader.nextEvent();// get the next event
			   if(xmlEvent.isStartElement()){// start element means <anyString>
				   StartElement startElement = xmlEvent.asStartElement();/// reads String content only
				   if(startElement.getName().getLocalPart().equals("Element")){
					   anElementHasStarted=1;
				   }
				   else if(anElementHasStarted==1){ 
					   if(startElement.getName().getLocalPart().equals(wanted.get(0))){
						   xmlEvent = xmlEventReader.nextEvent();// get the next event which is the text after the start
						   Integer cur=Integer.parseInt(xmlEvent.asCharacters().getData());
						   Integer key=Integer.parseInt(wanted.get(1));
						   if(relation==1&&cur.intValue()<key.intValue())
							   Data.add(idx);
						   
						   else if(relation==2&&cur.intValue()>key.intValue())
							   Data.add(idx);
						   else if(relation==3&&cur.intValue()==key.intValue())
							   Data.add(idx);
					   }
					   else
					    xmlEvent = xmlEventReader.nextEvent();// get the next event which is the text after the start
					 }
                                  // System.out.println(Data.get(idx));
		      }
			  if(xmlEvent.isEndElement()){// if this event is an end event </anyString>
				   EndElement  endElement = xmlEvent.asEndElement();/// end element is an object which has 3 components
				   ///one of them is localName which is the text part only between the 2 <>
				   if(endElement.getName().getLocalPart().equals("Element")){/// gets the string part of the end and compares
					   idx++;
				   }
			   }
		 }
		   xmlEventReader.close();
	   }catch(XMLStreamException | FileNotFoundException e){/// if file name is wrong or xml format is not well
		   e.printStackTrace();
	   }
	 return Data;
  }
    
  ///Deleto

  public  int delete(List<String>element,int isInt, String tableName){
		/// this function takes a list which has 2 elements column name and its value
	  /// if the column was a String then isInt equals 0
	  /// else then value of isInt is 1 -> if i want to delete elements smaller than the sent value
	  ///2 -> if i want to delete all values larger than this value
	  ///3-> if i want to delete all elements equal to this value
	  int ret=0;
	  String fileName=getFileName(tableName);/// this functions gets the path of the file which already exists
   	      List<List<String>>AllElements=getData(fileName);
   	      List<Integer>Index;
		  File file=new File(fileName);
		  if(file.exists()==false){
			  System.out.println("NO Such File\n");
			  return 0;
		  }
		  if(isInt==0)
			  Index=getDataForDeleteAndInsertStringVersion(fileName, element);
		  else
			  Index=getDataForDeleteAndInsertIntegerVersion(fileName,element,isInt);
		  file.delete();
		  ret=Index.size();
		  XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
		  try{
	    	  FileWriter fr=new FileWriter(fileName);
		     XMLStreamWriter writer = xMLOutputFactory.createXMLStreamWriter(fr);  
	         writer.writeStartDocument();
	         writer.writeStartElement(tableName);
	         for(int i=0;i<AllElements.size();i++){
	        	 if(Index.contains(i))
	        		 continue;
	        	 List<String>cur=AllElements.get(i);
	        	 writer.writeStartElement("Element");
	        	 for(int j=0;j<cur.size();j+=2){
		        	 writer.writeStartElement(cur.get(j));
		        	 writer.writeCharacters(cur.get(j+1));
		        	 writer.writeEndElement();
		         }
	        	 writer.writeEndElement();
	         }
        	 writer.writeEndElement();
	         writer.writeEndDocument();
	         writer.flush();
	         writer.close();
	         fr.close();
	      }catch (XMLStreamException e){
	    	  e.printStackTrace();
	      }
	      catch (IOException e){
	    	  e.printStackTrace();
	      }
	      return ret;
          
  }
  public  void GoPrint(List<String>l){
	  for(int i=0;i<l.size();i+=2){
		  System.out.print(l.get(i)+" : "+l.get(1)+"  ");
	  }
	  System.out.println();
  }
  
  public  Object [][] select(List<String>element,int isInt, String tableName,List<String>Only){
		/// this function takes a list which has 2 elements column name and its value
	  /// if the column was a String then isInt equals 0
	  /// else then value of isInt is 1 -> if i want to delete elements smaller than the sent value
	  ///2 -> if i want to delete all values larger than this value
	  Object [][] ret=new Object[0][0];
	  String fileName=getFileName(tableName);/// this functions gets the path of the file which already exists
   	      List<List<String>>AllElements=getData(fileName);
   	      List<Integer>Index;
   	      List<String> all=new ArrayList<String>();
   	      File file=new File(fileName);
		  if(file.exists()==false){
			  System.out.println("NO Such File\n");
			  return ret;
		  }
		  try {
			all=this.getXsdElements(tableName);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (XMLStreamException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  if(isInt==0)
			  Index=getDataForDeleteAndInsertStringVersion(fileName, element);
		  else
			  Index=getDataForDeleteAndInsertIntegerVersion(fileName,element,isInt);
		  file.delete();
		  XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
		  int idxInXml=0,pos=0,idx=0,needed=Only.size();
	      try{
	    	  FileWriter fr=new FileWriter(fileName);
		     XMLStreamWriter writer = xMLOutputFactory.createXMLStreamWriter(fr);
	         writer.writeStartElement(tableName);
	         for(int i=0;i<AllElements.size();i++){
	        	 List<String>cur=AllElements.get(i);
	        	 if(pos<Index.size()){
	        		 if(idxInXml==Index.get(pos)){
	        			 pos++;
	        			 Object[][] tmp=new Object[ret.length+1][needed];
	        			 for(int j=0;j<ret.length;j++)
	        				 tmp[j]=ret[j];
	        			 ret=tmp;
	        			 int idx2=0;
	        			 for(int j=0;j<cur.size();j+=2){
                            if(Only.contains(cur.get(j))){
                            	ret[idx][idx2]=cur.get(j+1);
                            	if(all.get(j+1).equals("int"))
                            		ret[idx][idx2]=(Integer)(Integer.parseInt(ret[idx][idx2].toString()));
                            	else
                            		ret[idx][idx2]=(String)(ret[idx][idx2]);
                               idx2++;
                            }
	        			 }
	        			 idx++;
	        	//		 GoPrint(cur);
	        		 }
	        	 }
	        	 idxInXml++;
	        	 writer.writeStartElement("Element");
	        	 for(int j=0;j<cur.size();j+=2){
		        	 writer.writeStartElement(cur.get(j));
		        	 writer.writeCharacters(cur.get(j+1));
		        	 writer.writeEndElement();
		         }
	        	 writer.writeEndElement();
	         }
        	 writer.writeEndElement();
	         writer.writeEndDocument();
	         writer.flush();
	         writer.close();
	         fr.close();
	      }catch (XMLStreamException e){
	    	  e.printStackTrace();
	      }
	      catch (IOException e){
	    	  e.printStackTrace();
	      }
	      return ret;
          
  }
	public   int ValidateXml(String name){
	    try{
	            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream(name+".xml"));
	        //schemaFactory da abstract class to create schema object to help us in validation 
	        // w el gowa bracket bt3t newInstance de schema language 
	        //

	        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
	        //Parses the specified File as a schema and returns it as a Schema.
	        //Parameter file that represent a schema
	        Schema schema = factory.newSchema(new File(name+".xsd"));
	       //validator is abstract class that checks an XML document against Schema.
	       //Schema -> This object represents a set of constraints that can be checked/ enforced against an XML document.
	       //newValidator create new validator to this schema to enforce/check the set of constraints this object represents.
	        javax.xml.validation.Validator validator = schema.newValidator();
	        //Method validate bt5od xml file w tashofo lw kan validate wala l2
	        validator.validate(new StAXSource(reader));


	        //no exception thrown, so valid
	        System.out.println("Document is valid");
	        return 1;
	    }
	    catch (Exception ex) {
	     // ex.printStackTrace();
	     //print tha exception
	     System.out.print(ex);
	     return 0;
	    }
//	     
	  }
	//for testing it in main
	//   ValidateXml("bondo2a");

	public  List<String> getXsdElements(String fileName) throws FileNotFoundException, XMLStreamException{

		List<String> columnsName = new ArrayList<String>();;
		String x,y ;
			   try{

		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		InputStream input = new FileInputStream(new File(fileName+".xsd"));
		XMLStreamReader xmlStreamReader = inputFactory.createXMLStreamReader(input);

		while (xmlStreamReader.hasNext()) {
		    int event = xmlStreamReader.next();

		    if (event == XMLStreamConstants.START_ELEMENT) {
		    x=xmlStreamReader.getAttributeValue(1);
		    y=xmlStreamReader.getAttributeValue(0);
		  //da kamyt if condition 3shn bamn3 hodos ay error 3'er matwk3 y3knan 3alena :D 3shn attribute zero feh table name w element w dol msh 3yznohom
		    if(x ==null || x.equalsIgnoreCase("qualified") || y.equalsIgnoreCase(fileName) || y.equalsIgnoreCase("unqualified") || y.equalsIgnoreCase("Element")){
		       
		    }
		    else{
		        //akid ana m3mltsh kda mn far3' bs 3shn life is not that easy f howa bytb3li datatype b xs: el abliha f lazm ashel xs:
		        y =y.substring(3);
		        //odd iterator for columnName and even one for datatype
		         columnsName.add(x);
		         columnsName.add(y);
		    }
		    }

		    
		                }

		            }
		   
		          catch(XMLStreamException | FileNotFoundException e){/// if file name is wrong or xml format is not well
				   e.printStackTrace();
			   }
		           return columnsName;
		   }
	public  boolean checkQueryValidity(String tableName,List<String>elements,int k){
		try {
			k++;
			List<String>xsd=getXsdElements(tableName);
			for(int i=0;i<elements.size();i+=k){
				if(!xsd.contains(elements.get(i)))
					return false;
			}
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	   public  void useMyValidatePlease(String fileName) throws IOException, XMLStreamException{
	       //u can replace awl try w catch b enk tandi function getdata bs w to7tha f list of list
	       //3mtan ana m3mltsh 7aga awee 3'er eni 3'asht 7agat mn your code morsy :D :D 
	      List<List<String> > data=getData(getFileName(fileName));
		  List<String>cur=null;System.out.println(data.size());
	           FileWriter fr=new FileWriter(fileName+".xml");
	             XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
			     XMLStreamWriter writer = xMLOutputFactory.createXMLStreamWriter(fr);  
		         writer.writeStartDocument();
		         writer.writeStartElement(fileName);
		         for(int i=0;i<data.size()-1;i++){
		        	 cur=data.get(i);
		        	 writer.writeStartElement("Element");
		        	 for(int j=0;j<cur.size();j+=2){
			        	 writer.writeStartElement(cur.get(j));
			        	 writer.writeCharacters(cur.get(j+1));
			        	 writer.writeEndElement();
			         }
		        	 writer.writeEndElement();
		         }
	        	 writer.writeEndElement();
		         writer.writeEndDocument();
		         writer.flush();
		         writer.close();
		         fr.close();
	   }
	// method de takbrk tast5dm my validation !!!!!!!!!!!!
}
