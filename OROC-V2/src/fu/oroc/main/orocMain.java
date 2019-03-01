package fu.oroc.main;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import fu.oroc.java.Concept;
import fu.oroc.java.Relation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.net.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
public class orocMain {

	public static void main(String[]args) throws IOException, SAXException, ParserConfigurationException, InterruptedException {
		ArrayList<Concept> conceptList = new ArrayList<Concept>();
        ArrayList<Relation> relationList = new ArrayList<Relation>();
        BufferedReader reader;
        orocMain.serverConnect();
	}
	
	
	public static void serverConnect() throws IOException, SAXException, ParserConfigurationException, InterruptedException {
		ServerSocket server = new ServerSocket(8080);
        System.out.println("wait for connection on port 8080");
        Random rand = new Random();
        
        boolean execute = true;
        while(execute) {
        Socket client = server.accept();
        System.out.println("got connection on port 8080");

        orocMain.orocExecution(client);
        
        }
	}
	
	public static void orocExecution(Socket client) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException, InterruptedException {
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder db = dbf.newDocumentBuilder();
         Document doc = db.parse(new FileInputStream(new File("/home/hasan/Desktop/new_Concepts.xml")));

         NodeList nodes = doc.getElementsByTagName("Concept");
         System.out.println("Number of Objects: " + nodes.getLength());
         HashMap<String, ArrayList<String>> in_material = new HashMap<String, ArrayList<String>>();
         HashMap<String, ArrayList<String>> in_shape = new HashMap<String, ArrayList<String>>();
         //Element line = (Element) name.item(0);
         //System.out.println("Name: " + getCharacterDataFromElement(line));
         System.out.println("Please give the iput object ot get replacement: ");
         Scanner sc= new Scanner(System.in);
         String inputObject= sc.nextLine();
         
         for (int j = 0; j < nodes.getLength(); j++) {
             Element element = (Element) nodes.item(j);
             NodeList name = element.getElementsByTagName("Name");
             Element name_line = (Element) name.item(0);
             System.out.println(name_line);

             if(inputObject.equals(getCharacterDataFromElement(name_line))) {
                 if(element.getAttribute("type").equalsIgnoreCase("complex")) {

                     NodeList parts = element.getElementsByTagName("Part");
                     for (int p = 0 ; p < parts.getLength() ; p++) {
                         Element e = (Element) parts.item(p);
                         NodeList sub_obj = e.getElementsByTagName("Name");
                         Element sub_obj_line = (Element) sub_obj.item(0);
                         String sub_obj_string = getCharacterDataFromElement(sub_obj_line);
                         //System.out.println(sub_obj_string);
                         for(int k = 0 ; k < nodes.getLength() ; k++) {
                             Element sub_element = (Element) nodes.item(k);
                             NodeList sub_name = sub_element.getElementsByTagName("Name");
                             Element sub_name_line = (Element) sub_name.item(0);
                             if(sub_obj_string.equalsIgnoreCase(getCharacterDataFromElement(sub_name_line))){
                                 //System.out.println(getCharacterDataFromElement(sub_name_line));
                                 getInputShapeMaterial(sub_element, in_shape, in_material, sub_name_line);
                             }
                         }

                     }
                 }
                 else {
                     getInputShapeMaterial(element, in_shape, in_material, name_line);
                     
                 }
                 break;
             }
         }
         getReplacement(client,in_material,in_shape);
	}
	
	
	private static void getReplacement(Socket client, HashMap<String,ArrayList<String>> in_material, HashMap<String,ArrayList<String>> in_shape) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		 PrintWriter out = new PrintWriter(client.getOutputStream(),true);
         BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
         for (String in_name : in_material.keySet()) {
             int p_flag = 0;
            
             ArrayList<String> replacement_material_list = new ArrayList<String>();
             ArrayList<String> in_material_list = in_material.get(in_name);
             
            //**//
             ArrayList<String> in_shape_list = in_shape.get(in_name);
             ArrayList<String> replacement_shape_list = new ArrayList<String>();
             while(p_flag <15) {
                 
                 //System.out.println(in_name);
                
                 for(int c = 0 ; c < in_material_list.size() ; c++) {
                     String toClient = in_material_list.get(c);
                     //System.out.println("toClient: " + toClient);
                     out.println(toClient);
                     String fromClient = in.readLine();
               
                     if(replacement_material_list.contains(fromClient)) 
                     {
                    	 //System.out.println("...");
                     }else
                     {
                      //System.out.println("Replacement Material for " + toClient + ": " + fromClient);
                   	  
                     }
                     replacement_material_list.add(fromClient);
                     
                     
                 }
                 out.println("material done");
                 

                 TimeUnit.SECONDS.sleep(1);

                

                
                 for(int c = 0 ; c < in_shape_list.size() ; c++) {
                     String toClient2 = in_shape_list.get(c);
                     out.println(toClient2);
                     String fromClient2 = in.readLine();
                     
                     if(replacement_shape_list.contains(fromClient2)) {
                    	 
                    	 //System.out.println("...");
                    	
                     }else {
                    	 //System.out.println("Replacement Shape for " + toClient2 + ": " + fromClient2);
                    	
                    	 
                     }
                    
                     replacement_shape_list.add(fromClient2);
                 }
                 out.println("shape done");
		
                 p_flag++;
	}
             System.out.println("All the replacement material for "+in_name+":"+removeDuplicate(replacement_material_list));
             System.out.println("All the replacement shape for "+in_name+":"+removeDuplicate(replacement_shape_list));
         }
	}
	
	private static ArrayList<String> removeDuplicate(ArrayList<String> list) 
	{
		ArrayList<String> newList = new ArrayList<String>(); 
		  
        // Traverse through the first list 
        for (String element : list) { 
  
            // If this element is not present in newList 
            // then add it 
            if (!newList.contains(element)) { 
  
                newList.add(element); 
            } 
        } 
  
        // return the new list 
        return newList; 
	}


	private static void getInputShapeMaterial(Element element, HashMap<String, ArrayList<String>> in_shape,
			HashMap<String, ArrayList<String>> in_material, Element name_line) {
		ArrayList<String> parts_shape_list = new ArrayList<String>();
        ArrayList<String> parts_material_list = new ArrayList<String>();
        NodeList material = element.getElementsByTagName("Material");
        for(int m = 0 ; m < material.getLength() ; m++) {
            Element material_line = (Element) material.item(m);
            parts_material_list.add(getCharacterDataFromElement(material_line));
            System.out.println("Input Material: " + getCharacterDataFromElement(material_line));
        }
        in_material.put(getCharacterDataFromElement(name_line), parts_material_list);

        NodeList shape = element.getElementsByTagName("Shape");
        for(int s = 0 ; s < shape.getLength() ; s++) {
            Element shape_line = (Element) shape.item(s);
            parts_shape_list.add(getCharacterDataFromElement(shape_line));
            System.out.println("Input Shape: " + getCharacterDataFromElement(shape_line));
        }
        in_shape.put(getCharacterDataFromElement(name_line), parts_shape_list);
		
		
	}
	
	


	//*//
	
	public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";

    }
}
	
