package it.unibs.fp.codicefiscale;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<Persona> persone = new ArrayList<>();
        ArrayList<codiceFiscale> codice = new ArrayList<>();

        Persona p = new Persona("yuhang", "ye", "M", "soave", "2001-07-23", new codiceFiscale(" "));

        p.genera_codice_fiscale();

        Xml.leggiPersone(persone);

        /*for(int i = 0; i < persone.size(); i++){
            System.out.println(i);

            persone.get(i).genera_codice_fiscale();
            System.out.println(persone.get(i).toString());
        }*/

        codiceFiscale c = new codiceFiscale("GCCSLD65T04D583R");
        System.out.println(c.validitàCodice());

        //Xml.leggiCodiceFiscale(codice);

        //persone.forEach(persona -> System.out.println(persone));


    }
}
