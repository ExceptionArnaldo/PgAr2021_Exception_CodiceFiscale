package it.unibs.fp.codicefiscale;

import java.util.ArrayList;

public class Persona {

    private final static char X = 'X';

    private final static char[] CODICE_MESE = {'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T'};

    private final static String COMUNEFILE = "comuni.xml";

    private final static String FEMMINA = "F";

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

        StringBuffer codice_fiscale_temp = new StringBuffer();

        codice_fiscale_temp.append(codiceCognome());
        codice_fiscale_temp.append(codiceNome());
        codice_fiscale_temp.append(codiceDataNascita());
        codice_fiscale_temp.append(codiceComune());

        codice_fiscale_temp.append(codice_fiscale.cifraControllo(codice_fiscale_temp.toString()));

        this.codice_fiscale = new codiceFiscale(codice_fiscale_temp.toString());
    }

    //restituisce il codice del cognome
    public String codiceCognome() {
        String codice_fiscale_temp;
        String vocali = "";
        String consonanti = "";
        String caratteri;

        String cognome_temp = cognome.toUpperCase();

        for (int i = 0; i < cognome_temp.length(); i++) {   // divide le consonanti e vocali
            if (cognome_temp.charAt(i) == 'A' || cognome_temp.charAt(i) == 'E' || cognome_temp.charAt(i) == 'I' || cognome_temp.charAt(i) == 'O' || cognome_temp.charAt(i) == 'U') {
                vocali += cognome_temp.charAt(i);
            } else {
                consonanti += cognome_temp.charAt(i);
            }
        }

        caratteri = consonanti + vocali; // mette insieme le vocali ai consonanti

        if (caratteri.length() >= 3) {  // se è più lungo di 3 allora estrae i primi 3 lettere
            codice_fiscale_temp = caratteri.substring(0, 3);
        } else {    // se la lunghezza è meno di tre aggiunge i X
            codice_fiscale_temp = caratteri;
            while (codice_fiscale_temp.length() != 3) {
                codice_fiscale_temp += X;
            }
        }

        return codice_fiscale_temp;
    }

    //restituisce il codice del nome
    public String codiceNome() {
        String codice_fiscale_temp;
        String vocali = "";
        String consonanti = "";
        String caratteri;

        String nome_temp = nome.toUpperCase();

        for (int i = 0; i < nome_temp.length(); i++) {  // divide le consonanti e vocali
            if (nome_temp.charAt(i) == 'A' || nome_temp.charAt(i) == 'E' || nome_temp.charAt(i) == 'I' || nome_temp.charAt(i) == 'O' || nome_temp.charAt(i) == 'U') {
                vocali += nome_temp.charAt(i);
            } else {
                consonanti += nome_temp.charAt(i);
            }
        }

        if (consonanti.length() >= 4) { //se i consonanti sono più di 4 allora estrae il primo, terzo e quarto
            codice_fiscale_temp = consonanti.charAt(0) + consonanti.substring(2, 4);
        } else {
            caratteri = consonanti + vocali;
            if (caratteri.length() >= 3) {
                codice_fiscale_temp = caratteri.substring(0, 3);
            } else { // aggiunge i X
                codice_fiscale_temp = caratteri;
                while (codice_fiscale_temp.length() != 3) {
                    codice_fiscale_temp += X;
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
        codice_fiscale_temp += CODICE_MESE[mese - 1];

        //estrazione del giorno
        if (sesso.equals(FEMMINA)) {
            giorno = giorno + 40;
        } else {
            if (giorno > 10) {
                codice_fiscale_temp += giorno;
            } else {
                codice_fiscale_temp += "0" + giorno;
            }
        }

        return codice_fiscale_temp;
    }

    //restituisce il codice del comune di nascita
    public String codiceComune() {

        return Xml.leggiComune(COMUNEFILE, comune_nascita.toUpperCase());

    }

    //metodo che prende in input un array di persone(considerando solo i loro codici fiscali) e uno di codici. Restituisce quanti CF del secondo array non sono presenti nelle persone
    public static void confrontoCodici(ArrayList<Persona> persone, ArrayList<codiceFiscale> codici, ArrayList<codiceFiscale> codici_spaiati) {
        int j;
        int conta_assenti = 0;

        for (int i = 0; i < persone.size(); i++) { //per ogni persona si controllano tutti i CF
            for (j = 0; j < codici.size(); j++) {
                if (persone.get(i).codice_fiscale.getCod_fis().equals(codici.get(j).getCod_fis())) //se il CF della persona e' presente dell'array di CF si passa alla persona successiva
                    break;
            }
            if (j == codici.size()) { //se sono stati passasti tutti i CF per una persona allora il suo CF non e' presente
                codici_spaiati.add(new codiceFiscale(persone.get(i).codice_fiscale.getCod_fis())); // salvataggio CF spaiato
                persone.get(i).codice_fiscale.setCod_fis("ASSENTE"); //il CF della persona diventa ASSENTE
                conta_assenti++; //conta quanti CF spaiati ci sono
            }
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

    public void setCodice_fiscale(codiceFiscale codice_fiscale) {
        this.codice_fiscale = codice_fiscale;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", sesso='" + sesso + '\'' +
                ", comune_nascita='" + comune_nascita + '\'' +
                ", data_nascita='" + data_nascita + '\'' +
                ", codice_fiscale='" + codice_fiscale.getCod_fis() + '\'' +
                '}';
    }
}