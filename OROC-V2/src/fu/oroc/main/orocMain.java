package fu.oroc.main;
import java.io.*;
import java.util.List;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import fur.oroc.csv.CSVReader;
import fur.oroc.csv.CSVReaderMaterial;
import fur.oroc.csv.CSVUtils;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fu.oroc.java.Concept;
import fu.oroc.java.Relation;
import fur.oroc.csv.ConecptCSV;
import fur.oroc.csv.CsVMain;
public class orocMain {

	public static void main(String[]args) throws Exception {
		ArrayList<Concept> conceptList = new ArrayList<Concept>();
        ArrayList<Relation> relationList = new ArrayList<Relation>();
        BufferedReader reader;
        orocMain.serverConnect();
        //ConecptCSV cc = new ConecptCSV();
	}
	
	
	public static void serverConnect() throws Exception {
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
	
	public static void orocExecution(Socket client) throws Exception {
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder db = dbf.newDocumentBuilder();
         Document doc = db.parse(new FileInputStream(new File("/home/hasan/Desktop/new_Concepts.xml")));

         HashMap<String, ArrayList<String>> in_material = new HashMap<String, ArrayList<String>>();
         HashMap<String, ArrayList<String>> in_shape = new HashMap<String, ArrayList<String>>();
        
         NodeList nodes = doc.getElementsByTagName("Concept");
         System.out.println("Number of Objects: " + nodes.getLength());


         for(int count =0;count<nodes.getLength();count++) {
        
        
        
         Element element1= (Element) nodes.item(count);
         NodeList nl = element1.getElementsByTagName("Name");
         Element inputObj=(Element)nl.item(0);
         
         //System.out.println("Name: " + getCharacterDataFromElement(line));
         
         Scanner sc= new Scanner(System.in);
         //String inputObject= sc.nextLine();
         String inputObject =getCharacterDataFromElement(inputObj);
         
         System.out.println("Object :"+inputObject);
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
         getReplacement(nodes,client,in_material,in_shape,inputObject);
         }
         
	}
	
	
	private static void getReplacement(NodeList nodes,Socket client, HashMap<String,ArrayList<String>> in_material, HashMap<String,ArrayList<String>> in_shape, String inputObject) throws Exception {
		// TODO Auto-generated method stub
		 PrintWriter out = new PrintWriter(client.getOutputStream(),true);
         BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
         for (String in_name : in_material.keySet()) {
             int p_flag = 0;
            
             ArrayList<String> replacement_material_list = new ArrayList<String>();
             ArrayList<String> replacement_material_list1 = new ArrayList<String>();
             ArrayList<String> in_material_list = in_material.get(in_name);
             ArrayList<Double> material_score = new ArrayList<>();
             ArrayList<Double> shape_score = new ArrayList<>();
             
             
             
            //**//
             ArrayList<String> in_shape_list = in_shape.get(in_name);
             ArrayList<String> replacement_shape_list = new ArrayList<String>();
             ArrayList<String> replacement_shape_list1 = new ArrayList<String>();
             
            
             while(p_flag <15) {
                 
                 //System.out.println(in_name);
                
                 for(int c = 0 ; c < in_material_list.size() ; c++) {
                     String toClient = in_material_list.get(c);
                     //System.out.println("toClient: " + toClient);
                     out.println(toClient);
                     String fromClient = in.readLine();
               
                     //System.out.println("fromClient:"+fromClient);
                     if(replacement_material_list.contains(fromClient)) 
                     {
                    	 //System.out.println("...");
                     }else
                     {
                      //System.out.println("Replacement Material for " + toClient + ": " + fromClient);
                   	  
                     }
                     double score = CSVReaderMaterial.getConfidenceScore(toClient, fromClient);
                     System.out.println("SCORE:"+ score);
                     if(score>=3.5) {
                    	 replacement_material_list1.add(fromClient);
                    	 replacement_material_list1 = removeDuplicate(replacement_material_list1);
                    	 
                    	 material_score.add(score);
                    	 material_score = removeDuplicateDouble(material_score);
                    	 
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
                     double score = CSVReader.getConfidenceScore(toClient2, fromClient2);
                     //System.out.println("SCORE:"+ score);
                     if(score>=3.5) {
                    	 replacement_shape_list1.add(fromClient2);
                    	 replacement_shape_list1 = removeDuplicate(replacement_shape_list1);
                    	 shape_score.add(score);
                    	 shape_score=removeDuplicateDouble2(shape_score);
                     }
                    
                     replacement_shape_list.add(fromClient2);
                 }
                 out.println("shape done");
		
                 p_flag++;
	}
             replacement_material_list = removeDuplicate(replacement_material_list);
             replacement_shape_list=removeDuplicate(replacement_shape_list);
             
             //replacement_material_list1 = removeDuplicate(replacement_material_list1);
             //replacement_shape_list1 = removeDuplicate(replacement_shape_list1);
             
             //material_score = removeDuplicateDouble(material_score);
             //shape_score= removeDuplicateDouble2(shape_score);
             
             System.out.println("materials with threshold:"+replacement_material_list1);
             System.out.println("material score"+material_score);
             System.out.println("shapes with threshold:"+replacement_shape_list1);
             System.out.println("shapes score"+shape_score);
             
             
            
             System.out.println("All the replacement material for "+in_name+":"+replacement_material_list);
             System.out.println("All the replacement shape for "+in_name+":"+replacement_shape_list);
             
             orocMain.getReplacementObject(nodes,replacement_material_list,replacement_shape_list,in_material,in_shape,inputObject,replacement_material_list1, replacement_shape_list1,material_score,shape_score);
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
	
	private static ArrayList<Double> removeDuplicateDouble(ArrayList<Double> list) 
	{
		ArrayList<Double> newList = new ArrayList<Double>(); 
		  int c=0;
        // Traverse through the first list 
        for (Double element : list) { 
  
            // If this element is not present in newList 
            // then add it 
        	if(element == 7.0) {
        		if(newList.contains(element)) {
        			if(c<2) {
        				newList.add(element); 
        				c++;
        			}
        			
        		}else {
        			newList.add(element); 
        			c++;
        			
        		}
        	}else {
        		if (!newList.contains(element)) { 
        			  
                    newList.add(element); 
                }
        	
        	}
             
        } 
  
        // return the new list 
        return newList; 
	}
	
	private static ArrayList<Double> removeDuplicateDouble2(ArrayList<Double> list) 
	{
		ArrayList<Double> newList = new ArrayList<Double>(); 
		  int c=0;
        // Traverse through the first list 
        for (Double element : list) { 
  
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
	
	
	public static void getReplacementObject(NodeList nodes, ArrayList<String> replacement_material_list, ArrayList<String> replacement_shape_list, HashMap<String,ArrayList<String>> in_material, HashMap<String,ArrayList<String>> in_shape, String inputObject, ArrayList<String> replacement_material_list1, ArrayList<String> replacement_shape_list1, ArrayList<Double> material_score, ArrayList<Double> shape_score) throws Exception {


        orocGetMaterialBasedReplacement mbr = new orocGetMaterialBasedReplacement();
		orocGetShapeBasedReplacement sbr = new orocGetShapeBasedReplacement();
		mbr.result(nodes,replacement_material_list);
		ArrayList<String> sbrlist = sbr.result(nodes, replacement_shape_list);
		orocGetMSBasedObject omsbr = new orocGetMSBasedObject();
		orocGetMSBasedObject1 omsbr1 = new orocGetMSBasedObject1();
		ArrayList<String> omsbrList=omsbr.result(nodes,replacement_material_list,replacement_shape_list);
		ArrayList<String> omsbrList1 = omsbr1.result(nodes, replacement_material_list1, replacement_shape_list1,material_score,shape_score);
				
				
		ArrayList<String> in_material1 = null;
		ArrayList<String> in_shape1 = null;
		for(String in_materialkey : in_material.keySet()){
		in_material1 = in_material.get(in_materialkey);
		}
		for(String in_shapekey : in_shape.keySet()){
			in_shape1 = in_shape.get(in_shapekey);
			}
		//System.out.println("Input material"+in_material1);
		
		orocGetSameMSObject sms = new orocGetSameMSObject();
		ArrayList<String> smsList =sms.result(nodes, in_material1, in_shape1);
		ArrayList<String> smdsList=sms.result1(nodes, in_material1, replacement_shape_list);
		ArrayList<String> dmssList=sms.result2(nodes, replacement_material_list, in_shape1);
		
		orocGetSameMSObject1 sms1 = new orocGetSameMSObject1();
		ArrayList<String> smdsList1=sms1.result1(nodes, in_material1, replacement_shape_list1,shape_score);
		ArrayList<String> dmssList1=sms1.result2(nodes, replacement_material_list1, in_shape1,material_score);
		
		
		
	
		
		//System.out.println("Return sahpe based replacement"+sbrlist);
		System.out.println("Same Material and Same Shpaes: "+smsList);
		System.out.println("Same Material and Different Shapes: "+smdsList);
		System.out.println("Different Material and Same Shapes: "+dmssList);
		System.out.println("Different Material and Different Shapes: "+omsbrList);
		
		System.out.println("Same Material and Different Shapes with threshold: "+smdsList1);
		System.out.println("Different Material and Same Shapes with threshold: "+dmssList1);
		System.out.println("Different Material and Different Shapes with threshold: "+omsbrList1);
		

		orocMain.makeCsv(inputObject,smsList,smdsList,dmssList,omsbrList,smdsList1,dmssList1,omsbrList1, material_score, shape_score);
		 in_material.clear();
         in_shape.clear();
		
	}
	


	//*//
	public static void makeCsv(String inputObject, ArrayList<String> smsList, ArrayList<String> smdsList, ArrayList<String> dmssList, ArrayList<String> omsbrList, ArrayList<String> smdsList1, ArrayList<String> dmssList1, ArrayList<String> omsbrList1, ArrayList<Double> material_score, ArrayList<Double> shape_score) throws Exception {
		int smsListLength= smsList.size();
		int smdsListLength = smdsList.size();
		int dmssListLength = dmssList.size();
		int omdbrListLenght = omsbrList.size();
		int smdsListLength1=0;
		
		try {
			if(smdsList1.size()!=0) {
				smdsListLength1=smdsList1.size();
			}else {
				System.out.println("nullpoint");
				smdsListLength1=0;
			}
		} catch (NullPointerException e) {
			// TODO: handle exception
			System.out.print("value is null");
		}
		
		
		int dmssListLength1 = 0;
		try {
			if(dmssList1.size()!=0) {
				dmssListLength1=dmssList1.size();
			}else {
				System.out.println("nullpoint");
				dmssListLength1=0;
			}
		} catch (NullPointerException e) {
			// TODO: handle exception
			System.out.print("value is null");
		}
		int omdbrListLenght1 = 0 ;
		try {
			if(omsbrList1.size()!=0) {
				omdbrListLenght1=omsbrList1.size();
			}else {
				System.out.println("nullpoint");
				omdbrListLenght1=0;
			}
		} catch (NullPointerException e) {
			// TODO: handle exception
			System.out.print("value is null");
		}
		
		int ln = 0;
		ArrayList<String> emp = new ArrayList<>();
		
		//ConecptCSV cc =new ConecptCSV();
	    List<ConecptCSV> ccList = Arrays.asList(
	    		new ConecptCSV(inputObject,"Same","Same",smsListLength,smsList,ln,emp),
	    		new ConecptCSV(inputObject,"Same","SOM",smdsListLength,smdsList,smdsListLength1,smdsList1),
	    		new ConecptCSV(inputObject,"SOM","Same",dmssListLength,dmssList,dmssListLength1,dmssList1),
	    		new ConecptCSV(inputObject,"SOM","SOM",omdbrListLenght,omsbrList,omdbrListLenght1,omsbrList1));  		
	    		 
//		for(int count = 1;count<5;count++) {
//	
//			
//			switch(count) {
//			case 1:
//				cc.setName(inputObject);
//				cc.setMaterial("Same");
//				cc.setShape("Same");
//				cc.setNoOfObjects(smsListLength);
//				cc.setObjectsList(smsList);
//				break;
//			case 2:
//				cc.setName(inputObject);
//				cc.setMaterial("SOM");
//				cc.setShape("Same");
//				cc.setNoOfObjects(dmssListLength);
//				cc.setObjectsList(dmssList);
//				break;
//			case 3:
//				cc.setName(inputObject);
//				cc.setMaterial("Same");
//				cc.setShape("SOM");
//				cc.setNoOfObjects(smdsListLength);
//				cc.setObjectsList(smdsList);
//				break;
//			case 4:
//				cc.setName(inputObject);
//				cc.setMaterial("SOM");
//				cc.setShape("SOM");
//				cc.setNoOfObjects(omdbrListLenght);
//				cc.setObjectsList(omsbrList);
//				break;
//				
//			}
//			
//		}
	CsVMain.main(ccList);
		
	}
	
	public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";

    }
	
}
	
