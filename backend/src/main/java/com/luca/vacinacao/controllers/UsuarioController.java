package com.luca.vacinacao.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luca.vacinacao.dtos.UsuarioDTO;
import com.luca.vacinacao.models.UsuarioModel;
import com.luca.vacinacao.repositories.UsuarioRepository;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Transactional
    @PostMapping(value="usuarios/criar")
    public void Inserir(@RequestBody UsuarioDTO usuario) {
        var repository = new UsuarioRepository(entityManagerFactory);
        var usuarioModel = usuario.ToModel();
        usuarioModel.setId(repository.ObterId());
        repository.Salvar(usuarioModel);
    }
    @GetMapping(value="usuarios/obter-todos")
    public List<UsuarioModel> ObterVarios() {
        var repository = new UsuarioRepository(entityManagerFactory);
        return repository.ObterTodos();
    }
    @GetMapping(value="usuarios/obter-por-id")
    public UsuarioModel ObterPorId(@RequestParam int id) {
        var repository = new UsuarioRepository(entityManagerFactory);
        return repository.ObterPorId(id);
    }
    
    @DeleteMapping(value="usuarios/excluir")
    public void Excluir(@RequestParam int id) {
        var repository = new UsuarioRepository(entityManagerFactory);
        repository.deletarPorId(id);
    }
}