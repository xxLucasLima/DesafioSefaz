package br.com.sefaz.teste.model.domain;

public class Telefone {

    public int Id;
    private String IdString;
    public int UsuarioId;
    public int Ddd;
    public String Numero;
    public char Tipo;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }

    public int getDdd() {
        return Ddd;
    }

    public void setDdd(int ddd) {
        Ddd = ddd;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public char getTipo() {
        return Tipo;
    }

    public void setTipo(char tipo) {
        Tipo = tipo;
    }

    public String getIdString() {
        return IdString;
    }

    public void setIdString(String IdString) {
        this.IdString = IdString;
    }

}
