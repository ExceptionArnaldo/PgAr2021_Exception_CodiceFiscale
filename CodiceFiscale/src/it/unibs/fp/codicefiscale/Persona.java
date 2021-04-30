package it.unibs.fp.codicefiscale;

import java.util.ArrayList;

public class Persona {

    private String nome;
    private String cognome;
    private String sesso;
    private String comune_nascita;
    private String data_nascita;
    private codiceFiscale codice_fiscale;

    public Persona(String nome, String cognome, String sesso, String comune_nascita, String data_nascita, codiceFiscale codice_fiscale) {
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.comune_nascita = comune_nascita;
        this.data_nascita = data_nascita;
        this.codice_fiscale = codice_fiscale;
    }

    // metodo per generare il codice fiscale
    public void generaCodiceFiscale() {

        StringBuilder codice_fiscale_temp = new StringBuilder();

        codice_fiscale_temp.append(codiceCognome());
        codice_fiscale_temp.append(codiceNome());
        codice_fiscale_temp.append(codiceDataNascita());
        codice_fiscale_temp.append(codiceComune());

        codice_fiscale_temp.append(codice_fiscale.cifraControllo(codice_fiscale_temp.toString()));

        this.codice_fiscale = new codiceFiscale(codice_fiscale_temp.toString());
    }

    public String unioneConsonantiVocali(String da_unire) {
        String caratteri;
        String vocali = "";
        String consonanti = "";
        boolean is_vocale;

        for (int i = 0; i < da_unire.length(); i++) {
            is_vocale = false;
            for (int j = 0; j < Costante.VOCALI.length; j++) {
                if (da_unire.charAt(i) == Costante.VOCALI[j]) {
                    vocali += da_unire.charAt(i);
                    is_vocale = true;
                    break;
                }
            }
            if (!is_vocale) consonanti += da_unire.charAt(i);
        }

        caratteri = consonanti + vocali;

        return caratteri;
    }

    public boolean controlloConsonante(char lettera) {
        for (int i = 0; i < Costante.VOCALI.length; i++) {
            if (lettera == Costante.VOCALI[i]) return false;
        }
        return true;
    }

    //restituisce il codice del cognome
    public String codiceCognome() {
        String codice_fiscale_temp;
        String caratteri;

        String cognome_temp = cognome.toUpperCase();

        caratteri = unioneConsonantiVocali(cognome_temp); // mette insieme le vocali ai consonanti

        if (caratteri.length() >= 3) {  // se è più lungo di 3 allora estrae i primi 3 lettere
            codice_fiscale_temp = caratteri.substring(0, 3);
        } else {    // se la lunghezza è meno di tre aggiunge i X
            codice_fiscale_temp = caratteri;
            while (codice_fiscale_temp.length() != 3) {
                codice_fiscale_temp += Costante.X;
            }
        }

        return codice_fiscale_temp;
    }

    //restituisce il codice del nome
    public String codiceNome() {
        String codice_fiscale_temp;
        String caratteri;

        String nome_temp = nome.toUpperCase();

        caratteri = unioneConsonantiVocali(nome_temp);

        if (caratteri.length() >= 4 && controlloConsonante(caratteri.charAt(3))) { //se i consonanti sono più di 4 allora estrae il primo, terzo e quarto
            codice_fiscale_temp = caratteri.charAt(0) + caratteri.substring(2, 4);
        } else {
            if (caratteri.length() >= 3) {
                codice_fiscale_temp = caratteri.substring(0, 3);
            } else { // aggiunge i X
                codice_fiscale_temp = caratteri;
                while (codice_fiscale_temp.length() != 3) {
                    codice_fiscale_temp += Costante.X;
                }
            }
        }

        return codice_fiscale_temp;
    }

    //restituisce il codice della data di nascita
    public String codiceDataNascita() {

        String codice_fiscale_temp = "";

        int mese = Integer.parseInt(data_nascita.substring(5, 7));
        int giorno = Integer.parseInt(data_nascita.substring(8));

        //estrazione dell'anno
        codice_fiscale_temp += data_nascita.charAt(2);
        codice_fiscale_temp += data_nascita.charAt(3);

        //estrazione del mese
        codice_fiscale_temp += Costante.CODICE_MESE[mese - 1];

        //estrazione del giorno
        if (sesso.equals(Costante.FEMMINA)) {
            giorno = giorno + Costante.DIFF_M_F;
            codice_fiscale_temp += giorno;
        } else {
            if (giorno >= 10) {
                codice_fiscale_temp += giorno;
            } else {
                codice_fiscale_temp += Costante.C0 + giorno;
            }
        }

        return codice_fiscale_temp;
    }

    //restituisce il codice del comune di nascita
    public String codiceComune() {

        return Xml.leggiComune(Costante.COMUNEFILE, comune_nascita.toUpperCase());

    }

    //metodo che prende in input un array di persone(considerando solo i loro codici fiscali) e uno di codici. Scrive ASSENTE se non trova corrispondenza
    public static void controlloAssenti(ArrayList<Persona> persone, ArrayList<codiceFiscale> codici) {

        int j;

        for (int i = 0; i < persone.size(); i++) { //per ogni persona si controllano tutti i CF
            for (j = 0; j < codici.size(); j++) {
                if (persone.get(i).getCodice_fiscale().equals(codici.get(j).getCod_fis())) //se il CF della persona e' presente dell'array di CF si passa alla persona successiva
                    break;
            }
            if (j == codici.size()) //se sono stati passasti tutti i CF per una persona allora il suo CF non e' presente
                persone.get(i).codice_fiscale.setCod_fis(Costante.ASSENTE); //il CF della persona diventa ASSENTE
        }
    }

    //metodo che trova i CF spaiati
    public static void confrontoCodice(ArrayList<codiceFiscale> codici_validi, ArrayList<Persona> persone, ArrayList<codiceFiscale> codici_spaiati) {

        int j;

        for (int i = 0; i < codici_validi.size(); i++) { //per ogni CF si controllano tutte le persone
            for (j = 0; j < persone.size(); j++) {
                if (persone.get(j).getCodice_fiscale().equals(codici_validi.get(i).getCod_fis())) //se il CF e' presente dell'array di persone si passa al CF successivo
                    break;
            }
            if (j == persone.size()) //se sono state passaste tutte le persone per un CF allora il suo CF non e' presente
                codici_spaiati.add(codici_validi.get(i)); //il CF viene salvato in un arraylist di codici spaiati
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getComune_nascita() {
        return comune_nascita;
    }

    public void setComune_nascita(String comune_nascita) {
        this.comune_nascita = comune_nascita;
    }

    public String getData_nascita() {
        return data_nascita;
    }

    public void setData_nascita(String data_nascita) {
        this.data_nascita = data_nascita;
    }

    public String getCodice_fiscale() {
        return codice_fiscale.toString();
    }
}