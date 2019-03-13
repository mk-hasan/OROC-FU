package fur.oroc.csv;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CsVMain {
	
	public static void main(List<ConecptCSV> ccList) throws Exception {


		String csvFile ="/home/hasan/csv/developer.csv";
		FileWriter writer = new FileWriter(csvFile,true);
		//ConecptCSV cc = new ConecptCSV();
		
		//List<String> objectlist = new ArrayList<>();
		//objectlist.add("Books");
		//objectlist.add("NewsPaper");
		//cc.setName("Books");
	    //cc.setMaterial("Same");
	    //cc.setShape("SOM");
	    //cc.setNoOfObjects(7);
	    //cc.setObjectsList(objectlist);






		//CSVUtils.writeLine(writer, Arrays.asList("Name","Material","Shape","NoOfObjects","Object Lists"));

		for(ConecptCSV c : ccList) {


			List<String> list = new ArrayList<>();
			list.add(c.getName());
			list.add(c.getMaterial());
			list.add(c.getShape());
			list.add(String.valueOf(c.getNoOfObjects()).toString());
			list.addAll(c.getobjectsList());



			CSVUtils.writeLine(writer, list);
		}



			
		
		
		writer.flush();
		writer.close();
	}

}
