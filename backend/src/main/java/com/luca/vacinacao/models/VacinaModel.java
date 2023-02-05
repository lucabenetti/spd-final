package com.luca.vacinacao.models;

public class VacinaModel {
    private int id;
    private String titulo;
    private String descricao;
    private int doses;
    private int periodicidade;
    private int intervalo;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public int getDoses() {
        return doses;
    }
    public void setDoses(int doses) {
        this.doses = doses;
    }
    public int getPeriodicidade() {
        return periodicidade;
    }
    public void setPeriodicidade(int periodicidade) {
        this.periodicidade = periodicidade;
    }
    public int getIntervalo() {
        return intervalo;
    }
    public void setIntervalo(int intervalo) {
        this.intervalo = intervalo;
    }   
}