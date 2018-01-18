/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Tests.WriteInXML;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class XML {
    
    final String FILE_ADDRESS = "Setting/Setting.xml";
    
    public void ResetXmlSetting () {
        WtriteInXml(300, 150, 200);
    }
    
    public int GetXmlScore (String folther, String line) {
        int valueReturn = 0;
        
        try {

            // Create a document by parsing a XML file
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new File(folther));

            // Get a node using XPath
            XPath xPath = XPathFactory.newInstance().newXPath();
            Node node = (Node) xPath.evaluate(line, document, XPathConstants.NODE);
            
            valueReturn = Integer.parseInt(node.getTextContent());
            
        } catch (IOException | ParserConfigurationException | XPathExpressionException | DOMException | SAXException e) {
            // Handle exception
        }
        
        return valueReturn;
    }
    
    public void WriteInXml (String folther, String line, int value) {
        try {

            // Create a document by parsing a XML file
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new File(folther));

            // Get a node using XPath
            XPath xPath = XPathFactory.newInstance().newXPath();
            Node node = (Node) xPath.evaluate(line, document, XPathConstants.NODE);

            // Set the node content
            node.setTextContent(String.valueOf(value));
            
            // Write changes to a file
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(new File(folther)));

        } catch (IOException | ParserConfigurationException | TransformerException | XPathExpressionException | DOMException | SAXException e) {
            // Handle exception
        }
    }
    
    public void WtriteInXml (int value1, int value2, int value3) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            //Create the XML document
            Document documentXML = documentBuilder.newDocument();

            //Create the fist node
            Element setting = documentXML.createElement("Setting");
            documentXML.appendChild(setting); //Put the 'Setting' in the XML document

            //----  Create the Tag 'Pipes'  ----
            Element pipes = documentXML.createElement("Pipes");
            Attr id = documentXML.createAttribute("id"); //Create one attribute
            id.setValue("0");
            pipes.setAttributeNode(id); //Put the attribute on the Tag 'Pipes'
            //Put the 'Setting' on the XML file
            setting.appendChild(pipes);

            //Create the datas that is going to be put on the XML file
            Element distancePipesX = documentXML.createElement("DistancePipesX");
            Element distancePipesY = documentXML.createElement("DistancePipesY");
            Element speed = documentXML.createElement("Speed");

            //Put the value on the element variables
            distancePipesX.appendChild(documentXML.createTextNode(String.valueOf(value1)));
            distancePipesY.appendChild(documentXML.createTextNode(String.valueOf(value2)));
            speed.appendChild(documentXML.createTextNode(String.valueOf(value3)));

            //Put the value of the element variables on the XML file
            pipes.appendChild(distancePipesX);
            pipes.appendChild(distancePipesY);
            pipes.appendChild(speed);


            //Now the XML file is going to be create on the Hard Disk
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource documentSource = new DOMSource(documentXML);

            StreamResult documentoFinal = new StreamResult(new File(FILE_ADDRESS));

            transformer.transform(documentSource, documentoFinal);

        } catch (ParserConfigurationException | TransformerException ex) {
            Logger.getLogger(WriteInXML.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    public int[] GetXmlSetting() {
        int[] pipesElements = null;
        
        try {
            
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document documentXML = documentBuilder.parse(new File(FILE_ADDRESS));

            //Take all the element that are on 'Pipes' 
            NodeList pipesNode = documentXML.getElementsByTagName("Pipes");
            Node pipeItem = pipesNode.item(0);
            Element pipeElement = (Element) pipeItem;

            //Take the attributes that are on 'Pipes' Tag
            NodeList pipesNodeChil = pipeElement.getChildNodes();
            Node[] pipeItemChil = new Node[pipesNodeChil.getLength()];
            pipesElements = new int[pipesNodeChil.getLength()];
            for (int i = 0; i < pipeItemChil.length; i ++) {
                pipeItemChil[i] = pipesNodeChil.item(i);
                Element pipeElementChil = (Element) pipeItemChil[i];
                pipesElements[i] =Integer.parseInt(pipeElementChil.getTextContent());
            }

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(WriteInXML.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return pipesElements;
    }
    
}
