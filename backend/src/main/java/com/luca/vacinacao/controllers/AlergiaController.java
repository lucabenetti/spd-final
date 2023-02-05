package com.luca.vacinacao.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luca.vacinacao.dtos.AlergiaDTO;
import com.luca.vacinacao.models.AlergiaModel;
import com.luca.vacinacao.repositories.AlergiaRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

@RestController
public class AlergiaController {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @PostMapping(value="alergias/criar")
    public void Inserir(@RequestBody AlergiaDTO alergia) {
        var repository = new AlergiaRepository(entityManagerFactory);
        var alergiaModel = alergia.ToModel();
        alergiaModel.setId(repository.ObterId());
        repository.Salvar(alergiaModel);
    }
    @GetMapping(value="alergias/obter-todos")
    public List<AlergiaModel> ObterVarios() {
        var repository = new AlergiaRepository(entityManagerFactory);
        return repository.ObterTodos();
    }
    @GetMapping(value="alergias/obter-por-id")
    public AlergiaModel ObterPorId(@RequestParam int id) {
        var repository = new AlergiaRepository(entityManagerFactory);
        return repository.ObterPorId(id);
    }

    @DeleteMapping(value="alergias/excluir")
    public void Excluir(@RequestParam int id) {
        var repository = new AlergiaRepository(entityManagerFactory);
        repository.deletarPorId(id);
    }
}
