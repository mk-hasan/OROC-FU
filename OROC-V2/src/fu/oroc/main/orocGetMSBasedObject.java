package fu.oroc.main;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class orocGetMSBasedObject {

	public ArrayList<String> result(NodeList nodes, ArrayList<String> replacement_material_list, ArrayList<String> replacement_shape_list) {
		
		if((replacement_material_list.isEmpty())||(replacement_shape_list.isEmpty())) 
		{
			System.out.println("No Object based on material found possible");
			return null;
		}else {
			
			ArrayList<String> replacementObject = new ArrayList<String>();
			if(replacement_material_list.size()>replacement_shape_list.size()){
				int count = replacement_shape_list.size();
				for(int i = 0;i<count;i++) {
					String choosenShape = replacement_shape_list.get(i);
					
					for (int k =0; k<replacement_material_list.size();k++) {
						String choosenMaterial = replacement_material_list.get(k);
						for (int j = 0; j < nodes.getLength(); j++) {
			            	Element element = (Element) nodes.item(j);
			            	if(element.getAttribute("type").equalsIgnoreCase("primitive")) {
				            	NodeList material_list = element.getElementsByTagName("Material");
				            	NodeList shape_list = element.getElementsByTagName("Shape");
	
				            	for(int o = 0 ; o < shape_list.getLength() ; o++) {
				            		Element line = (Element) material_list.item(o);
				            		if(orocMain.getCharacterDataFromElement(line).toLowerCase().contains(choosenShape.toLowerCase())) {
				            			for(int o2 = 0 ; o2 < material_list.getLength() ; o2++) {
						            		Element line2 = (Element) material_list.item(o2);
						            		if(orocMain.getCharacterDataFromElement(line2).toLowerCase().contains(choosenMaterial.toLowerCase())) {
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
			}else {
				int count = replacement_material_list.size();
				for(int i = 0;i<count;i++) {
					String choosenMaterial = replacement_material_list.get(i);
					
					for (int k =0; k<replacement_shape_list.size();k++) {
						String choosenShape = replacement_shape_list.get(k);
						for (int j = 0; j < nodes.getLength(); j++) {
			            	Element element = (Element) nodes.item(j);
			            	if(element.getAttribute("type").equalsIgnoreCase("primitive")) {
				            	NodeList material_list = element.getElementsByTagName("Material");
				            	NodeList shape_list = element.getElementsByTagName("Shape");
	
				            	for(int o = 0 ; o < material_list.getLength() ; o++) {
				            		Element line = (Element) material_list.item(o);
				            		if(orocMain.getCharacterDataFromElement(line).toLowerCase().contains(choosenMaterial.toLowerCase())) {
				            			for(int o2 = 0 ; o2 < shape_list.getLength() ; o2++) {
						            		Element line2 = (Element) shape_list.item(o2);
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
				
			}
			return replacementObject;
			//System.out.println("Replacement Object based on Material and Shape similarity: "+replacementObject);
		}
		
	}
}
