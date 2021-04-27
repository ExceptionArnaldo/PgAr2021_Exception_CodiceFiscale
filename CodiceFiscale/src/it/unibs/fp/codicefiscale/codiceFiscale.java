package it.unibs.fp.codicefiscale;

public class codiceFiscale {

    private final char[] ELENCO_PARI = {'0','1','2','3','4','5','6','7','8','9','A','B',
            'C','D','E','F','G','H','I','J','K','L','M','N',
            'O','P','Q','R','S','T','U','V','W','X','Y','Z'
    };

    private final int[] ELENCO_DISPARI= {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13,
            15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16,
            10, 22, 25, 24, 23
    };

    private String cod_fis;

    public  codiceFiscale(String cod_fis){
        this.cod_fis = cod_fis;
    }

    public char cifraControllo(String cod_fis_temp){ // metodo per calcolare la cifra di controllo
        String char_pos_pari = "";      // caratteri nelle posizioni pari
        String char_pos_dispari = "";   // caratteri nelle posizioni dispari
        int somma=0;

        for(int i = 0 ; i < cod_fis_temp.length(); i++) {   // for per dividere i caratteri pari e dispari(posizioni)
            if(i % 2 == 0)
                char_pos_dispari += cod_fis_temp.charAt(i);
            else
                char_pos_pari += cod_fis_temp.charAt(i);
        }

        for(int i = 0; i < char_pos_dispari.length(); i++) {    // caratteri dispari
            for(int j = 0; j < ELENCO_DISPARI.length; j++) {
                if(char_pos_dispari.charAt(i) == ELENCO_PARI[j]) somma = somma + ELENCO_DISPARI[j];
            }
        }

        for(int i = 0; i < char_pos_pari.length(); i++) {       // ccaaratteri pari
            for(int j = 0; j < ELENCO_DISPARI.length; j++) {
                if(char_pos_pari.charAt(i) == ELENCO_PARI[j]) {
                    if(j < 10) somma = somma + j;
                    else somma = somma + j - 10;
                }
            }
        }

        return (ELENCO_PARI[(somma % 26)+ 10]);
    }

}
