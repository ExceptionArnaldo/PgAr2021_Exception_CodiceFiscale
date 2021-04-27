package it.unibs.fp.codicefiscale;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<Persona> persone = new ArrayList<>();

        Persona p = new Persona("Yuhang", "Ye", 'M', "soave", "2001-07-23", new codiceFiscale(" "));

        p.genera_codice_fiscale();

        //Xml.leggiPersone();
    }
}
