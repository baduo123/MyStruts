package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jasper.tagplugins.jstl.core.Set;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sun.corba.se.spi.orb.StringPair;

public class ProxyXML {
	private static Document doc;
	static{
		SAXReader saxReader = new SAXReader();
		try {
			doc = saxReader.read(ProxyXML.class.getResourceAsStream("/struts.xml"));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String readBean(String name){
		String path = "//action[@name='"+name+"']";
		Element e = (Element) doc.selectSingleNode(path);
		if (e==null) {
			return null;
		}
		return e.attributeValue("class");
	}
	
	public static Map readReturnMsg(String name){
//		List<String[]>arr = new ArrayList<String[]>();
		Map map = new HashMap();
		String path = "//action[@name='"+name+"']/result";
		Element es = (Element) doc.selectSingleNode(path);
		List<Element>list = doc.selectNodes(path);
		if (list.isEmpty()) {
			return null;
		}
		for (Element e : list) {
			String[] str = new String[3];
			str[0] = e.attributeValue("type")==null? "forward":"rediert";
			str[1] = e.getStringValue();
			str[2] = e.attributeValue("name");
			map.put(str[2], str);
		}
		

		return map;
		
	}
}
