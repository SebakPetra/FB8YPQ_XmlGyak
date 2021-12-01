package hu.domparse.fb8ypq;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DomReadFB8YPQ {

	public static void main(String[] args) {
		try {
			File xmlFile = new File("XMLFB8YPQ.xml"); // fájl, amibõl olvasunk
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // XML dokumentumból DOM objektum -
																					// lehetõvé tétele
			DocumentBuilder dBuilder = factory.newDocumentBuilder(); // XML fájl, Document lekéréséhez
			Document doc = dBuilder.parse(xmlFile); // dokument lekérése
			doc.getDocumentElement().normalize();
			System.out.println("Táncgála adatok lekérése");
			Read(doc); // fõ metódus, meghívódik a Read
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}

	public static void Read(Document doc) {
		NodeList nList = doc.getElementsByTagName("Fellepes"); // Fellepes taggal rendelkezõ elemek lekérése
																// listába
		for (int i = 0; i < nList.getLength(); i++) { // listán végigmegyünk
			Node nNode = nList.item(i); // lekérjük a lista aktuális elemét, Elementé konvertáljuk
			Element element = (Element) nNode;
			// Lekérjük az attribútumokat, majd azok segítségével meghívjuk a definiált
			// metódusokat
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				String szinpad = element.getElementsByTagName("szinpad").item(0).getTextContent(); // darabszám
																									// lekérdezése
				String koreografiaId = element.getAttribute("koreoId");
				String csoportId = element.getAttribute("csoportId");
				System.out.println("\n-----------------------------------" + (i + 1)
						+ ". fellépés-----------------------------------");
				System.out.println("\n\tSzínpad:\t" + szinpad);
				ReadKoreografiaById(doc, koreografiaId);
				ReadCsoportById(doc, csoportId);
			}
		}
	}

	// fa struktúra miatt az attribútumban megadott id alapján kérdezzük le az egyes
	// rendeléshez tartozó elemeket
	// A legtöbb objektum rendelkezik leszármazottal, amelyet egy újabb metódus
	// kérdez le, az attrubútumban megadott ID alapján
	public static void ReadCsoportById(Document doc, String id) {
		NodeList nList = doc.getElementsByTagName("Csoport");
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			Element element = (Element) nNode;
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				if (element.getAttribute("id").equals(id)) {
					String korosztaly = element.getElementsByTagName("Korosztaly").item(0).getTextContent();
					String letszam = element.getElementsByTagName("Letszam").item(0).getTextContent();
					String csoportnev = element.getElementsByTagName("Csoportnev").item(0).getTextContent();
					System.out.println("Csoport adatok: \n\tKorosztály:\t" + korosztaly + "\n\tLétszám:\t" + letszam
							+ "\n\tCsoportnév:\t" + csoportnev);
					String tanciskolaId = element.getAttribute("tanciskolaId");
					ReadTanciskolaById(doc, tanciskolaId);
				}
			}
		}
	}
	
	public static void ReadTanciskolaById(Document doc, String id) {
		NodeList nList = doc.getElementsByTagName("Tanciskola");
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			Element element = (Element) nNode;
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				if (element.getAttribute("id").equals(id)) {
					String tname = element.getElementsByTagName("TanciskolaNev").item(0).getTextContent();
					String telszam = element.getElementsByTagName("Telefonszam").item(0).getTextContent();
					String nev = element.getElementsByTagName("Nev").item(0).getTextContent();
					String tapasztalat = element.getElementsByTagName("Tapasztalat").item(0).getTextContent();
					System.out.println("Tanciskola adatok: \n\tTánciskola név:\t" + tname + "\n\tTelefonszám:\t"
							+ telszam + "\n\tVezetõ adatai:\t \n\tNév:\t" + nev + "\n\tTapasztalat:\t" + tapasztalat);
					String tancgalaId = element.getAttribute("tancgalaId");
					ReadTancgalaById(doc, tancgalaId);
				}
			}
		}
	}
	
	public static void ReadTancgalaById(Document doc, String id) {
		NodeList nList = doc.getElementsByTagName("Tancgala");
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			Element element = (Element) nNode;
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				if (element.getAttribute("id").equals(id)) {
					String helyszin = element.getElementsByTagName("Helyszin").item(0).getTextContent();
					String idopont = element.getElementsByTagName("Idopont").item(0).getTextContent();
					String idotartam = element.getElementsByTagName("Idotartam").item(0).getTextContent();
					System.out.println("Tancgála adatok: \n\tHelyszín:\t" + helyszin + "\n\tIdõpont:\t" + idopont
							+ "\n\tIdotartam:\t" + idotartam);
					String szervezoId = element.getAttribute("szervezoId");
					ReadSzervezoById(doc, szervezoId);
				}
			}
		}
	}
	
	public static void ReadSzervezoById(Document doc, String id) {
		NodeList nList = doc.getElementsByTagName("Szervezo");
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			Element element = (Element) nNode;
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				if (element.getAttribute("id").equals(id)) {
					String nev = element.getElementsByTagName("Nev").item(0).getTextContent();
					String ceg = element.getElementsByTagName("Ceg").item(0).getTextContent();
					String telszam = element.getElementsByTagName("Telefonszam").item(0).getTextContent();
					System.out.println(
							"Szervezõ adatok: \n\tNév:\t" + nev + "\n\tCég:\t" + ceg + "\n\tTelefonszám:\t" + telszam);
				}
			}
		}
	}


	public static void ReadKoreografiaById(Document doc, String id) {
		NodeList nList = doc.getElementsByTagName("Koreografia");
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			Element element = (Element) nNode;
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				if (element.getAttribute("id").equals(id)) {
					String zene = element.getElementsByTagName("Zene").item(0).getTextContent();
					String stilus = element.getElementsByTagName("Stilus").item(0).getTextContent();
					System.out.println("Koreográfia adatok: \n\tZene:\t" + zene + "\n\tStílus:\t" + stilus);
				}
			}
		}
	}

	public static void ReadTancoraById(Document doc, String id) {
		NodeList nList = doc.getElementsByTagName("Tancora");
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			Element element = (Element) nNode;
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				if (element.getAttribute("id").equals(id)) {
					String idopont = element.getElementsByTagName("Idopont").item(0).getTextContent();
					String terem = element.getElementsByTagName("Terem").item(0).getTextContent();
					String csoportId = element.getAttribute("csId");
					System.out.println("Táncóra adatok: \n\tIdõpont:\t" + idopont + "\n\tTerem:\t" + terem);
					ReadCsoportById(doc, csoportId);
				}
			}
		}
	}
}
