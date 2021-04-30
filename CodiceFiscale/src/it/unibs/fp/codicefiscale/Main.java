package it.unibs.fp.codicefiscale;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {

        ArrayList<Persona> persone = new ArrayList<>();
        ArrayList<codiceFiscale> codici_validi = new ArrayList<>();
        ArrayList<codiceFiscale> codici_invalidi = new ArrayList<>();
        ArrayList<codiceFiscale> codici_spaiati = new ArrayList<>();

        System.out.printf((Costante.MSG_LETTURA) + "%n", Costante.PERSONE_FILE);
        Xml.leggiPersone(Costante.PERSONE_FILE, persone); //1. leggere il file xml di persone e salvare i dati delle persone in un ArrayList

        System.out.println(Costante.MSG_GEN_CODICI);
        for (int i = 0; i < persone.size(); i++) { //2. generare i CF delle persone
            persone.get(i).generaCodiceFiscale();
        }

        System.out.printf((Costante.MSG_VERIFICA_CF) + "%n", Costante.CF_FILE);
        Xml.leggiCodiceFiscale(Costante.CF_FILE, codici_validi, codici_invalidi); //3.1 leggere il file xml di CF e salvare in un array di CF quelli corretti e in un altro quelli sbagliati
        Persona.controlloAssenti(persone, codici_validi); //3.2 verifica della presenza dei CF delle persone con quelli dell' ArrayList di CF

        Persona.confrontoCodice(codici_validi, persone, codici_spaiati); //Salvataggio dei CF spaiati

        System.out.printf((Costante.MSG_SCRITTURA) + "%n", Costante.SCRITTURA_FILE);
        Xml.scriviPersone(Costante.SCRITTURA_FILE, persone, codici_invalidi, codici_spaiati); //4. scrittura del file xml
        Xml.formatXMLFile(Costante.SCRITTURA_FILE); //formattazione file xml
    }
}