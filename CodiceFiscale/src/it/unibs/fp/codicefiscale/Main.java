package it.unibs.fp.codicefiscale;

import java.util.ArrayList;

public class Main {

    private final static String PERSONEFILE = "inputPersone.xml";
    private final static String CFFILE = "codiciFiscali.xml";
    private final static String SCRITTURAFILE = "codiciPersone.xml";
    private final static String MSG_LETTURA = "Lettura del file %s ...";
    private final static String MSG_GEN_CODICI = "Generazione dei Codici Fiscali ...";
    private final static String MSG_VERIFICA_CF = "Verifica validita' codici fiscali del file %s ...";
    private final static String MSG_SCRITTURA = "Scrittura del file %s ...";

    public static void main(String[] args) throws Exception {

        ArrayList<Persona> persone = new ArrayList<>();
        ArrayList<codiceFiscale> codici = new ArrayList<>();
        ArrayList<codiceFiscale> codici_invalidi = new ArrayList<>();
        ArrayList<codiceFiscale> codici_spaiati = new ArrayList<>();

        //Persona p = new Persona("yuhang", "ye", "M", "soave", "2001-07-23", new codiceFiscale(" "));

        //p.genera_codice_fiscale();

        System.out.printf((MSG_LETTURA) + "%n", PERSONEFILE);
        Xml.leggiPersone(PERSONEFILE, persone); //1. leggere il file xml di persone e salvare i dati delle persone in un ArrayList

        System.out.println(MSG_GEN_CODICI);
        for (int i = 0; i < persone.size(); i++) { //2. generare i CF delle persone
            persone.get(i).genera_codice_fiscale();
        }

        System.out.printf((MSG_VERIFICA_CF) + "%n", CFFILE);
        Xml.leggiCodiceFiscale(CFFILE, codici, codici_invalidi); //3.1 leggere il file xml di CF e salvare in un array di CF quelli corretti e in un altro quelli sbagliati

        Persona.confrontoCodici(persone, codici, codici_spaiati); //3.2 verifica della presenza dei CF delle persone con quelli dell' ArrayList di CF. Salvataggio dei CF spaiati

        System.out.printf((MSG_SCRITTURA) + "%n", SCRITTURAFILE);
        Xml.scriviPersone(SCRITTURAFILE, persone, codici_invalidi, codici_spaiati); //4. scrittura del file xml

        Xml.formatXMLFile(SCRITTURAFILE);

        //codiceFiscale c = new codiceFiscale("GCCSLD65T04D583R");
        //System.out.println(c.validitàCodice());

        //for (int i = 0; i < persone.size(); i++)
        //    System.out.println(i + persone.get(i).toString());

        System.out.println(persone.size());
        System.out.println(codici.size());
        System.out.println(codici_invalidi.size());
        System.out.println(codici_spaiati.size());
    }
}
