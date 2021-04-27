package it.unibs.fp.codicefiscale;

import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Xml {
    public static void leggiPersone(ArrayList<Persona> persone) {
        XMLInputFactory xmlif;
        XMLStreamReader xmlr;

        String nome = null;
        String cognome = null;
        String sesso = null;
        String comune_nascita = null;
        String data_nascita;

        String file = "/PgAr2021_Exception_CodiceFiscale/inputPersone.xml";

        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(file, new FileInputStream(file));
            while (xmlr.hasNext()) {
                switch (xmlr.getEventType()) {
                    case XMLStreamConstants.START_DOCUMENT:
                        System.out.println("Start Read Doc " + file);
                        break;

                    case XMLStreamConstants.START_ELEMENT:
                        System.out.println("Tag " + xmlr.getLocalName());
                        for (int i = 0; i < xmlr.getAttributeCount(); i++)
                            System.out.printf(" => attributo %s->%s%n", xmlr.getAttributeLocalName(i), xmlr.getAttributeValue(i));

                        switch (xmlr.getLocalName()) {      //da sistemare
                            case "nome":
                                xmlr.next();
                                nome = xmlr.getText();
                                System.out.println("-> " + xmlr.getText());
                                break;
                            case "cognome":
                                xmlr.next();
                                cognome = xmlr.getText();
                                System.out.println("-> " + xmlr.getText());
                                break;
                            case "sesso":
                                xmlr.next();
                                sesso = xmlr.getText();
                                System.out.println("-> " + xmlr.getText());
                                break;
                            case "comune_nascita":
                                xmlr.next();
                                comune_nascita = xmlr.getText();
                                System.out.println("-> " + xmlr.getText());
                                break;
                            case "data_nascita":
                                xmlr.next();
                                data_nascita = xmlr.getText();
                                System.out.println("-> " + xmlr.getText());
                                persone.add(new Persona(nome, cognome, sesso, comune_nascita, data_nascita));
                                break;
                        }


                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        System.out.println("END-Tag " + xmlr.getLocalName());
                        break;
                    case XMLStreamConstants.COMMENT:
                        System.out.println("// commento " + xmlr.getText());
                        break;
                    /*case XMLStreamConstants.CHARACTERS:
                        if (xmlr.getText().trim().length() > 0)
                            System.out.println("-> " + xmlr.getText());
                        break;*/

                }

                xmlr.next();
            }
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del reader:");
            System.out.println(e.getMessage());
        }
        //test funzionamento salvataggio dati
        persone.forEach(persona -> System.out.println("\n" + persone));
    }

    /*public static void scriviPersone(){
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
}