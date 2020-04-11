/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 *
 * @author No_One
 */
public class Assembler {

    /**
     * @param args the command line arguments
     */
     public static String LCTCTR;
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here

     
   
    int pass =1;
     HashMap<String, String> symTable = new HashMap<>();
    
    
    FileInputStream fstream = new FileInputStream("file.txt");
    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
    File f = new File("myfile.txt");
    String strLine;

    strLine = br.readLine();
    InstrLine s= new InstrLine(strLine);
    String opcode= s.map.get("column2");
//      System.out.println(opcode);
  //  System.out.println(strLine);
    // get instruction name
    
 while (pass == 1){ 
    if (opcode.equals("START")){
    
//       System.out.println(opcode);
       LCTCTR = s.map.get("column3");
//        System.out.println(LCTCTR);
        strLine = br.readLine();
        s.changeinst(strLine);
         opcode= s.map.get("column2");
//                System.out.println(opcode);
s.writeToIntermediate(f);
  /// file 
    }else{
      LCTCTR =Integer.toHexString(0); 
      System.out.println(LCTCTR);
    strLine = br.readLine();
       s.changeinst(strLine);
         opcode= s.map.get("column2");
         s.writeToIntermediate(f);
  /// file 
    }
 
    while ( opcode.equals("END") == false)  {
    System.out.println(strLine);
        if(!s.map.get("column1").equals(".")){// not comment      
            // check symb
        
            // gets sym
            if(!s.map.get("column1").equals("#")&& symTable.containsKey(s.map.get("column1"))){     // found symb
              // set error falsg ????   
                System.out.println("repeated");

            }else if (!s.map.get("column1").equals("#") && !symTable.containsKey(s.map.get("column1") ) ){
                //store symb
                symTable.put(s.map.get("column1") ,LCTCTR );
//                System.out.println("label" +s.map.get("column1"));
            }
        // search optable
     
        if(InstructionSet.ifFound(opcode)){
        LCTCTR = Integer.toHexString((Integer.parseInt(LCTCTR,16)+3));
        System.out.println(LCTCTR);
        }else if(opcode.equals("WORD")) {
        LCTCTR = Integer.toHexString((Integer.parseInt(LCTCTR,16)+3));  
        System.out.println(LCTCTR);
        }else if(opcode.equals("RESW")) {
          System.out.println("extra"+LCTCTR);
         LCTCTR = Integer.toHexString((Integer.parseInt(LCTCTR,16)+(3*Integer.parseInt(s.map.get("column3"),16))));  
           System.out.println(LCTCTR);
        }else if(opcode.equals("RESB")) {
//          System.out.println("extra "+LCTCTR);
         LCTCTR = Integer.toHexString((Integer.parseInt(LCTCTR,16)+Integer.parseInt(s.map.get("column3"),16)));  
           System.out.println(LCTCTR);
        }else if(opcode.equals("BYTE")) {
            int i =0,j=0;
          
       
            if (s.getMap().get("column3").startsWith("X")) {
            String original =s.getMap().get("column3");
            String  newString = original.replaceAll("'","");
                    newString = newString.replace("X","");
               i =Integer.parseInt(newString,16);
              i = Integer.toHexString(i).length();

              
           LCTCTR = Integer.toHexString((Integer.parseInt(LCTCTR,16)+i));  
  
         
            }else
            if (s.getMap().get("column3").startsWith("C")) {
            	String original =s.getMap().get("column3");
            String newString = original.replaceAll("'","");
                    newString = newString.replace("C","");
                       
           LCTCTR = Integer.toHexString((Integer.parseInt(LCTCTR,16)+newString.length()));       
     
            }
         System.out.println( LCTCTR);
        }

        
        else{
            
          System.out.println("error");   
        }
        
        }else{
            System.out.println("this is comment");
        }
      //  System.out.println(opcode);
      s.writeToIntermediate(f);
        strLine = br.readLine();
        s.changeinst(strLine);
         opcode= s.map.get("column2");
         
    }
       pass = 2;
            }  
     
        }
 
  

}

