package com.luca.vacinacao.models;

import java.util.Date;
import java.util.List;

public class UsuarioModel {
    private int id;

    private String nome;

    private Date data_nascimento;

    private String sexo;

    private String logradouro;

    private int numero;

    private String setor;

    private String cidade;

    private String uf;

    private List<AlergiaModel> alergias;

    private List<AgendaModel> agendas;

    public List<AgendaModel> getAgendas() {
        return agendas;
    }
    public void setAgendas(List<AgendaModel> agendas) {
        this.agendas = agendas;
    }
    public List<AlergiaModel> getAlergias() {
        return alergias;
    }
    public void setAlergias(List<AlergiaModel> alergias) {
        this.alergias = alergias;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Date getData_nascimento() {
        return data_nascimento;
    }
    public void setData_nascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }
    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public String getLogradouro() {
        return logradouro;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public String getSetor() {
        return setor;
    }
    public void setSetor(String setor) {
        this.setor = setor;
    }
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public String getUf() {
        return uf;
    }
    public void setUf(String uf) {
        this.uf = uf;
    }

    
}