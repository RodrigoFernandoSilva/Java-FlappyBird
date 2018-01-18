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
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class WriteInXML {
    
    static final String FILE_ADDRESS = "Setting/Setting.xml";
    
    public static void main (String args[]){

        if (!new File(FILE_ADDRESS).exists() || 1 == 0) {
            System.out.println("Arquivo XML não encontrado, será criado um!!!");
            
            EscreverNoXML(150, 55, 200);
            
        } else {
            System.out.println("Arquivo XML encontrado!!!");
            
            try {
                int[] pipesElements;
                
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document documentXML = documentBuilder.parse(new File(FILE_ADDRESS));
                
                //Pega todos os elementos com a Tag 'Pipes'
                NodeList pipesNode = documentXML.getElementsByTagName("Pipes");
                Node pipeItem = pipesNode.item(0); //Como eu sei que só tem um item, coloque logo o valor 0
                Element pipeElement = (Element) pipeItem; //Trainsforma o item em um elemento, pq
                                                          //é isso q ele é
                
                //Isso é para pegar os nos filho da Tag 'Pipes'
                NodeList pipesNodeChil = pipeElement.getChildNodes();
                Node[] pipeItemChil = new Node[pipesNodeChil.getLength()];
                pipesElements = new int[pipesNodeChil.getLength()];
                for (int i = 0; i < pipeItemChil.length; i ++) {
                    pipeItemChil[i] = pipesNodeChil.item(i);
                    Element pipeElementChil = (Element) pipeItemChil[i];
                    pipesElements[i] =Integer.parseInt(pipeElementChil.getTextContent()) + 10;
                    System.out.println(pipesElements[i]);
                }
                
                EscreverNoXML(pipesElements[0], pipesElements[1], pipesElements[2]);
                
            } catch (ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(WriteInXML.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private static void EscreverNoXML (int valor1, int valor2, int valor3) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            //Cria o documento XML
            Document documentXML = documentBuilder.newDocument();


            //Cria o nó chamado 'Setting'
            Element setting = documentXML.createElement("Setting");
            documentXML.appendChild(setting); //Coloca o 'setting' no arquivo XML

            //----  Cria a Tag 'Pipes'  ----
            Element pipes = documentXML.createElement("Pipes");
            Attr id = documentXML.createAttribute("id"); //Cria um atributo
            id.setValue("0");
            pipes.setAttributeNode(id); //Coloca o atributo criado na Tag 'pipes'
            //Coloca o 'setting' no arquivo XML, porque até agora tudo foi criado, mas nada
            //foi realmente colocado no arquivo
            setting.appendChild(pipes);

            //Criar os dados que vão ser salvos na Tag 'Pipes'
            Element distancePipesX = documentXML.createElement("DistancePipesX");
            Element distancePipesY = documentXML.createElement("DistancePipesY");
            Element speed = documentXML.createElement("Speed");

            //Coloca nas variáveis o valor a ser salvo na sub Tag criada
            distancePipesX.appendChild(documentXML.createTextNode(String.valueOf(valor1)));
            distancePipesY.appendChild(documentXML.createTextNode(String.valueOf(valor2)));
            speed.appendChild(documentXML.createTextNode(String.valueOf(valor3)));

            //Coloca realmente a sub Tag criada no arquivo XML 
            pipes.appendChild(distancePipesX);
            pipes.appendChild(distancePipesY);
            pipes.appendChild(speed);


            //As linhas abaixo é que vão criar o documento no disco em si
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource documentSource = new DOMSource(documentXML);

            StreamResult documentoFinal = new StreamResult(new File(FILE_ADDRESS));

            transformer.transform(documentSource, documentoFinal);

        } catch (ParserConfigurationException | TransformerException ex) {
            Logger.getLogger(WriteInXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


