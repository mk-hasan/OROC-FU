package fu.oroc.main;

import java.io.StringReader;
//from  ja  va 2 s .co  m
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ApplicationTest {

    public static void main(String[] args) throws Exception {
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xmlRecords));

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(is);
        NodeList nodes = doc.getElementsByTagName("employee");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);

            NodeList name = element.getElementsByTagName("name");
            Element line = (Element) name.item(0);
            System.out.println("Name: " + getCharacterDataFromElement(line));
            System.out.println("parentNode"+line.getChildNodes());

            NodeList title = element.getElementsByTagName("title");
            line = (Element) title.item(0);
            System.out.println("Title: " + getCharacterDataFromElement(line));
        }

    }
    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        Node child1 = e.getLastChild();

        if (child1 instanceof CharacterData) {
            CharacterData cd = (CharacterData) child1;
            return cd.getData();
        }
        return "";
    }
    static String xmlRecords =
            "<data>" +
                    "  <employee>" +
                    "    <name>Tom</name>"+
                    "    <title>Manager</title>" +
                    "    <title>Assistant</title>"+
                    "  </employee>" +
                    "  <employee>" +
                    "    <name>Jerry</name>"+
                    "    <title>Programmer</title>" +
                    "  </employee>" +
                    "</data>";
}