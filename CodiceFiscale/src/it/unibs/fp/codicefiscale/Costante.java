package it.unibs.fp.codicefiscale;

public class Costante {

    // usati nel main
    public static final String PERSONE_FILE = "inputPersone.xml";
    public static final String CF_FILE = "codiciFiscali.xml";
    public static final String SCRITTURA_FILE = "codiciPersone.xml";
    public static final String MSG_LETTURA = "Lettura del file %s ...";
    public static final String MSG_GEN_CODICI = "Generazione dei Codici Fiscali ...";
    public static final String MSG_VERIFICA_CF = "Verifica validita' codici fiscali del file %s ...";
    public static final String MSG_SCRITTURA = "Scrittura del file %s ...";
    public static final String ANDARE_A_CAPO = "\n";

    // usati nella classe persona e codice fiscale
    public static final char X = 'X';
    public static final char[] CODICE_MESE = {'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T'};
    public static final String COMUNEFILE = "comuni.xml";
    public static final String FEMMINA = "F";
    public static final char[] ELENCO_PARI = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B',
            'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    public static final int[] ELENCO_DISPARI = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13,
            15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16,
            10, 22, 25, 24, 23
    };
    public static final char[] VOCALI = {'A', 'E', 'I', 'O', 'U'};
    public static final int[] GIORNI_MESE = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static final int LUNGHEZZA_ALFABETO = 26;
    public static final int LUNGHRZZA_COD_FIS = 16;
    public static final int DIFF_M_F = 40;

    public static final String ASSENTE = "ASSENTE";
    public static final String C0 = "0";

    // usati nella classe XML
    public static final String ERRORE_LETTURA = "Errore nell'inizializzazione del reader:";
    public static final String ERRORE_SCRITTURA = "Errore nella scrittura";

    public static final String OUTPUT = "output";
    public static final String PERSONE = "persone";
    public static final String NUMERO = "numero";
    public static final String PERSONA = "persona";
    public static final String ID = "ID";
    public static final String NOME = "nome";
    public static final String COGNOME = "cognome";
    public static final String SESSO = "sesso";
    public static final String COMUNE_NASCITA = "comune_nascita";
    public static final String DATA_NASCITA = "data_nascita";
    public static final String CODICE_FISCALE = "codice_fiscale";

    public static final String CODICI = "codici";
    public static final String CODICE = "codice";
    public static final String INVALIDI = "invalidi";
    public static final String SPAIATI = "spaiati";

    public static final String ENCODING = "utf-8";
    public static final String VERSION = "1.0";

    public static final String METODO_FORMATTAZIONE = "xml";
    public static final String INDENT_FORMATTAZIONE = "yes";
    public static final String HTTPS_FORMATTAZIONE = "{https://xml.apache.org/xslt}indent-amount";
    public static final String LIVELLO_INDENTAZIONE = "2";
    public static final String DICHIARAZIONE_FORMATTAZIONE = "yes";
}
