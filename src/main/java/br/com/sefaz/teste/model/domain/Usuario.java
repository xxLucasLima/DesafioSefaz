package br.com.sefaz.teste.model.domain;

import javax.validation.constraints.*;
import sun.security.util.Length;

public class Usuario {

    public int Id;
    public String Nome;
    public String Email;
    public String Senha;
    public Telefone Telefone;

//	public Usuario(Telefone telefone) {
//		this.telefone = telefone;
//	}
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Telefone getTelefone() {
        return Telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.Telefone = telefone;
    }

    @NotNull
    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    @NotNull
    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    @NotNull
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
