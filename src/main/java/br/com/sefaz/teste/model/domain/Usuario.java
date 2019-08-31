package br.com.sefaz.teste.model.domain;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    public int Id;
    public String Nome;
    public String Email;
    public String Login;
    public String Senha;
    public ArrayList<Telefone> Telefones;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }    
    
    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }    
    
    public ArrayList<Telefone> getTelefones() {
        return Telefones;
    }
}
