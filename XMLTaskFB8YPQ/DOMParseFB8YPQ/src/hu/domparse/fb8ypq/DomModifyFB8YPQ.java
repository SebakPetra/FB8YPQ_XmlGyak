package hu.domparse.fb8ypq;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DomModifyFB8YPQ {

	public static void main(String[] args)
			throws ParserConfigurationException, IOException, SAXException, TransformerException {
		File xmlFile = new File("XMLFB8YPQ.xml"); // xml fájl bekérése
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // olvasás lehetõvé tétele
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();
		System.out.println("XML Módosítása");
		System.out.println("Adja meg mit szeretne módosítani: ");
		System.out.println("1 Táncgála módosítása\n2 Csoport módosítása\n3 Táncóra módosítása\n4 Szervezõ módosítása");
		Modify(doc);
	}

	public static void ModifyXML(Document doc) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("XMLFB8YPQ.xml"));
		transformer.transform(source, result);
	}

	// Modify-ban a felhasználót megkérdezzük, hogy a táncgála mely adatát kívánja
	// módosítani
	public static void Modify(Document doc) throws TransformerException {
		int tancgalakSzama = doc.getElementsByTagName("Tancgala").getLength(); // tancgalak számának lekérdezése
		int csoportokSzama = doc.getElementsByTagName("Csoport").getLength(); // csoportok számának lekérdezése
		int tancorakSzama = doc.getElementsByTagName("Tancora").getLength(); // tancorak számának lekérdezése
		int szervezokSzama = doc.getElementsByTagName("Szervezo").getLength(); // szervezok számának
																				// lekérdezése
		Scanner scan = new Scanner(System.in);
		System.out.println("Adja meg a sorszamot: ");
		int readCategory = scan.nextInt();
		switch (readCategory) {
		case 1:
			ModifyTancgala(doc, tancgalakSzama);
			break;
		case 2:
			ModifyCsoport(doc, csoportokSzama);
			break;
		case 3:
			ModifyTancora(doc, tancorakSzama);
			break;
		case 4:
			ModifySzervezo(doc, szervezokSzama);
			break;
		}
		scan.close();
	}

	private static void ModifyTancgala(Document doc, int tancgalaszam) throws TransformerException {
		// Kiiratjuk a jelenlegi táncgálákat, majd lekérdezzük melyiket kívánja
		// módosítani.
		System.out.println("Melyik táncgála adatait szeretné módosítani?");
		for (int i = 1; i < tancgalaszam + 1; i++) {
			System.out.println(i + ". táncgála");
			DomReadFB8YPQ.ReadTancgalaById(doc, String.valueOf(i));
			System.out.println("-------------------------------------------");
		}
		String id = ReadId();
		// Bekérjük az új adatokat
		Scanner sc = new Scanner(System.in);
		System.out.print("Helyszín: ");
		String helyszin = sc.nextLine();
		System.out.print("Idõpont: ");
		String idopont = sc.nextLine();
		System.out.print("Idõtartam: ");
		String idotartam = sc.nextLine();
		sc.close();
		// lekérdezzük az Elementeket, majd setTextContent-el módosítjuk.
		NodeList nodeList = doc.getElementsByTagName("Tancgala");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nNode = nodeList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) nNode;
				String sid = element.getAttribute("id");
				if (sid.equals(id)) {
					Node node1 = element.getElementsByTagName("Helyszin").item(0);
					node1.setTextContent(helyszin);
					Node node2 = element.getElementsByTagName("Idopont").item(0);
					node2.setTextContent(idopont);
					Node node3 = element.getElementsByTagName("Idotartam").item(0);
					node3.setTextContent(idotartam);
					System.out.println("Sikeres módosítás");
				}
			}
		}
		ModifyXML(doc); // Létrehozzuk az XML-t
	}

	private static void ModifyCsoport(Document doc, int csoportszam) throws TransformerException {
		System.out.println("Melyik csoportot kívánja módosítani?");
		for (int i = 1; i < csoportszam + 1; i++) {
			System.out.println(i + ". csoport");
			DomReadFB8YPQ.ReadCsoportById(doc, String.valueOf(i));
			System.out.println("-------------------------------------------");
		}
		String id = ReadId();
		// Bekérjük az új adatokat
		Scanner sc = new Scanner(System.in);
		System.out.print("Korosztály: ");
		String korosztaly = sc.nextLine();
		System.out.print("Létszám: ");
		String letszam = sc.nextLine();
		System.out.print("Csoportnév: ");
		String csoportnev = sc.nextLine();
		sc.close();
		// lekérdezzük az Elementeket, majd setTextContent-el módosítjuk.
		NodeList nodeList = doc.getElementsByTagName("Csoport");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nNode = nodeList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) nNode;
				String sid = element.getAttribute("id");
				if (sid.equals(id)) {
					Node node1 = element.getElementsByTagName("Korosztaly").item(0);
					node1.setTextContent(korosztaly);
					Node node2 = element.getElementsByTagName("Letszam").item(0);
					node2.setTextContent(letszam);
					Node node3 = element.getElementsByTagName("Csoportnev").item(0);
					node3.setTextContent(csoportnev);
					System.out.println("Sikeres módosítás");
				}
			}
		}
		ModifyXML(doc); // Létrehozzuk az XML-t
	}

	private static void ModifyTancora(Document doc, int tancoraszam) throws TransformerException {
		System.out.println("Melyik táncórát szeretné módosítani?");
		for (int i = 1; i < tancoraszam + 1; i++) {
			System.out.println(i + ". táncóra");
			DomReadFB8YPQ.ReadTancoraById(doc, String.valueOf(i));
			System.out.println("-------------------------------------------");
		}
		String id = ReadId();
		Scanner sc = new Scanner(System.in);
		System.out.print("Idõpont");
		String idopont = sc.nextLine();
		System.out.print("Terem: ");
		String terem = sc.nextLine();
		sc.close();
		NodeList nodeList = doc.getElementsByTagName("Tancora");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nNode = nodeList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) nNode;
				String sid = element.getAttribute("id");
				if (sid.equals(id)) {
					Node node1 = element.getElementsByTagName("Idopont").item(0);
					node1.setTextContent(idopont);
					Node node2 = element.getElementsByTagName("Terem").item(0);
					node2.setTextContent(terem);
					System.out.println("Sikeres módosítás");
				}
			}
		}
		ModifyXML(doc);
	}

	public static String ReadId() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\nid:");
		String id = sc.nextLine();
		sc.close();
		return id;
	}

	private static void ModifySzervezo(Document doc, int szervezoszam) throws TransformerException {
		System.out.println("Melyik szervezõ adatait szeretné módosítani?");
		for (int i = 1; i < szervezoszam + 1; i++) {
			System.out.println(i + ". specifikacio");
			DomReadFB8YPQ.ReadSzervezoById(doc, String.valueOf(i));
			System.out.println("-------------------------------------------");
		}
		String id = ReadId();
		Scanner sc = new Scanner(System.in);
		System.out.print("Név: ");
		String nev = sc.nextLine();
		System.out.print("Cég: ");
		String ceg = sc.nextLine();
		System.out.print("Telefonszám: ");
		String telszam = sc.nextLine();
		sc.close();
		NodeList nodeList = doc.getElementsByTagName("Szervezo");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nNode = nodeList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) nNode;
				String sid = element.getAttribute("id");
				if (sid.equals(id)) {
					Node node1 = element.getElementsByTagName("Nev").item(0);
					node1.setTextContent(nev);
					Node node2 = element.getElementsByTagName("Ceg").item(0);
					node2.setTextContent(ceg);
					Node node3 = element.getElementsByTagName("Telefonszam").item(0);
					node3.setTextContent(telszam);
					System.out.println("Sikeres módosítás");
				}
			}
		}
		ModifyXML(doc);
	}
}
