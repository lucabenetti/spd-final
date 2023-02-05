package com.luca.vacinacao.models;

import java.util.Date;

public class AgendaModel {
    private int id;
    private Date data;
    private String situacao;
    private Date data_situacao;
    private String observacoes;

    public VacinaModel vacina;

    public VacinaModel getVacina() {
        return vacina;
    }
    public void setVacina(VacinaModel vacina) {
        this.vacina = vacina;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }
    public String getSituacao() {
        return situacao;
    }
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    public Date getData_situacao() {
        return data_situacao;
    }
    public void setData_situacao(Date data_situacao) {
        this.data_situacao = data_situacao;
    }
    public String getObservacoes() {
        return observacoes;
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
