package fu.oroc.main;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class orocGetSameMSObject1 {
	
public  ArrayList<String> result(NodeList nodes, ArrayList<String> material_list, ArrayList<String> shape_list) {
		
		if((material_list.isEmpty())||(shape_list.isEmpty())) 
		{
			System.out.println("No Object based on material found possible");
			return null;
		}else {
			
			ArrayList<String> replacementObject = new ArrayList<String>();
			int count = material_list.size();
			for(int i = 0;i<count;i++) {
				String choosenMaterial = material_list.get(i);
				
				for (int k =0; k<shape_list.size();k++) {
					String choosenShape = shape_list.get(k);
					for (int j = 0; j < nodes.getLength(); j++) {
		            	Element element = (Element) nodes.item(j);
		            	if(element.getAttribute("type").equalsIgnoreCase("primitive")) {
			            	NodeList material_list1 = element.getElementsByTagName("Material");
			            	NodeList shape_list1 = element.getElementsByTagName("Shape");

			            	for(int o = 0 ; o < material_list1.getLength() ; o++) {
			            		Element line = (Element) material_list1.item(o);
			            		if(orocMain.getCharacterDataFromElement(line).toLowerCase().contains(choosenMaterial.toLowerCase())) {
			            			for(int o2 = 0 ; o2 < shape_list1.getLength() ; o2++) {
					            		Element line2 = (Element) shape_list1.item(o2);
					            		if(orocMain.getCharacterDataFromElement(line2).toLowerCase().contains(choosenShape.toLowerCase())) {
					            			NodeList name = element.getElementsByTagName("Name");
						            		Element line3 = (Element) name.item(0);
						            		//count = count + 1;
						            		replacementObject.add(orocMain.getCharacterDataFromElement(line3));
					            		}
			            			}
				            	}
			            	}
		            	}
		            }
				}
			}
			return replacementObject;
			//System.out.println("Replacement Object based on same shape and material:"+ replacementObject);
			

}
}

public ArrayList<String> result1(NodeList nodes, ArrayList<String> material_list, ArrayList<String> shape_list, ArrayList<Double> shape_score) {
	
	if((material_list.isEmpty())||(shape_list.isEmpty())) 
	{
		System.out.println("No Object based on material found possible");
		return null;
	}else {
		
		ArrayList<String> replacementObject = new ArrayList<String>();
		int count = material_list.size();
		for(int i = 0;i<count;i++) {
			String choosenMaterial = material_list.get(i);
			
			for (int k =0; k<shape_list.size();k++) {
				String choosenShape = shape_list.get(k);
				for (int j = 0; j < nodes.getLength(); j++) {
	            	Element element = (Element) nodes.item(j);
	            	if(element.getAttribute("type").equalsIgnoreCase("primitive")) {
		            	NodeList material_list1 = element.getElementsByTagName("Material");
		            	NodeList shape_list1 = element.getElementsByTagName("Shape");

		            	for(int o = 0 ; o < material_list1.getLength() ; o++) {
		            		Element line = (Element) material_list1.item(o);
		            		if(orocMain.getCharacterDataFromElement(line).toLowerCase().contains(choosenMaterial.toLowerCase())) {
		            			for(int o2 = 0 ; o2 < shape_list1.getLength() ; o2++) {
				            		Element line2 = (Element) shape_list1.item(o2);
				            		if(orocMain.getCharacterDataFromElement(line2).toLowerCase().contains(choosenShape.toLowerCase())) {
				            			NodeList name = element.getElementsByTagName("Name");
					            		Element line3 = (Element) name.item(0);
					            		//count = count + 1;
					            		replacementObject.add((orocMain.getCharacterDataFromElement(line3)+"("+"-"+","+shape_score.get(i)+")").toString());
				            		}
		            			}
			            	}
		            	}
	            	}
	            }
			}
		}
		return replacementObject;
		//System.out.println("Replacement Object based on same material and similar shapes:"+ replacementObject);
		

}
}

public ArrayList<String> result2(NodeList nodes, ArrayList<String> material_list, ArrayList<String> shape_list, ArrayList<Double> material_score) {
	
	if((material_list.isEmpty())||(shape_list.isEmpty())) 
	{
		System.out.println("No Object based on material found possible");
		return null;
	}else {
		
		ArrayList<String> replacementObject = new ArrayList<String>();
		int count = shape_list.size();
		for(int i = 0;i<count;i++) {
			String choosenShape = shape_list.get(i);
			
			for (int k =0; k<material_list.size();k++) {
				String choosenMaterial = material_list.get(k);
				for (int j = 0; j < nodes.getLength(); j++) {
	            	Element element = (Element) nodes.item(j);
	            	if(element.getAttribute("type").equalsIgnoreCase("primitive")) {
		            	NodeList material_list1 = element.getElementsByTagName("Material");
		            	NodeList shape_list1 = element.getElementsByTagName("Shape");

		            	for(int o = 0 ; o < shape_list1.getLength() ; o++) {
		            		Element line = (Element) shape_list1.item(o);
		            		if(orocMain.getCharacterDataFromElement(line).toLowerCase().contains(choosenShape.toLowerCase())) {
		            			for(int o2 = 0 ; o2 < material_list1.getLength() ; o2++) {
				            		Element line2 = (Element) material_list1.item(o2);
				            		if(orocMain.getCharacterDataFromElement(line2).toLowerCase().contains(choosenMaterial.toLowerCase())) {
				            			NodeList name = element.getElementsByTagName("Name");
					            		Element line3 = (Element) name.item(0);
					            		//count = count + 1;
					            		replacementObject.add((orocMain.getCharacterDataFromElement(line3)+"("+material_score.get(i)+","+"-"+")").toString());
				            		}
		            			}
			            	}
		            	}
	            	}
	            }
			}
		}
		
	
		return replacementObject;
		//System.out.println("Replacement Object based on same shape and similar materials:"+ replacementObject);
		

}
}
}
