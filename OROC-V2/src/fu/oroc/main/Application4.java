package fu.oroc.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.net.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import fu.oroc.transform.*;
import fu.oroc.java.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.CharacterData;


public class Application4 {

    public static void getInputShapeMaterial(Element element, HashMap<String, ArrayList<String>> in_shape, HashMap<String, ArrayList<String>> in_material, Element name_line) {
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

    public static void main(String[] args) throws Exception {

        ArrayList<Concept> conceptList = new ArrayList<Concept>();
        ArrayList<Relation> relationList = new ArrayList<Relation>();
        BufferedReader reader;
       /* reader = new BufferedReader(new FileReader("/Users/apple/Downloads/OROC-FU-master/classical_problem/Candle_problem"));
        String prob_line = reader.readLine();
        String s_flag = "";

        while (prob_line != null) {
            if(!prob_line.equals("")) {
                if(prob_line.equalsIgnoreCase("Concepts:")) {
                    s_flag = "concepts";
                }
                else if(prob_line.equalsIgnoreCase("Relations:")) {
                    s_flag = "relations";
                }
                else if(prob_line.equalsIgnoreCase("Goal:")) {
                    s_flag = "goal";
                }
                else {
                    if(s_flag.equalsIgnoreCase("concepts")) {
                        //System.out.println("concept:" + prob_line);
                        Concept c = new Concept(prob_line);
                        conceptList.add(c);
                    }
                    else if(s_flag.equalsIgnoreCase("relations")) {
                        //System.out.println("relation:" + prob_line);
                        String[] values = prob_line.split(",");
                        Concept c1 = null;
                        Concept c2 = null;
                        for(int i = 0 ; i < conceptList.size() ; i++) {
                            String temp = conceptList.get(i).getConcept();
                            if(temp.equalsIgnoreCase(values[1])) {
                                c1 = conceptList.get(i);
                            }
                            if(temp.equalsIgnoreCase(values[2])) {
                                c2 = conceptList.get(i);
                            }
                        }
                        Relation r1 = new Relation(values[0], c1, c2);
                    }
                    else {
                        //System.out.println("goal:" + prob_line);
                    }
                }
            }
            // read next line
            prob_line = reader.readLine();
        }
        reader.close();
*/
        ServerSocket server = new ServerSocket(8080);
        System.out.println("wait for connection on port 8080");
        Random rand = new Random();

        boolean run = true;
        while(run) {
            Socket client = server.accept();
            System.out.println("got connection on port 8080");

            Concept c1 = new Concept("Ceiling");
            Concept c2 = new Concept("Pendulum");
            Concept c3 = new Concept("Pendulum");
            Concept c4 = new Concept("Book");

            Relation r1 = new Relation("hang", c1, c2);
            Relation r2 = new Relation("hang", c1, c3);

            Set<Concept> concepts = new HashSet<Concept>();
            concepts.add(c1);
            concepts.add(c2);
            concepts.add(c3);

            Set<Relation> relations = new HashSet<Relation>();
            relations.add(r1);
            relations.add(r2);

            // Goal ??


            Decomposition d = new Decomposition();
            concepts = d.decompose(concepts);

            Replacement r = new Replacement();
            concepts = r.replace(concepts);

            Integration i = new Integration();


            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new FileInputStream(new File("/home/hasan/Desktop/new_Concepts.xml")));

            NodeList nodes = doc.getElementsByTagName("Concept");
            System.out.println("Number of Objects: " + nodes.getLength());
            HashMap<String, ArrayList<String>> in_material = new HashMap<String, ArrayList<String>>();
            HashMap<String, ArrayList<String>> in_shape = new HashMap<String, ArrayList<String>>();
            //Element line = (Element) name.item(0);
            //System.out.println("Name: " + getCharacterDataFromElement(line));
            System.out.println("Input Object: " + c4.getConcept());
            for (int j = 0; j < nodes.getLength(); j++) {
                Element element = (Element) nodes.item(j);
                NodeList name = element.getElementsByTagName("Name");
                Element name_line = (Element) name.item(0);
                System.out.println(name_line);

                if(c4.getConcept().equals(getCharacterDataFromElement(name_line))) {
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

            PrintWriter out = new PrintWriter(client.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            for (String in_name : in_material.keySet()) {
                int p_flag = 0;
                while(p_flag == 0) {
                    ArrayList<String> replacement_material_list = new ArrayList<String>();
                    ArrayList<String> in_material_list = in_material.get(in_name);
                    System.out.println(in_name);
                    for(int c = 0 ; c < in_material_list.size() ; c++) {
                        String toClient = in_material_list.get(c);
                        //System.out.println("toClient: " + toClient);
                        out.println(toClient);
                        String fromClient = in.readLine();
                        System.out.println("Replacement Material for " + toClient + ": " + fromClient);
                        replacement_material_list.add(fromClient);
                    }
                    out.println("material done");

                    TimeUnit.SECONDS.sleep(1);

                    ArrayList<String> in_shape_list = in_shape.get(in_name);
                    ArrayList<String> replacement_shape_list = new ArrayList<String>();

                    for(int c = 0 ; c < in_shape_list.size() ; c++) {
                        String toClient2 = in_shape_list.get(c);
                        out.println(toClient2);
                        String fromClient2 = in.readLine();
                        System.out.println("Replacement Shape for " + toClient2 + ": " + fromClient2);
                        replacement_shape_list.add(fromClient2);
                    }
                    out.println("shape done");

                    // To check if at least one material exists in the replacement_material_list
                    int n = rand.nextInt(replacement_material_list.size());
                    String fromClientMaterial = replacement_material_list.get(n);
                    Boolean no_replacement_material = false;
                    if(fromClientMaterial == null || fromClientMaterial.length() == 0) {
                        int flag = 0;
                        n = replacement_material_list.size();
                        for(int y = 0 ; y < n ; y++) {
                            fromClientMaterial = replacement_material_list.get(y);
                            if(fromClientMaterial != null && fromClientMaterial.length() != 0) {
                                flag = 1;
                                break;
                            }
                        }
                        if(flag == 0) {
                            no_replacement_material = true;
                        }
                    }

                    // To check if at least one shape exists in the replacement_shape_list
                    n = rand.nextInt(replacement_shape_list.size());
                    String fromClientShape = replacement_shape_list.get(n);
                    Boolean no_replacement_shape = false;
                    if(fromClientShape == null || fromClientShape.length() == 0) {
                        int flag = 0;
                        n = replacement_shape_list.size();
                        for(int y = 0 ; y < n ; y++) {
                            fromClientShape = replacement_shape_list.get(y);
                            if(fromClientShape != null && fromClientShape.length() != 0) {
                                flag = 1;
                                break;
                            }
                        }
                        if(flag == 0) {
                            no_replacement_shape = true;
                        }
                    }

                    if(!no_replacement_material && !no_replacement_shape) {
                        System.out.println("Chosen material: " + fromClientMaterial);
                        System.out.println("Chosen shape: " + fromClientShape);
                        int count = 0;
                        ArrayList<String> probable_replacements = new ArrayList<String>();
                        for (int j = 0; j < nodes.getLength(); j++) {
                            Element element = (Element) nodes.item(j);
                            if(element.getAttribute("type").equalsIgnoreCase("primitive")) {
                                NodeList out_material = element.getElementsByTagName("Material");
                                NodeList out_shape = element.getElementsByTagName("Shape");

                                for(int o = 0 ; o < out_material.getLength() ; o++) {
                                    Element line = (Element) out_material.item(o);
                                    if(getCharacterDataFromElement(line).toLowerCase().contains(fromClientMaterial.toLowerCase())) {
                                        for(int o2 = 0 ; o2 < out_shape.getLength() ; o2++) {
                                            Element line2 = (Element) out_shape.item(o2);
                                            if(getCharacterDataFromElement(line2).toLowerCase().contains(fromClientShape.toLowerCase())) {
                                                NodeList name = element.getElementsByTagName("Name");
                                                Element line3 = (Element) name.item(0);
                                                count = count + 1;
                                                probable_replacements.add(getCharacterDataFromElement(line3));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(probable_replacements.size() > 0) {
                            n = rand.nextInt(probable_replacements.size());
                            //System.out.println("n: " + n);
                            p_flag = 1;
                            System.out.println("Replacement Object According to Material: " + probable_replacements.get(n));
                            System.out.println("--------------");
                        }
                        else {
                            System.out.println("No replacement object in your xml file. Trying again...");
                            System.out.println("--------------");
                        }
                    }
                    else {
                        System.out.println("Either replacement_material or replacement_shape not found for this object! Trying again...");
                        System.out.println("--------------");
                    }
                }
            }
        }
    }


//		    	if(!no_replacement_shape) {
//		    		System.out.println("Chosen shape: " + fromClient2);
//		    		int count = 0;
//		            ArrayList<String> probable_replacements = new ArrayList<String>();
//		            for (int j = 0; j < nodes.getLength(); j++) {
//		            	Element element = (Element) nodes.item(j);
//		            	if(element.getAttribute("type").equalsIgnoreCase("primitive")) {
//			            	NodeList out_shape = element.getElementsByTagName("Shape");
//
//			            	for(int o = 0 ; o < out_shape.getLength() ; o++) {
//			            		Element line = (Element) out_shape.item(o);
//			            		if(getCharacterDataFromElement(line).toLowerCase().contains(fromClient2.toLowerCase())) {
//				            		//System.out.println("inside");
//				            		NodeList name = element.getElementsByTagName("Name");
//				            		Element line2 = (Element) name.item(0);
//				            		//System.out.println(getCharacterDataFromElement(line2) + " " + getCharacterDataFromElement(line).toLowerCase());
//				            		count = count + 1;
//				            		probable_replacements.add(getCharacterDataFromElement(line2));
//				            	}
//			            	}
//		            	}
//		            }
//		            if(probable_replacements.size() > 0) {
//			            n = rand.nextInt(probable_replacements.size());
//			            //System.out.println("n: " + n);
//			            System.out.println("Replacement Object According to Shape: " + probable_replacements.get(n));
//			            System.out.println("--------------");
//		            }
//		            else {
//		            	System.out.println("No replacement object in your xml file");
//		            }
//		    	}
//		    	else {
//		    		System.out.println("No replacement shape found for this object!");
//		    	}

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";

    }

}