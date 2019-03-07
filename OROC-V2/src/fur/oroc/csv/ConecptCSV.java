package fur.oroc.csv;

import java.util.ArrayList;
import java.util.List;

public class ConecptCSV {

	private String Name;
	private String Material;
	private String Shape;
	private int NoOfObjects;
	private List<String> ObjectsList;
	
	public ConecptCSV(String name, String material,String shape, int noofObj, ArrayList<String> objectsList){
	
		this.Name= name;
		this.Material=material;
		this.Shape=shape;
		this.NoOfObjects=noofObj;
		this.ObjectsList=objectsList;
	}
	
	
	//public void setName(String name) {
		//this.Name= name;
	//}
	public String getName() {
		return this.Name;
	}
	
	//public void setMaterial(String material) {
		//this.Material=material;
		
	//}
	public String getMaterial() {
		return this.Material;
	}
//	public void setNoOfObjects(int noofObj) {
//		this.NoOfObjects = noofObj;
//	}
	public int getNoOfObjects() {
		return this.NoOfObjects;
	}
//	public void setShape(String shape) {
//		this.Shape=shape;
//		
//	}
	public String getShape() {
		return this.Shape;
	}
//	public void setObjectsList(List<String> objectsList) {
//		this.ObjectsList = objectsList;
//	}
//	
	public List<String> getobjectsList() {
		return this.ObjectsList;
		
	}
}
