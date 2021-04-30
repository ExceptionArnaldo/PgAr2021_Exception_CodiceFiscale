package it.unibs.fp.codicefiscale;

public class Costante {

    // usati nel main
    final static String PERSONE_FILE = "inputPersone.xml";
    final static String CF_FILE = "codiciFiscali.xml";
    final static String SCRITTURA_FILE = "codiciPersone.xml";
    final static String MSG_LETTURA = "Lettura del file %s ...";
    final static String MSG_GEN_CODICI = "Generazione dei Codici Fiscali ...";
    final static String MSG_VERIFICA_CF = "Verifica validita' codici fiscali del file %s ...";
    final static String MSG_SCRITTURA = "Scrittura del file %s ...";

    // usati nella classe persona e codice fiscale
    final static char X = 'X';
    final static char[] CODICE_MESE = {'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T'};
    final static String COMUNEFILE = "comuni.xml";
    final static String FEMMINA = "F";
    final static char[] ELENCO_PARI = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B',
            'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    final static int[] ELENCO_DISPARI = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13,
            15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16,
            10, 22, 25, 24, 23
    };
    final static char[] VOCALI = {'A', 'E', 'I', 'O', 'U'};
    final static int[] GIORNI_MESE = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    final static int LUNGHEZZA_ALFABETO = 26;
    final static int LUNGHRZZA_COD_FIS = 16;
    final static int DIFF_M_F = 40;

    final static String ASSENTE = "Assente";

    // usati nella classe XML
    final static String ERRORE_LETTURA = "Errore nell'inizializzazione del reader:";
    final static String ERRORE_SCRITTURA = "Errore nella scrittura";
}
