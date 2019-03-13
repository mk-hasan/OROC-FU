package fu.oroc.main;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class orocGetShapeBasedReplacement {
	

 
	public ArrayList<String> result(NodeList nodes, ArrayList<String> replacement_shape_list) {
	if(replacement_shape_list.isEmpty()) 
	{
		System.out.println("No Object based on material found possible");
		return null;
	}
	else 
	{
		ArrayList<String> replacementObject = new ArrayList<String>();
		for(int l=0;l<replacement_shape_list.size();l++) {
		String choosenshape = replacement_shape_list.get(l);
		
		for(int j=0;j<nodes.getLength();j++)
		{
			Element element = (Element) nodes.item(j);
			if(element.getAttribute("type").equalsIgnoreCase("primitive")) {
				NodeList material_list = element.getElementsByTagName("Shape");
				
				
				for(int count =0; count<material_list.getLength();count++)
				{
					Element line = (Element) material_list.item(count);
					if(orocMain.getCharacterDataFromElement(line).toLowerCase().contains(choosenshape.toLowerCase())) {
						NodeList objectName = element.getElementsByTagName("Name");
						Element line2 = (Element) objectName.item(0);
						replacementObject.add(orocMain.getCharacterDataFromElement(line2));
					}
				}
			}
			
		}
	}
		//return replacementObject;
		//System.out.println("Replacement Object based on shape similarity:"+replacementObject);
		return replacementObject;
	}
	
	
	
	
}

}
