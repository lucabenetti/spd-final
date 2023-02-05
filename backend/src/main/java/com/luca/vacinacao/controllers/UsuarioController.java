package com.luca.vacinacao.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luca.vacinacao.dtos.DeletarDTO;
import com.luca.vacinacao.dtos.UsuarioAlergiaDTO;
import com.luca.vacinacao.dtos.UsuarioDTO;
import com.luca.vacinacao.models.UsuarioModel;
import com.luca.vacinacao.repositories.UsuarioRepository;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

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
    public void Excluir(@RequestBody DeletarDTO dto) {
        var repository = new UsuarioRepository(entityManagerFactory);
        repository.deletarPorId(dto.id);
    }

    @PutMapping(value="usuarios/associar-usuario-alergia")
    public void Excluir(@RequestBody List<UsuarioAlergiaDTO> dto) {
        var repository = new UsuarioRepository(entityManagerFactory);
        repository.associarUsuarioAlergia(dto);
    }
}
