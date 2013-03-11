package helper;

import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Utility class for XML parsing and text extraction.
 *
 */
public class XMLUtil {
	private static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	
	/**
	 * Method to create Document from a input XML Stream.
	 * 
	 * @param xmlStream - input stream
	 * 
	 * @return Document - XML Document
	 */
	public static Document parseXML(InputStream xmlStream) {
		Document doc = null;
		
		try {
            DocumentBuilder db = dbf.newDocumentBuilder();
 
            doc = db.parse(xmlStream); 
 
        } catch (Exception e) {
        }
            
        return doc;
	}
	
	/**
	 * Method to get the Text value from the given node
	 * 
	 * @param node - Node
	 * 
	 * @return - text value of the Node
	 */
	public static String getText(Node node) {
		return getAllText(node).trim();
	}
	
	private static String getAllText(Node node) {
		StringBuffer result = new StringBuffer();
	    if (! node.hasChildNodes()) return "";

	    NodeList list = node.getChildNodes();
	    
	    for (int i=0; i < list.getLength(); i++) {
	        Node subnode = list.item(i);
	        
	        if (subnode.getNodeType() == Node.TEXT_NODE
	        		|| subnode.getNodeType() == Node.CDATA_SECTION_NODE) {
	            result.append(subnode.getNodeValue()).append(" ");
	        }
	        else
	        {
	            // Recurse into the subtree for text
	            // (and ignore comments)
	            result.append(getAllText(subnode));
	        }
	    }
	    
	    return result.toString();
	}
	
	/**
	 * Method to get attribute value for given node and attribute.
	 * 
	 * @param n - Node
	 * @param attrName - Attribute Name
	 * 
	 * @return value of the attribute.
	 */
	public static String getValue(Node n, String attrName) {
		String val = null;
					
		if (n != null && n.getAttributes() != null && n.getAttributes().getNamedItem(attrName) != null) {
			val = n.getAttributes().getNamedItem(attrName).getNodeValue();
		}
		
		return val;
	}
	
	/**
	 * Method to get Node matching Tag Name with Attribute name and Attribute value.
	 * 
	 * @param node - XML Node
	 * @param tagName - Tag Name
	 * @param attrName - Attribute Name
	 * @param attrValue - Attribute Value

	 * @return Node
	 */
	public static Node getNode(Node node, String tagName, String attrName, String attrValue) {
		Node val = null;
		
		if (node != null) {
			NodeList items = node.getChildNodes();
			
			if (items!=null) {
				for (int j = 0; j < items.getLength(); j++) {
					Node n = items.item(j);
					
					if (n != null && n.getNodeName().equals(tagName)
							&& n.getAttributes().getNamedItem(attrName) != null
							&& attrValue.equals(n.getAttributes().getNamedItem(attrName).getNodeValue())) {
						val = n;
						
						break;
					}
				}
			}
		}
		
		return val;
	}
	
	/**
	 * Method to get Node matching given xpath from the input xml. 
	 * 
	 * @param xml - Input XML
	 * @param path - XPath
	 * 
	 * @return Node corresponding to the xpath in the input xml.
	 * 
	 * @throws Exception
	 */
	public static Node getNode(String xml, String path) throws Exception {
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document dom = db.parse(new InputSource(new StringReader(xml)));
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		XPathExpression expr = xpath.compile(path);

		Node node = (Node) expr.evaluate(dom, XPathConstants.NODE);
		
		return node;
	}
}

