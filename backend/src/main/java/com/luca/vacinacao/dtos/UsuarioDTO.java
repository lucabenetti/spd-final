package com.luca.vacinacao.dtos;

import java.util.Date;

import com.luca.vacinacao.models.UsuarioModel;

public class UsuarioDTO {
    public String nome;
    public Date dt_nasc;
    public String sexo;
    public String logradouro;
    public int numero;
    public String setor;
    public String cidade;
    public String uf;

    public UsuarioModel ToModel(){
        var usuarioModel = new UsuarioModel();
        usuarioModel.setNome(this.nome);
        usuarioModel.setData_nascimento(this.dt_nasc);
        usuarioModel.setSexo(this.sexo);
        usuarioModel.setLogradouro(this.logradouro);
        usuarioModel.setNumero(this.numero);
        usuarioModel.setSetor(this.setor);
        usuarioModel.setCidade(this.cidade);
        usuarioModel.setUf(this.uf);
        return usuarioModel;
    }
}
