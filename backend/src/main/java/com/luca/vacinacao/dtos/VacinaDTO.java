package com.luca.vacinacao.dtos;

import com.luca.vacinacao.models.VacinaModel;

public class VacinaDTO {
    public String titulo;
    public String descricao;
    public int doses;
    public int periodicidade;
    public int intervalo;

    public VacinaModel ToModel(){
        var vacina = new VacinaModel();

        vacina.setTitulo(titulo);
        vacina.setDescricao(descricao);
        vacina.setDoses(doses);
        vacina.setPeriodicidade(periodicidade);
        vacina.setIntervalo(intervalo);

        return vacina;
    }
}
