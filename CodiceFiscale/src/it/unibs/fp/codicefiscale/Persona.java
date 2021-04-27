package it.unibs.fp.codicefiscale;

public class Persona {

    private final static char X = 'X';

    private final static String comuneFile = "comuni.xml";

    private final static String FEMMINA = "F";
    private final static String MASCHIO = "M";

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

    public void genera_codice_fiscale(){

        StringBuffer codice_fiscale_temp = new StringBuffer("");

        codice_fiscale_temp.append(codice_cognome());
        codice_fiscale_temp.append(codice_nome());
        codice_fiscale_temp.append(codice_data_nascita());
        codice_fiscale_temp.append(codice_comune());

        codice_fiscale_temp.append(codice_fiscale.cifraControllo(codice_fiscale_temp.toString()));

        System.out.println(codice_fiscale_temp);
    }

    public String codice_cognome(){
        String codice_fiscale_temp = "";
        String vocali = "";
        String consonanti = "";

        String cognome_temp = cognome.toUpperCase();

        for(int i = 0; i < cognome_temp.length(); i++)
        {
            if(cognome_temp.charAt(i) == 'A' || cognome_temp.charAt(i) == 'E' || cognome_temp.charAt(i) == 'I' || cognome_temp.charAt(i) == 'O' || cognome_temp.charAt(i) == 'U')
            {
                vocali += cognome_temp.charAt(i);
            }
            else
            {
                consonanti += cognome_temp.charAt(i);
            }
        }

        if(consonanti.length() >= 3)
        {
            codice_fiscale_temp = consonanti.substring(0, 3);
        }

        if(consonanti.length() == 2 && vocali.length() > 0)
        {
            codice_fiscale_temp += consonanti;
            codice_fiscale_temp += vocali.substring(0, 1);
        }

        if(consonanti.length() == 1 && vocali.length() == 1)
        {
            codice_fiscale_temp += consonanti;
            codice_fiscale_temp += vocali;
            codice_fiscale_temp += X;
        }

        if(consonanti.length() == 2 && vocali.length() == 0)
        {
            codice_fiscale_temp += consonanti;
            codice_fiscale_temp += X;
        }

        if(consonanti.length() == 1 && vocali.length() == 0)
        {
            codice_fiscale_temp += consonanti;
            codice_fiscale_temp += X;
            codice_fiscale_temp += X;
        }

        if(consonanti.length() == 1 && vocali.length() >= 2)
        {
            codice_fiscale_temp += consonanti.substring(0, 1);
            codice_fiscale_temp += consonanti.substring(0, 2);
        }

        if(consonanti.length() == 0 && vocali.length() >= 3)
        {
            codice_fiscale_temp += vocali.substring(0, 3);
        }

        if(consonanti.length() == 0 && vocali.length() == 2)
        {
            codice_fiscale_temp += vocali.substring(0, 2);
            codice_fiscale_temp += X;
        }

        if(consonanti.length() == 0 && vocali.length() == 1)
        {
            codice_fiscale_temp += vocali;
            codice_fiscale_temp += X;
            codice_fiscale_temp += X;
        }

        return codice_fiscale_temp;
    }

    public String codice_nome(){
        String codice_fiscale_temp = "";
        String vocali = "";
        String consonanti = "";

        String nome_temp = nome.toUpperCase();

        for(int i = 0; i < nome_temp.length(); i++)
        {
            if(nome_temp.charAt(i) == 'A' || nome_temp.charAt(i) == 'E' || nome_temp.charAt(i) == 'I' || nome_temp.charAt(i) == 'O' || nome_temp.charAt(i) == 'U')
            {
                vocali += nome_temp.charAt(i);
            }
            else
            {
                consonanti += nome_temp.charAt(i);
            }
        }

        if(consonanti.length() >= 4)
        {
            codice_fiscale_temp += consonanti.charAt(0);
            codice_fiscale_temp += consonanti.charAt(2);
            codice_fiscale_temp += consonanti.charAt(3);
        }

        if(consonanti.length() == 3)
        {
            codice_fiscale_temp += consonanti;
        }

        if(consonanti.length() == 2 && vocali.length() >= 1)
        {
            codice_fiscale_temp += consonanti;
            codice_fiscale_temp += vocali.substring(0, 1);
        }

        if(consonanti.length() == 2 && vocali.length() == 0)
        {
            codice_fiscale_temp += consonanti;
            codice_fiscale_temp += X;
        }

        if(consonanti.length() == 1 && vocali.length() == 0)
        {
            codice_fiscale_temp = consonanti;
            codice_fiscale_temp += X;
            codice_fiscale_temp += X;
        }

        if(consonanti.length() == 0 && vocali.length() >= 3)
        {
            codice_fiscale_temp += vocali.substring(0, 3);
        }

        if(consonanti.length() == 1 && vocali.length() == 1)
        {
            codice_fiscale_temp += consonanti;
            codice_fiscale_temp += vocali;
            codice_fiscale_temp += X;
        }

        if(consonanti.length() == 0 && vocali.length() == 2)
        {
            codice_fiscale_temp += vocali;
            codice_fiscale_temp += X;
        }

        return codice_fiscale_temp;
    }

    public String codice_data_nascita(){

        String codice_fiscale_temp = "";

        int mese = Integer.parseInt(data_nascita.substring(5, 7));
        int giorno = Integer.parseInt(data_nascita.substring(8));

        //estrazione dell'anno
        codice_fiscale_temp += data_nascita.charAt(2);
        codice_fiscale_temp += data_nascita.charAt(3);

        //estrazione del mese
        switch(mese){
            case 1:
                codice_fiscale_temp += "A";
                break;
            case 2:
                codice_fiscale_temp += "B";
                break;
            case 3:
                codice_fiscale_temp += "C";
                break;
            case 4:
                codice_fiscale_temp += "D";
                break;
            case 5:
                codice_fiscale_temp += "E";
                break;
            case 6:
                codice_fiscale_temp += "H";
                break;
            case 7:
                codice_fiscale_temp += "L";
                break;
            case 8:
                codice_fiscale_temp += "M";
                break;
            case 9:
                codice_fiscale_temp += "P";
                break;
            case 10:
                codice_fiscale_temp += "R";
                break;
            case 11:
                codice_fiscale_temp += "S";
                break;
            case 12:
                codice_fiscale_temp += "T";
                break;
        }

        //estrazione del giorno
        if(sesso.equals(FEMMINA))
        {
            giorno = giorno + 40;
        }

        else
        {
            if(giorno > 10)
            {
                codice_fiscale_temp += giorno;
            }

            else
            {
                codice_fiscale_temp += "0" + giorno;
            }
        }

        return codice_fiscale_temp;

    }

    public String codice_comune(){

        return Xml.leggiComune(comuneFile, comune_nascita.toUpperCase());

    }

    @Override
    public String toString() {
        return "Persona{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", sesso='" + sesso + '\'' +
                ", comune_nascita='" + comune_nascita + '\'' +
                ", data_nascita='" + data_nascita + '\'' +
                '}';
    }
}
