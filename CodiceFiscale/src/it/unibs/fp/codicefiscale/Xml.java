package it.unibs.fp.codicefiscale;

import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Xml {
    public static void leggiPersone() {
        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;

        String file = "/GitHub/PgAr2021_Exception_CodiceFiscale/inputPersone.xml";

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
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        System.out.println("END-Tag " + xmlr.getLocalName());
                        break;
                    case XMLStreamConstants.COMMENT:
                        System.out.println("// commento " + xmlr.getText());
                        break;
                    case XMLStreamConstants.CHARACTERS:
                         /*if(xmlr.getLocalName().equals("nome"))
                             //getNome
                        else if(xmlr.getLocalName().equals("cognome"))
                            //getCognome
                        else if(xmlr.getLocalName().equals("sesso"))
                            //getSesso
                        else if(xmlr.getLocalName().equals("comune_nascita"))
                            //getComune_nascita
                        else if(xmlr.getLocalName().equals("data_nascita"))
                            //getData_nascita
                        */
                        if (xmlr.getText().trim().length() > 0)
                            System.out.println("-> " + xmlr.getText());
                        break;
                }

                xmlr.next();
            }
        }
        catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del reader:");
            System.out.println(e.getMessage());
        }
    }

    public static void scriviPersone(){
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
    }
    }