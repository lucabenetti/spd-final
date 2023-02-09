package com.luca.vacinacao.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luca.vacinacao.dtos.AlergiaDTO;
import com.luca.vacinacao.dtos.DeletarDTO;
import com.luca.vacinacao.models.AlergiaModel;
import com.luca.vacinacao.repositories.AlergiaRepository;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class AlergiaController {

    @PostMapping(value="alergias/criar")
    public void Inserir(@RequestBody AlergiaDTO alergia) throws Exception {
        var repository = new AlergiaRepository();
        var alergiaModel = alergia.ToModel();
        alergiaModel.setId(repository.ObterId());
        repository.Salvar(alergiaModel);
    }
    @GetMapping(value="alergias/obter-todos")
    public List<AlergiaModel> ObterVarios() throws Exception {
        var repository = new AlergiaRepository();
        return repository.ObterTodos();
    }
    @GetMapping(value="alergias/obter-por-id")
    public AlergiaModel ObterPorId(@RequestParam int id) throws Exception {
        var repository = new AlergiaRepository();
        return repository.ObterPorId(id);
    }

    @DeleteMapping(value="alergias/excluir")
    public void Excluir(@RequestBody DeletarDTO dto) throws Exception {
        var repository = new AlergiaRepository();
        repository.deletarPorId(dto.id);
    }
}
