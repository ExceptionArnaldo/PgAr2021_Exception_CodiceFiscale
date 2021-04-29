package it.unibs.fp.codicefiscale;

public class codiceFiscale {

    private final static char[] ELENCO_PARI = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B',
            'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private final static int[] ELENCO_DISPARI = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13,
            15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16,
            10, 22, 25, 24, 23
    };

    private final static char[] VOCALI = {'A', 'E', 'I', 'O', 'U'};

    private final static char[] CODICE_MESE = {'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T'};

    private final static char X = 'X';

    private final static int[] GIORNI_MESE = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

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
            for (int j = 0; j < ELENCO_DISPARI.length; j++) {
                if (char_pos_dispari.charAt(i) == ELENCO_PARI[j]) somma = somma + ELENCO_DISPARI[j];
            }
        }

        for (int i = 0; i < char_pos_pari.length(); i++) {       // caratteri pari
            for (int j = 0; j < ELENCO_DISPARI.length; j++) {
                if (char_pos_pari.charAt(i) == ELENCO_PARI[j]) {
                    if (j < 10) somma = somma + j;
                    else somma = somma + j - 10;
                }
            }
        }

        return (ELENCO_PARI[(somma % 26) + 10]);
    }


    public boolean validitaCodice() { //controlla se il CF e' valido

        boolean validita = true;

        for (int i = 0; i < cod_fis.length(); i++) { //controllo posizioni corrette lettere e numeri
            if (i < 6) validita = controlloLettere(i);
            else if (i == 6 || i == 7) validita = controlloNumeri(i);
            else if (i == 8) validita = controlloLettere(i);
            else if (i == 9 || i == 10) validita = controlloNumeri(i);
            else if (i == 11) validita = controlloLettere(i);
            else if (i < 15) validita = controlloNumeri(i);
            else if (i == 15) validita = controlloLettere(i);

            if (!validita) return false;
        }
        return validita;
    }

    public boolean controlloNumeri(int pos) {
        if (cod_fis.charAt(pos) >= '0' && cod_fis.charAt(pos) <= '9') {
            if (pos != 10) return true; //se il nunmero corrisponde al giorno di nascita serve un ulteriore controllo
            else {
                return controlloGiorno(Integer.parseInt(cod_fis.substring(9, 11)));
            }
        } else return false;
    }

    public boolean controlloLettere(int pos) {
        if (cod_fis.charAt(pos) >= 'A' && cod_fis.charAt(pos) <= 'Z') {

            if (pos == 15) return controlloUltimaLettera(cod_fis.charAt(pos)); //verifica algoritmo ultima lettera

            if(pos == 2 || pos == 5)  return controlloNomeCognome(cod_fis.substring(pos - 2, pos + 1)); // se è il nome o cognome va a verificare la validità

            if (pos == 8) return controlloMese(pos); //se la lettera e' il mese serve un ulteriore controllo
            else return true;
        } else return false;
    }

    public boolean controlloMese(int pos) {
        for (int i = 0; i < CODICE_MESE.length; i++) {
            if (cod_fis.charAt(pos) == CODICE_MESE[i]) return true;
        }

        return false;
    }

    public boolean controlloGiorno(int giorno) {
        for (int i = 0; i < CODICE_MESE.length; i++) {
            if (cod_fis.charAt(8) == CODICE_MESE[i]) {
                if ((giorno >= 1 && giorno <= GIORNI_MESE[i]) || (giorno >= 41 && giorno <= GIORNI_MESE[i] + 40)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean controlloUltimaLettera(char ultimaLettera) {
        if (ultimaLettera == cifraControllo(cod_fis.substring(0, 15))) return true;
        else return false;
    }

    public boolean controlloNomeCognome(String nome){ // metodo per verificare la validità del nome/cognome cioè dopo una vocale non possono esserci consonanti se non la X

        boolean vocale = false;

        for(int i = 0; i < 3; i++){
            if(vocale == true && controlloVocale(nome.charAt(i)) == false && nome.charAt(i) != X) return false;
            if(vocale == false) vocale = controlloVocale(nome.charAt(i));
        }

        return true;
    }

    public boolean controlloVocale(char lettera){ // metodo per verificare se la lettera è una vocale
        for(int i = 0; i < VOCALI.length; i++){
            if(lettera == VOCALI[i]) return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return cod_fis;
    }
}
