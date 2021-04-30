package it.unibs.fp.codicefiscale;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;

public class Xml {

    //legge un file xml e salva i dati delle persone in un ArrayList di tipo persona
    public static void leggiPersone(String nome_file, ArrayList<Persona> persone) {

        XMLInputFactory xmlif;
        XMLStreamReader xmlr;

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
                        case Costante.NOME:
                            xmlr.next();
                            nome = xmlr.getText();
                            break;
                        case Costante.COGNOME:
                            xmlr.next();
                            cognome = xmlr.getText();
                            break;
                        case Costante.SESSO:
                            xmlr.next();
                            sesso = xmlr.getText();
                            break;
                        case Costante.COMUNE_NASCITA:
                            xmlr.next();
                            comune_nascita = xmlr.getText();
                            break;
                        case Costante.DATA_NASCITA:
                            xmlr.next();
                            data_nascita = xmlr.getText();
                            persone.add(new Persona(nome, cognome, sesso, comune_nascita, data_nascita, new codiceFiscale(" "))); //ottenuti tutti i valori dell'xml di una persona. Creazione Persona
                            break;
                    }
                }
                xmlr.next();
            }
        } catch (Exception e) {
            System.out.println(Costante.ERRORE_LETTURA);
            System.out.println(e.getMessage());
        }
    }

    public static void scriviPersone(String nome_file, ArrayList<Persona> persone, ArrayList<codiceFiscale> codici_invalidi, ArrayList<codiceFiscale> codici_spaiati) {

        XMLOutputFactory xmlof;
        XMLStreamWriter xmlw;

        try { // blocco try per raccogliere eccezioni
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(nome_file), Costante.ENCODING);
            xmlw.writeStartDocument(Costante.ENCODING, Costante.VERSION);
            xmlw.writeStartElement(Costante.OUTPUT); // scrittura del tag radice output

            xmlw.writeStartElement(Costante.PERSONE);
            xmlw.writeAttribute(Costante.NUMERO, Integer.toString(persone.size()));

            for (int i = 0; i < persone.size(); i++) {
                xmlw.writeStartElement(Costante.PERSONA); // apertura del tag <persona>
                xmlw.writeAttribute(Costante.ID, Integer.toString(i)); // attributo id
                scriviTag(xmlw, Costante.NOME, persone.get(i).getNome()); // scrittura del tag <nome>
                scriviTag(xmlw, Costante.COGNOME, persone.get(i).getCognome()); // scrittura del tag <cognome>
                scriviTag(xmlw, Costante.SESSO, persone.get(i).getSesso()); // scrittura del tag <sesso>
                scriviTag(xmlw, Costante.COMUNE_NASCITA, persone.get(i).getComune_nascita()); // scrittura del tag <comune_nascita>
                scriviTag(xmlw, Costante.DATA_NASCITA, persone.get(i).getData_nascita()); // scrittura del tag <data_nascita>
                scriviTag(xmlw, Costante.CODICE_FISCALE, persone.get(i).getCodice_fiscale()); // scrittura del tag <codice_fiscale>
                xmlw.writeEndElement(); // chiusura di </persona>
            }
            xmlw.writeEndElement(); // chiusura di </persone>

            xmlw.writeStartElement(Costante.CODICI); // scrittura del tag <codici>
            stampaCodici(xmlw, Costante.INVALIDI, codici_invalidi);
            stampaCodici(xmlw, Costante.SPAIATI, codici_spaiati);
            xmlw.writeEndElement(); // chiusura di </codici>

            xmlw.writeEndElement(); // chiusura di </output>
            xmlw.writeEndDocument(); // scrittura della fine del documento

            xmlw.flush(); // svuota il buffer e procede alla scrittura
            xmlw.close(); // chiusura del documento e delle risorse impiegate
        } catch (Exception e) { // se c’è un errore viene eseguita questa parte
            System.out.println(Costante.ERRORE_SCRITTURA);
            System.out.println(e.getMessage());
        }
    }

    //stampa CF
    private static void stampaCodici(XMLStreamWriter xmlw, String tag, ArrayList<codiceFiscale> codici) throws XMLStreamException {

        xmlw.writeStartElement(tag); // scrittura del tag <...>
        xmlw.writeAttribute(Costante.NUMERO, Integer.toString(codici.size())); // attributo numero

        for (int i = 0; i < codici.size(); i++)   // scrittura tutti CF
            scriviTag(xmlw, Costante.CODICE, codici.get(i).toString());

        xmlw.writeEndElement(); // chiusura di </...>
    }

    //scrive un tag completo
    private static void scriviTag(XMLStreamWriter xmlw, String tag, String valore) throws XMLStreamException {
        xmlw.writeStartElement(tag);
        xmlw.writeCharacters(valore);
        xmlw.writeEndElement();
    }

    //prende il comune di nascita della persona e restituisce il relativo codice se trovato nel file xml
    public static String leggiComune(String nome_file, String comune) {

        XMLInputFactory xmlif;
        XMLStreamReader xmlr;

        String codice = "";

        boolean trovato = false;

        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(nome_file, new FileInputStream(nome_file));

            while (xmlr.hasNext()) { // continua a leggere finché ha eventi a disposizione
                if (xmlr.getEventType() == XMLStreamConstants.CHARACTERS) { // interessa solo il nome dei comuni
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
            System.out.println(Costante.ERRORE_LETTURA);
            System.out.println(e.getMessage());
        }
        return codice;
    }

    //legge xml e riempie un ArrayList di codici fiscali se questi risultano corretti
    public static void leggiCodiceFiscale(String nome_file, ArrayList<codiceFiscale> codici_corretti, ArrayList<codiceFiscale> codici_sbagliati) {

        XMLInputFactory xmlif;
        XMLStreamReader xmlr;

        String cod_fis;

        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(nome_file, new FileInputStream(nome_file));

            while (xmlr.hasNext()) { // continua a leggere finché ha eventi a disposizione
                if (xmlr.getEventType() == XMLStreamConstants.CHARACTERS) {
                    if (xmlr.getText().trim().length() > 0) { // controlla se il testo non contiene solo spazi
                        cod_fis = xmlr.getText();
                        if (new codiceFiscale(cod_fis).validitaCodice()) // crea codice fiscale e verifica se e' corretto
                            codici_corretti.add(new codiceFiscale(cod_fis)); // se corretto lo aggiunge all'ArrayList CF corretti
                        else
                            codici_sbagliati.add(new codiceFiscale(cod_fis)); // se sbagliato lo aggiunge all'ArrayList CF sbagliati
                    }
                }
                xmlr.next();
            }
        } catch (Exception e) {
            System.out.println(Costante.ERRORE_LETTURA);
            System.out.println(e.getMessage());
        }
    }

    public static void formatXMLFile(String file) throws Exception { //prende un xml non formattato e lo formatta

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new InputStreamReader(new FileInputStream(file))));

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, Costante.METODO_FORMATTAZIONE); //tipo di file generato
        transformer.setOutputProperty(OutputKeys.INDENT, Costante.INDENT_FORMATTAZIONE); //indentazione
        transformer.setOutputProperty(Costante.HTTPS_FORMATTAZIONE, Costante.LIVELLO_INDENTAZIONE); //formattazione
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, Costante.DICHIARAZIONE_FORMATTAZIONE);
        Source source = new DOMSource(document);
        Result result = new StreamResult(new File(file));
        transformer.transform(source, result);
    }
}