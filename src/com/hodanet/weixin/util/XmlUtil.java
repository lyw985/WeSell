package com.hodanet.weixin.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class XmlUtil {

    public static Map<String, String> xml2Map(String xmlStr) throws JDOMException, IOException {
        Map<String, String> rtnMap = new HashMap<String, String>();
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new StringReader(xmlStr));
        // õڵ
        Element root = doc.getRootElement();
        String rootName = root.getName();
        rtnMap.put("root.name", rootName);
        // õݹ麯õײԪصƺֵmap
        convert(root, rtnMap, rootName);
        return rtnMap;
    }

    /**
     * ݹ麯ҳ²Ľڵ㲢뵽mapУxml2Mapá
     * 
     * @param e xml ڵ㣬ڵ
     * @param map Ŀmap
     * @param lastname Ӹڵ㵽һڵӵִ
     */
    public static void convert(Element e, Map<String, String> map, String lastname) {
        if (e.getAttributes().size() > 0) {
            Iterator it_attr = e.getAttributes().iterator();
            while (it_attr.hasNext()) {
                Attribute attribute = (Attribute) it_attr.next();
                String attrname = attribute.getName();
                String attrvalue = e.getAttributeValue(attrname);
                map.put(lastname + "." + attrname, attrvalue);
            }
        }
        List children = e.getChildren();
        Iterator it = children.iterator();
        while (it.hasNext()) {
            Element child = (Element) it.next();
            String name = lastname + "." + child.getName();
            // ӽڵ㣬ݹ
            if (child.getChildren().size() > 0) {
                convert(child, map, name);
            } else {
                // ûӽڵ㣬ֵmap
                map.put(name, child.getText());
                // ýڵԣеֵҲmap
                if (child.getAttributes().size() > 0) {
                    Iterator attr = child.getAttributes().iterator();
                    while (attr.hasNext()) {
                        Attribute attribute = (Attribute) attr.next();
                        String attrname = attribute.getName();
                        String attrvalue = child.getAttributeValue(attrname);
                        map.put(name + "." + attrname, attrvalue);
                    }
                }
            }
        }
    }
}
