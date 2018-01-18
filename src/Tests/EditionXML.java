/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
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
public class EditionXML {
    
    static final String FILE_ADDRESS_ELIMINATION = "Setting/ScoreElimination.xml";
    static final String FILE_ADDRESS_PUNCTUATION = "Setting/ScorePunctuation.xml";
    
    static Document documentXML;
    
    public static void main (String args[]) {
        
        try {

            // Create a document by parsing a XML file
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new File("Setting/Score.xml"));

            // Get a node using XPath
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/Setting/Pipes/DistancePipesX";
            Node node = (Node) xPath.evaluate(expression, document, XPathConstants.NODE);

            // Set the node content
            node.setTextContent("Whatever I want to write");

            // Write changes to a file
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(new File("Setting/Teste.xml")));

        } catch (IOException | ParserConfigurationException | TransformerException | XPathExpressionException | DOMException | SAXException e) {
            // Handle exception
        }
        
        System.exit(0);
        
        CriarScoreXml2();
        
        //As linhas abaixo é que vão criar o documento no disco em si
        try {
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource documentSource = new DOMSource(documentXML);

            StreamResult documentoFinal = new StreamResult(new File(FILE_ADDRESS_PUNCTUATION));
            transformer.transform(documentSource, documentoFinal);
            
        } catch (TransformerException ex) {
            Logger.getLogger(EditionXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static void CriarScoreXml2 () {
        
        try {
            int i, i0, i1;
            
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            
            //Cria o documento XML
            documentXML = documentBuilder.newDocument();
            
            //Cria os nó principais
            Element score = documentXML.createElement("score");
            documentXML.appendChild(score); //Coloca o 'setting' no arquivo XML
            
            //----  Cria as Tags de distancia em x  ----
            String distanceXString = "DistanceX_";
            String distanceYString = "DistanceY_";
            String speedString = "Speed_";
            Element[] distanceXElement = new Element[5];
            Element[] distanceYElement = new Element[5];
            Element[] speedElement = new Element[5];
            
            int distanceXnumber = 210;
            int distanceYnumber;
            int speedNumber;
            
            for (i = 0; i < distanceXElement.length; i ++) {
                distanceXElement[i] = documentXML.createElement(distanceXString +  String.valueOf(distanceXnumber));
                
                distanceYnumber = 130;
                for (i0 = 0; i0 < distanceXElement.length; i0 ++) {
                    distanceYElement[i0] = documentXML.createElement(distanceYString +  String.valueOf(distanceYnumber));
                    distanceYnumber += 10;
                    
                    speedNumber = 160;
                    for (i1 = 0; i1 < speedElement.length; i1 ++) {
                        speedElement[i1] = documentXML.createElement(speedString + String.valueOf(speedNumber));
                        speedElement[i1].appendChild(documentXML.createTextNode("0"));
                        
                        distanceYElement[i0].appendChild(speedElement[i1]);
                        speedNumber += 20;
                    }
                    
                    distanceXElement[i].appendChild(distanceYElement[i0]);
                }
                
                distanceXnumber += 30;
            }
            
            for (Element element : distanceXElement)
                score.appendChild(element);
 
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(WriteInXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static void CriarScoreXml1 () {
        try {
            int i, i0, i1;
            int number;
            
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            //Cria o documento XML
            documentXML = documentBuilder.newDocument();


            //Cria os nó principais
            Element score = documentXML.createElement("score");
            documentXML.appendChild(score); //Coloca o 'setting' no arquivo XML
            
            //----  Cria as Tags de distancia em x  ----
            String distanceXString = "DistanceX_";
            Element[] distanceXElement = new Element[5];
            
            number = 210;
            for (i = 0; i < distanceXElement.length; i ++) {
                distanceXElement[i] = documentXML.createElement(distanceXString +  String.valueOf(number));
                score.appendChild(distanceXElement[i]);
                number += 30;
            }
            
            //----  Cria as Tags de distancia em y dentro da de cima  ----
            String distanceYString = "DistanceY_";
            Element[][] distanceYElement = new Element[5][5];
            
            for (i = 0; i < distanceYElement.length; i ++) {
                number = 130;
                for (i0 = 0; i0 < distanceYElement[i].length; i0 ++) {
                    distanceYElement[i][i0] = documentXML.createElement(distanceYString +  String.valueOf(number));
                    distanceXElement[i].appendChild(distanceYElement[i][i0]);
                    number += 10;
                }
                
            }
            
            //----  Cria as Tags de speed para cada umas das tag de distancia em y  ----
            String speedString = "Speed_";
            Element[][][] speedElement = new Element[5][5][5];
            
            for (i = 0; i < distanceXElement.length; i ++) {
                for (i0 = 0; i0 < distanceYElement[i].length; i0 ++) {
                    number = 160;
                    for (i1 = 0; i1 < speedElement[i][i0].length; i1 ++) {
                        speedElement[i][i0][i1] = documentXML.createElement(speedString + number);
                        speedElement[i][i0][i1].appendChild(documentXML.createTextNode("0"));
                        distanceYElement[i][i0].appendChild(speedElement[i][i0][i1]);
                        number += 20;
                    }
                }
            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(WriteInXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
