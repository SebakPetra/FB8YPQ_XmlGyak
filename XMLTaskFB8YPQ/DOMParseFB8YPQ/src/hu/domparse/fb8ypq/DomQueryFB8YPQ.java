package hu.domparse.fb8ypq;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomQueryFB8YPQ {

	public static void main(String[] args)
			throws ParserConfigurationException, IOException, SAXException, TransformerException {
		// TODO Auto-generated method stub
		File xmlFile = new File("XMLFB8YPQ.xml"); // xml fájl bekérése
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // olvasás lehetõvé tétele
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();

		System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
		System.out.println("------------------------------");
		System.out.println("Az Elte tánciskola adatai: ");
		LoadTanciskolaQuery(doc);

	}

	public static void LoadTanciskolaQuery(Document doc) throws TransformerException {
		NodeList nodeList = doc.getElementsByTagName("Tanciskola"); // Tanciskola elemek listázása
		String tanciskola;
		Element element = null;
		Node nNode = null;
		for (int i = 0; i < nodeList.getLength(); i++) {
			nNode = nodeList.item(i);
			element = (Element) nNode;
			String nev = element.getElementsByTagName("TanciskolaNev").item(0).getTextContent();
			System.out.println((i + 1) + ") " + nev);

		}
		// Tánciskola választása
		System.out.println("Írja be annak a tánciskolának a nevét, amelyikbe járó csoportok adatait szeretné látni:");
		Scanner sc = new Scanner(System.in);
		tanciskola = sc.nextLine();
		for (int i = 0; i < nodeList.getLength(); i++) {
			nNode = nodeList.item(i);
			element = (Element) nNode;
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				if (tanciskola.equals("Elte")) {
					LoadCsoportQuery(doc, "1");
					break;
				}

				if (tanciskola.equals("Dance Elit")) {
					LoadCsoportQuery(doc, "2");
					break;
				}
			}
		}

	}

	// Kiválasztott tánciskola csoportjai adatainak kiíratása
	public static void LoadCsoportQuery(Document doc, String id) throws TransformerException {
		NodeList nodeList = doc.getElementsByTagName("Csoport");
		int csoport = 0;

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nNode = nodeList.item(i);
			Element element = (Element) nNode;
			String tId = element.getAttribute("tanciskolaId");
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				if (id.equals(tId)) {
					csoport += 1;
					System.out.println(csoport + ". csoport adatai:");
					String csId = element.getAttribute("id");
					DomReadFB8YPQ.ReadCsoportById(doc, csId);

				}
			}
		}

	}

}
