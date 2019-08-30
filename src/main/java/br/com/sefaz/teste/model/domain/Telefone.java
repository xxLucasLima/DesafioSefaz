package br.com.sefaz.teste.model.domain;

import javax.validation.constraints.NotNull;

public class Telefone {

    public int Id;
    public int UsuarioId;
    public int Ddd;
    public String Numero;
    public String Tipo;

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

    @NotNull
    public int getDdd() {
        return Ddd;
    }

    public void setDdd(int ddd) {
        Ddd = ddd;
    }

    @NotNull
    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    @NotNull
    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

}
