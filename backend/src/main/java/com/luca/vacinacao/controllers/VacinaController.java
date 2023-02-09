package com.luca.vacinacao.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luca.vacinacao.dtos.DeletarDTO;
import com.luca.vacinacao.dtos.VacinaDTO;
import com.luca.vacinacao.models.VacinaModel;
import com.luca.vacinacao.repositories.VacinaRepository;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class VacinaController {


    @PostMapping(value="vacinas/criar")
    public void Inserir(@RequestBody VacinaDTO vacina) throws Exception {
        var repository = new VacinaRepository();
        var vacinaModel = vacina.ToModel();
        vacinaModel.setId(repository.ObterId());
        repository.Salvar(vacinaModel);
    }
    @GetMapping(value="vacinas/obter-todos")
    public List<VacinaModel> ObterVarios() throws Exception {
        var repository = new VacinaRepository();
        return repository.ObterTodos();
    }
    @GetMapping(value="vacinas/obter-por-id")
    public VacinaModel ObterPorId(@RequestParam int id) throws Exception {
        var repository = new VacinaRepository();
        return repository.ObterPorId(id);
    }

    @DeleteMapping(value="vacinas/excluir")
    public void Excluir(@RequestBody DeletarDTO dto) throws Exception {
        var repository = new VacinaRepository();
        repository.deletarPorId(dto.id);
    }
}
