package com.luca.vacinacao.dtos;

import com.luca.vacinacao.models.AlergiaModel;

public class AlergiaDTO {
    public String nome; 

    public AlergiaModel ToModel(){
        var alergiaModel = new AlergiaModel();
        alergiaModel.setNome(nome);
        return alergiaModel;
    }
}
