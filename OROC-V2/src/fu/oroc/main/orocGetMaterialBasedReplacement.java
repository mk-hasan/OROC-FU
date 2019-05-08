package fu.oroc.main;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class orocGetMaterialBasedReplacement {

	
	public void result(NodeList nodes, ArrayList<String> replacement_material_list) {
		
		if(replacement_material_list.isEmpty()) 
		{
			System.out.println("No Object based on material found possible");
			
		}
		else 
		{
			ArrayList<String> replacementObject = new ArrayList<String>();
			for(int l=0;l<replacement_material_list.size();l++) {
			String choosenmaterial = replacement_material_list.get(l);
			
			for(int j=0;j<nodes.getLength();j++)
			{
				Element element = (Element) nodes.item(j);
				if(element.getAttribute("type").equalsIgnoreCase("primitive")) {
					NodeList material_list = element.getElementsByTagName("Material");
					
					
					for(int count =0; count<material_list.getLength();count++)
					{
						Element line = (Element) material_list.item(count);
						if(orocMain.getCharacterDataFromElement(line).toLowerCase().contains(choosenmaterial.toLowerCase())) {
							NodeList objectName = element.getElementsByTagName("Name");
							Element line2 = (Element) objectName.item(0);
							replacementObject.add(orocMain.getCharacterDataFromElement(line2));
						}
					}
				}
				
			}
		}
			//System.out.println("Replacement Object based on material similarity:"+replacementObject);
			
		}
		
	}
	
}
