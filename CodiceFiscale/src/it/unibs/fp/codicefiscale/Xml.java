package it.unibs.fp.codicefiscale;

import javax.xml.stream.*;
import java.io.FileInputStream;
import java.util.ArrayList;

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

    /*public static void scriviPersone(String nome_file, ArrayList<Persona> persone, invalidi, spaiati){
        XMLOutputFactory xmlof = null;
        XMLStreamWriter xmlw = null;
        String[] check_persone = {}; // esempio di dati da scrivere
        try { // blocco try per raccogliere eccezioni
        xmlw.writeStartElement("output"); // scrittura del tag radice output
        xmlw.writeStartElement("persone");
        xmlw.writeStartElement("numero");
        for (int i = 0; i < check_persone.length; i++) {
            xmlw.writeStartElement("persona"); // scrittura del tag persona...
            xmlw.writeAttribute("ID", Integer.toString(i)); // ...con attributo id...
            xmlw.writeStartElement("nome");
            xmlw.writeCharacters(persone.getNome()); // ...e content dato
            xmlw.writeEndElement();
            xmlw.writeStartElement("cognome");
            xmlw.writeCharacters(persone.getCognome());
            xmlw.writeEndElement();
            xmlw.writeStartElement("sesso");
            xmlw.writeCharacters(persone.getSesso());
            xmlw.writeEndElement();
            xmlw.writeStartElement("comune_nascita");
            xmlw.writeCharacters(persone.getComune());
            xmlw.writeEndElement();
            xmlw.writeStartElement("data_nascita");
            xmlw.writeCharacters(persone.getData());
            xmlw.writeEndElement();
            xmlw.writeStartElement("codice_fiscale");
            xmlw.writeCharacters(persone.getCF());
            xmlw.writeEndElement();
            xmlw.writeEndElement(); // chiusura di </persona>
        }
        xmlw.writeStartElement("codici");
        xmlw.writeAttribute("invalidi", invalidi);
        xmlw.writeAttribute("spaiati", spaiati);
        xmlw.writeEndElement();

        xmlw.writeEndElement(); // chiusura di </output>
        xmlw.writeEndDocument(); // scrittura della fine del documento
        xmlw.flush(); // svuota il buffer e procede alla scrittura
        xmlw.close(); // chiusura del documento e delle risorse impiegate
    } catch (Exception e) { // se c’è un errore viene eseguita questa parte
            System.out.println("Errore nella scrittura");
    }
    }*/

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
    public static int leggiCodiceFiscale(String nome_file, ArrayList<codiceFiscale> codici) {

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
                                if (new codiceFiscale(cod_fis).validitaCodice()) //crea codice fiscale e verifica se e' corretto
                                    codici.add(new codiceFiscale(cod_fis)); //se corretto lo aggiunge all' ArrayList
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
        return codici_tot;
    }
}