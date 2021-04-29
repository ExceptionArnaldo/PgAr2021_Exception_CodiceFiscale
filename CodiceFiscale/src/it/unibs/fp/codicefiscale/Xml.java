package it.unibs.fp.codicefiscale;


import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class Xml {

    //legge un file xml e salva i dati delle persone in un ArrayList di tipo persona
    public static void leggiPersone(String nome_file, ArrayList<Persona> persone) {

        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;

        String nome = null;
        String cognome = null;
        String sesso = null;
        String comune_nascita = null;
        String data_nascita;

        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(nome_file, new FileInputStream(nome_file));
            while (xmlr.hasNext()) {
                if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) { //interessano solo i dati relativi alle persone
                    switch (xmlr.getLocalName()) {
                        case "nome":
                            xmlr.next();
                            nome = xmlr.getText();
                            break;
                        case "cognome":
                            xmlr.next();
                            cognome = xmlr.getText();
                            break;
                        case "sesso":
                            xmlr.next();
                            sesso = xmlr.getText();
                            break;
                        case "comune_nascita":
                            xmlr.next();
                            comune_nascita = xmlr.getText();
                            break;
                        case "data_nascita":
                            xmlr.next();
                            data_nascita = xmlr.getText();
                            persone.add(new Persona(nome, cognome, sesso, comune_nascita, data_nascita, new codiceFiscale(" "))); //ottenuti tutti i valori dell'xml di una persona. Creazione Persona
                            break;
                    }
                }
                xmlr.next();
            }
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del reader:");
            System.out.println(e.getMessage());
        }
    }

    public static void scriviPersone(String nome_file, ArrayList<Persona> persone, ArrayList<codiceFiscale> codici_invalidi, ArrayList<codiceFiscale> codici_spaiati) {

        XMLOutputFactory xmlof = null;
        XMLStreamWriter xmlw = null;

        try { // blocco try per raccogliere eccezioni
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(nome_file), "utf-8");
            xmlw.writeStartDocument("utf-8", "1.0");

            xmlw.writeStartElement("output"); // scrittura del tag radice output
            xmlw.writeStartElement("persone");
            xmlw.writeAttribute("numero", Integer.toString(persone.size()));
            //xmlw.writeEndElement();

            for (int i = 0; i < persone.size(); i++) {
                stampaPersone(xmlw, persone, i);
            }
            xmlw.writeEndElement(); // chiusura di </persone>

            xmlw.writeStartElement("codici"); // scrittura del tag <codici>

            /*xmlw.writeStartElement("invalidi"); // scrittura del tag <invalidi>
            xmlw.writeAttribute("numero", Integer.toString(invalidi)); // attributo numero invalidi

            for (int i = 0; i < tot_invalidi.size(); i++) {
                xmlw.writeStartElement("codice");
                xmlw.writeCharacters(tot_invalidi.get(i).getCodice_fiscale());
                xmlw.writeEndElement();
            }

            xmlw.writeEndElement(); // chiusura di </invalidi>

            xmlw.writeStartElement("spaiati"); // scrittura del tag <spaiati>
            xmlw.writeAttribute("numero", Integer.toString(spaiati)); // attributo numero spaiati
            //ciclo for
            xmlw.writeEndElement(); // chiusura di </spaiati>

             */
            stampaCodici(xmlw, "invalidi", codici_invalidi);
            stampaCodici(xmlw, "spaiati", codici_spaiati);

            xmlw.writeEndElement(); // chiusura di </codici>

            xmlw.writeEndElement(); // chiusura di </output>
            xmlw.writeEndDocument(); // scrittura della fine del documento

            /*Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            t.transform(new DOMSource(xmlw),new StreamResult(s));

             */
            /*TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            Source source = new DOMSource(xmlw);
            Result result = new StreamResult(new File("codiciPersone.xml"));
            t.transform(source, result);

             */

            //formatXMLFile("codiciPersone.xml");

            xmlw.flush(); // svuota il buffer e procede alla scrittura
            xmlw.close(); // chiusura del documento e delle risorse impiegate
        } catch (Exception e) { // se c’è un errore viene eseguita questa parte
            System.out.println("Errore nella scrittura");
            System.out.println(e.getMessage());
        }
    }

    private static void stampaCodici(XMLStreamWriter xmlw, String tag, ArrayList<codiceFiscale> codici) throws XMLStreamException {

        xmlw.writeStartElement(tag); // scrittura del tag <...>
        xmlw.writeAttribute("numero", Integer.toString(codici.size())); // attributo numero

        for (int i = 0; i < codici.size(); i++) {  // scrittura tutti CF
            xmlw.writeStartElement("codice");
            xmlw.writeCharacters(codici.get(i).toString());
            xmlw.writeEndElement();
        }

        xmlw.writeEndElement(); // chiusura di </...>
    }

    private static void stampaPersone(XMLStreamWriter xmlw, ArrayList<Persona> persone, int i) throws XMLStreamException {

        xmlw.writeStartElement("persona"); // scrittura del tag <persona>
        xmlw.writeAttribute("ID", Integer.toString(i)); // attributo id
        xmlw.writeStartElement("nome"); // scrittura del tag <nome>
        xmlw.writeCharacters(persone.get(i).getNome()); // nome
        xmlw.writeEndElement(); // chiusura di </nome>
        xmlw.writeStartElement("cognome"); // scrittura del tag <cognome>
        xmlw.writeCharacters(persone.get(i).getCognome()); // cognome
        xmlw.writeEndElement(); // chiusura di </cognome>
        xmlw.writeStartElement("sesso"); // scrittura del tag <sesso>
        xmlw.writeCharacters(persone.get(i).getSesso()); // sesso
        xmlw.writeEndElement(); // chiusura di </sesso>
        xmlw.writeStartElement("comune_nascita"); // scrittura del tag <comune_nascita>
        xmlw.writeCharacters(persone.get(i).getComune_nascita()); //comune_nascita
        xmlw.writeEndElement(); // chiusura di </comune_nascita>
        xmlw.writeStartElement("data_nascita"); // scrittura del tag <data_nascita>
        xmlw.writeCharacters(persone.get(i).getData_nascita()); // data_nascita
        xmlw.writeEndElement(); // chiusura di </data_nascita>
        xmlw.writeStartElement("codice_fiscale"); // scrittura del tag <codice_fiscale>
        xmlw.writeCharacters(persone.get(i).getCodice_fiscale()); // codice_fiscale
        xmlw.writeEndElement(); // chiusura di </codice_fiscale>
        xmlw.writeEndElement(); // chiusura di </persona>
    }

    //prende il comune di nascita della persona e restituisce il relativo codice se trovato nel file xml
    public static String leggiComune(String nome_file, String comune) {

        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;

        String codice = "";

        boolean trovato = false;

        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(nome_file, new FileInputStream(nome_file));

            while (xmlr.hasNext()) { // continua a leggere finché ha eventi a disposizione
                if (xmlr.getEventType() == XMLStreamConstants.CHARACTERS) { //interessa solo il nome dei comuni
                    if (xmlr.getText().trim().length() > 0) { // controlla se il testo non contiene solo spazi
                        if (trovato) {
                            codice = xmlr.getText();
                            return codice;
                        }
                        if (xmlr.getText().equals(comune)) trovato = true;
                    }
                }
                xmlr.next();
            }
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del reader:");
            System.out.println(e.getMessage());
        }
        return codice;
    }

    //legge xml e riempie un ArrayList di codici fiscali se questi risultano corretti. Restituisce il numero totale di codici controllati
    public static void leggiCodiceFiscale(String nome_file, ArrayList<codiceFiscale> codici_corretti, ArrayList<codiceFiscale> codici_sbagliati) {

        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;

        int codici_tot = 0;

        String cod_fis;

        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(nome_file, new FileInputStream(nome_file));

            while (xmlr.hasNext()) { // continua a leggere finché ha eventi a disposizione
                // switch sul tipo di evento
                switch (xmlr.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT: //controlla se il tag e' un codice, in caso affermativo incrementa il contatore
                        if (xmlr.getLocalName().equals("codice"))
                            codici_tot++;
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (xmlr.getEventType() == XMLStreamConstants.CHARACTERS) {
                            if (xmlr.getText().trim().length() > 0) { // controlla se il testo non contiene solo spazi
                                cod_fis = xmlr.getText();
                                if (new codiceFiscale(cod_fis).validitaCodice()) // crea codice fiscale e verifica se e' corretto
                                    codici_corretti.add(new codiceFiscale(cod_fis)); // se corretto lo aggiunge all'ArrayList CF corretti
                                else
                                    codici_sbagliati.add(new codiceFiscale(cod_fis)); // se sbagliato lo aggiunge all'ArrayList CF sbagliati
                            }
                        }
                        break;
                }
                xmlr.next();
            }
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del reader:");
            System.out.println(e.getMessage());
        }
    }

    public static void formatXMLFile(String file) throws Exception{

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new InputStreamReader(new FileInputStream(file))));

        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.setOutputProperty(OutputKeys.METHOD, "xml");
        xformer.setOutputProperty(OutputKeys.INDENT, "yes");
        xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        Source source = new DOMSource(document);
        Result result = new StreamResult(new File(file));
        xformer.transform(source, result);
    }
}