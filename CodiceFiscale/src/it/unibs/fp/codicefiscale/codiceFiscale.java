package it.unibs.fp.codicefiscale;

public class codiceFiscale {

    private String cod_fis;

    public codiceFiscale(String cod_fis) {
        this.cod_fis = cod_fis;
    }

    public String getCod_fis() {
        return cod_fis;
    }

    public void setCod_fis(String cod_fis) {
        this.cod_fis = cod_fis;
    }

    public char cifraControllo(String cod_fis_temp) { // metodo per calcolare la cifra di controllo
        String char_pos_pari = "";      // caratteri nelle posizioni pari
        String char_pos_dispari = "";   // caratteri nelle posizioni dispari
        int somma = 0;

        for (int i = 0; i < cod_fis_temp.length(); i++) {   // for per dividere i caratteri pari e dispari(posizioni)
            if (i % 2 == 0)
                char_pos_dispari += cod_fis_temp.charAt(i);
            else
                char_pos_pari += cod_fis_temp.charAt(i);
        }

        for (int i = 0; i < char_pos_dispari.length(); i++) {    // caratteri dispari
            for (int j = 0; j < Costante.ELENCO_DISPARI.length; j++) {
                if (char_pos_dispari.charAt(i) == Costante.ELENCO_PARI[j]) somma = somma + Costante.ELENCO_DISPARI[j];
            }
        }

        for (int i = 0; i < char_pos_pari.length(); i++) {       // caratteri pari
            for (int j = 0; j < Costante.ELENCO_DISPARI.length; j++) {
                if (char_pos_pari.charAt(i) == Costante.ELENCO_PARI[j]) {
                    if (j < 10) somma = somma + j;
                    else somma = somma + j - 10;
                }
            }
        }

        return (Costante.ELENCO_PARI[(somma % Costante.LUNGHEZZA_ALFABETO) + 10]);
    }

    public boolean validitaCodice() { //controlla se il CF e' valido

        boolean validita = false;

        if (cod_fis.length() == Costante.LUNGHRZZA_COD_FIS) {

            for (int i = 0; i < cod_fis.length(); i++) { //controllo posizioni corrette lettere, numeri e casi vari
                if (i < 6) validita = controlloLettere(i);
                else if (i == 6 || i == 7) validita = controlloNumeri(i);
                else if (i == 8) validita = controlloLettere(i);
                else if (i == 9 || i == 10) validita = controlloNumeri(i);
                else if (i == 11) validita = controlloLettere(i);
                else if (i < 15) validita = controlloNumeri(i);
                else if (i == 15) validita = controlloLettere(i);

                if (!validita) return false;
            }
        }
        return validita;
    }

    // controlla se è un numero
    public boolean controlloNumeri(int pos) {
        if (cod_fis.charAt(pos) >= '0' && cod_fis.charAt(pos) <= '9') {
            if (pos != 10) return true; //se il nunmero corrisponde al giorno di nascita serve un ulteriore controllo
            else {
                return controlloGiorno(Integer.parseInt(cod_fis.substring(9, 11)));
            }
        } else return false;
    }

    // controlla se è una lettera
    public boolean controlloLettere(int pos) {
        if (cod_fis.charAt(pos) >= 'A' && cod_fis.charAt(pos) <= 'Z') {

            if (pos == 15) return controlloUltimaLettera(cod_fis.charAt(pos)); //verifica algoritmo ultima lettera

            if (pos == 2 || pos == 5)
                return controlloNomeCognome(cod_fis.substring(pos - 2, pos + 1)); // se è il nome o cognome va a verificare la validità

            if (pos == 8) return controlloMese(pos); //se la lettera e' il mese serve un ulteriore controllo
            else return true;
        } else return false;
    }

    // controlla il mese se è un carattere ammessibile
    public boolean controlloMese(int pos) {
        for (int i = 0; i < Costante.CODICE_MESE.length; i++) {
            if (cod_fis.charAt(pos) == Costante.CODICE_MESE[i]) return true;
        }

        return false;
    }

    // controlla il giorno
    public boolean controlloGiorno(int giorno) {
        for (int i = 0; i < Costante.CODICE_MESE.length; i++) {
            if (cod_fis.charAt(8) == Costante.CODICE_MESE[i]) {
                if ((giorno >= 1 && giorno <= Costante.GIORNI_MESE[i]) || (giorno > Costante.DIFF_M_F && giorno <= Costante.GIORNI_MESE[i] + Costante.DIFF_M_F)) {
                    return true;
                }
            }
        }

        return false;
    }

    // controlla l'ultima lettera del codice fiscale
    public boolean controlloUltimaLettera(char ultimaLettera) {
        if (ultimaLettera == cifraControllo(cod_fis.substring(0, 15))) return true;
        else return false;
    }

    // metodo per verificare la validità del nome/cognome cioè dopo una vocale non possono esserci consonanti se non la X
    public boolean controlloNomeCognome(String nome) {

        boolean vocale = false;

        for (int i = 0; i < 3; i++) {
            if (vocale && !controlloVocale(nome.charAt(i)) && nome.charAt(i) != Costante.X) return false;
            if (!vocale) vocale = controlloVocale(nome.charAt(i));
        }

        return true;
    }

    // metodo per verificare se la lettera è una vocale
    public boolean controlloVocale(char lettera) {
        for (int i = 0; i < Costante.VOCALI.length; i++) {
            if (lettera == Costante.VOCALI[i]) return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return cod_fis;
    }
}