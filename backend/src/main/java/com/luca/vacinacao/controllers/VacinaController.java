package com.luca.vacinacao.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luca.vacinacao.dtos.VacinaDTO;
import com.luca.vacinacao.models.VacinaModel;
import com.luca.vacinacao.repositories.VacinaRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

@RestController
public class VacinaController {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @PostMapping(value="vacinas/criar")
    public void Inserir(@RequestBody VacinaDTO vacina) {
        var repository = new VacinaRepository(entityManagerFactory);
        var vacinaModel = vacina.ToModel();
        vacinaModel.setId(repository.ObterId());
        repository.Salvar(vacinaModel);
    }
    @GetMapping(value="vacinas/obter-todos")
    public List<VacinaModel> ObterVarios() {
        var repository = new VacinaRepository(entityManagerFactory);
        return repository.ObterTodos();
    }
    @GetMapping(value="usuarios/obter-por-id")
    public VacinaModel ObterPorId(@RequestParam int id) {
        var repository = new VacinaRepository(entityManagerFactory);
        return repository.ObterPorId(id);
    }

    @DeleteMapping(value="usuarios/excluir")
    public void Excluir(@RequestParam int id) {
        var repository = new VacinaRepository(entityManagerFactory);
        repository.deletarPorId(id);
    }
}
