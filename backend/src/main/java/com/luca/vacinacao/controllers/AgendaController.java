package com.luca.vacinacao.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luca.vacinacao.dtos.AgendaDTO;
import com.luca.vacinacao.dtos.AtualizarAgendaDTO;
import com.luca.vacinacao.dtos.DeletarDTO;
import com.luca.vacinacao.dtos.FiltrarAgendaDTO;
import com.luca.vacinacao.models.AgendaModel;
import com.luca.vacinacao.repositories.AgendaRepository;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

import org.springframework.web.bind.annotation.*;

@RestController
public class AgendaController {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @PostMapping(value="agendas/criar")
    public void Inserir(@RequestBody AgendaDTO agenda) {
        var repository = new AgendaRepository(entityManagerFactory);
        repository.Salvar(agenda);
    }
    @PutMapping(value="agendas/atualizar-status")
    public void AtualizarStatus(@RequestBody AtualizarAgendaDTO agenda) throws Exception {
        if(agenda.situacao == "Realizada" || agenda.situacao == "Cancelada") {
            var repository = new AgendaRepository(entityManagerFactory);

            repository.AtualizarStatusAgenda(agenda);
        }

        throw new Exception("Situação inválida");
    }
    @PostMapping(value="agendas/obter-todos")
    public List<AgendaModel> ObterVarios(@RequestBody FiltrarAgendaDTO filtrarAgendaDTO) {
        var repository = new AgendaRepository(entityManagerFactory);
        return repository.ObterTodos(filtrarAgendaDTO);
    }
    @GetMapping(value="agendas/obter-por-id")
    public AgendaModel ObterPorId(@RequestParam int id) {
        var repository = new AgendaRepository(entityManagerFactory);
        return repository.ObterPorId(id);
    }

    @DeleteMapping(value="agendas/excluir")
    public void Excluir(@RequestBody DeletarDTO dto) {
        var repository = new AgendaRepository(entityManagerFactory);
        repository.deletarPorId(dto.id);
    }
}
