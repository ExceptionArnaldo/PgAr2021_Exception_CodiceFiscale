package it.unibs.fp.codicefiscale;

public class Persona {

    private String nome;
    private String cognome;
    private String sesso;
    private String comune_nascita;
    private String data_nascita;

    public Persona(String nome, String cognome, String sesso, String comune_nascita, String data_nascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.comune_nascita = comune_nascita;
        this.data_nascita = data_nascita;
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
