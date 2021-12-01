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
			File xmlFile = new File("XMLFB8YPQ.xml"); // f�jl, amib�l olvasunk
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // XML dokumentumb�l DOM objektum -
																					// lehet�v� t�tele
			DocumentBuilder dBuilder = factory.newDocumentBuilder(); // XML f�jl, Document lek�r�s�hez
			Document doc = dBuilder.parse(xmlFile); // dokument lek�r�se
			doc.getDocumentElement().normalize();
			System.out.println("T�ncg�la adatok lek�r�se");
			Read(doc); // f� met�dus, megh�v�dik a Read
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}

	public static void Read(Document doc) {
		NodeList nList = doc.getElementsByTagName("Fellepes"); // Fellepes taggal rendelkez� elemek lek�r�se
																// list�ba
		for (int i = 0; i < nList.getLength(); i++) { // list�n v�gigmegy�nk
			Node nNode = nList.item(i); // lek�rj�k a lista aktu�lis elem�t, Element� konvert�ljuk
			Element element = (Element) nNode;
			// Lek�rj�k az attrib�tumokat, majd azok seg�ts�g�vel megh�vjuk a defini�lt
			// met�dusokat
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				String szinpad = element.getElementsByTagName("szinpad").item(0).getTextContent(); // darabsz�m
																									// lek�rdez�se
				String koreografiaId = element.getAttribute("koreoId");
				String csoportId = element.getAttribute("csoportId");
				System.out.println("\n-----------------------------------" + (i + 1)
						+ ". fell�p�s-----------------------------------");
				System.out.println("\n\tSz�npad:\t" + szinpad);
				ReadKoreografiaById(doc, koreografiaId);
				ReadCsoportById(doc, csoportId);
			}
		}
	}

	// fa strukt�ra miatt az attrib�tumban megadott id alapj�n k�rdezz�k le az egyes
	// rendel�shez tartoz� elemeket
	// A legt�bb objektum rendelkezik lesz�rmazottal, amelyet egy �jabb met�dus
	// k�rdez le, az attrub�tumban megadott ID alapj�n
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
					System.out.println("Csoport adatok: \n\tKoroszt�ly:\t" + korosztaly + "\n\tL�tsz�m:\t" + letszam
							+ "\n\tCsoportn�v:\t" + csoportnev);
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
					System.out.println("Tanciskola adatok: \n\tT�nciskola n�v:\t" + tname + "\n\tTelefonsz�m:\t"
							+ telszam + "\n\tVezet� adatai:\t \n\tN�v:\t" + nev + "\n\tTapasztalat:\t" + tapasztalat);
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
					System.out.println("Tancg�la adatok: \n\tHelysz�n:\t" + helyszin + "\n\tId�pont:\t" + idopont
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
							"Szervez� adatok: \n\tN�v:\t" + nev + "\n\tC�g:\t" + ceg + "\n\tTelefonsz�m:\t" + telszam);
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
					System.out.println("Koreogr�fia adatok: \n\tZene:\t" + zene + "\n\tSt�lus:\t" + stilus);
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
					System.out.println("T�nc�ra adatok: \n\tId�pont:\t" + idopont + "\n\tTerem:\t" + terem);
					ReadCsoportById(doc, csoportId);
				}
			}
		}
	}
}
