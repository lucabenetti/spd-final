package com.luca.vacinacao.repositories;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.luca.vacinacao.dtos.AgendaDTO;
import com.luca.vacinacao.dtos.AtualizarAgendaDTO;
import com.luca.vacinacao.dtos.FiltrarAgendaDTO;
import com.luca.vacinacao.models.AgendaModel;
import com.luca.vacinacao.models.UsuarioModel;
import com.luca.vacinacao.models.VacinaModel;

public class AgendaRepository extends BaseRepository {
    private Connection con;

    public AgendaRepository() throws SQLException {
        con = FabricaDeConexao.obterConexao();
    }

    public void AtualizarStatusAgenda(AtualizarAgendaDTO atualizarAgendaDTO) throws Exception {
        String queryStr = "UPDATE Agendas set situacao = ?, data_situacao = ? where id = ?";
        try {
            var id = ObterId();
            var et = con.prepareStatement(queryStr);

            et.setInt(3, id);
            et.setString(1, atualizarAgendaDTO.situacao);
            et.setDate(2, new java.sql.Date(new Date().getTime()));
            et.executeUpdate();
            et.execute();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void Salvar(AgendaDTO agenda) throws Exception{
        String queryStr = "INSERT INTO Agendas values(?, ?, ?, ?, ?, ?, ?)";
        try {
            var id = ObterId();
            var query = con.prepareStatement(queryStr);
            query.setInt(1, id);
            query.setDate(2, new java.sql.Date(agenda.data.getTime()));
            query.setString(3, "Agendada");
            query.setDate(4, new java.sql.Date(new Date().getTime()));
            query.setString(5, agenda.observacoes);
            query.setInt(6, agenda.usuarioId);
            query.setInt(7, agenda.vacinaId);
            query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int ObterId() throws Exception {
        var queryStr = "select max(id) from Agendas";
        
        try {
            
            var query = con.prepareStatement(queryStr);
            var resultado = query.executeQuery();

            if(resultado.next()){
                var ultimoId = resultado.getInt(1);
                return ultimoId + 1;
            }

            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<AgendaModel> ObterAgendasAssociadaAoUsuario(int usuarioId) throws Exception {
        var queryStr = "select Agendas.id, Agendas.data, Agendas.situacao, Agendas.data_situacao, Agendas.observacoes, Vacinas.id, Vacinas.descricao, Vacinas.doses, Vacinas.intervalo, Vacinas.periodicidade, Vacinas.titulo, Usuarios.id, Usuarios.nome, Usuarios.data_nascimento, Usuarios.sexo, Usuarios.logradouro, Usuarios.numero, Usuarios.setor, Usuarios.uf, Usuarios.cidade  from Agendas inner join Vacinas on Agendas.vacinaid = Vacinas.id inner join Usuarios on Agendas.usuarioid = Usuarios.id where usuarioid = ?";
    
        try {
            var agendas = new ArrayList<AgendaModel>();
            
            var query = con.prepareStatement(queryStr);
            query.setInt(1, usuarioId);
            var resultado = query.executeQuery();

            while (resultado.next()) {
                var agenda = new AgendaModel();
                agenda.setId(ObterRegistroInt(resultado, 0));
                agenda.setData(ObterRegistroData(resultado, 1));
                agenda.setSituacao(ObterRegistroString(resultado, 2));
                agenda.setData_situacao(ObterRegistroData(resultado, 3));
                agenda.setObservacoes(ObterRegistroString(resultado, 4));

                var vacina = new VacinaModel();
                vacina.setId(ObterRegistroInt(resultado, 5));
                vacina.setDescricao(ObterRegistroString(resultado, 6));
                vacina.setDoses(ObterRegistroInt(resultado, 7));
                vacina.setIntervalo(ObterRegistroInt(resultado, 8));
                vacina.setPeriodicidade(ObterRegistroInt(resultado, 9));
                vacina.setTitulo(ObterRegistroString(resultado, 10));

                var usuario = new UsuarioModel();
                usuario.setId(ObterRegistroInt(resultado, 11));
                usuario.setNome(ObterRegistroString(resultado, 12));
                usuario.setData_nascimento(ObterRegistroData(resultado, 13));
                usuario.setSexo(ObterRegistroChar(resultado, 14));
                usuario.setLogradouro(ObterRegistroString(resultado, 15));
                usuario.setNumero(ObterRegistroInt(resultado, 16));
                usuario.setSetor(ObterRegistroString(resultado, 17));
                usuario.setUf(ObterRegistroString(resultado, 18));
                usuario.setCidade(ObterRegistroString(resultado, 19));
                
                agenda.setUsuario(usuario);
                agenda.setVacina(vacina);
                agendas.add(agenda);
            }

            return agendas;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<AgendaModel> ObterTodos(FiltrarAgendaDTO filtrarAgendaDTO) throws Exception {
        var queryStr = "select Agendas.id, Agendas.data, Agendas.situacao, Agendas.data_situacao, Agendas.observacoes, Vacinas.id, Vacinas.descricao, Vacinas.doses, Vacinas.intervalo, Vacinas.periodicidade, Vacinas.titulo, Usuarios.id, Usuarios.nome, Usuarios.data_nascimento, Usuarios.sexo, Usuarios.logradouro, Usuarios.numero, Usuarios.setor, Usuarios.uf, Usuarios.cidade  from Agendas inner join Vacinas on Agendas.vacinaid = Vacinas.id inner join Usuarios on Agendas.usuarioid = Usuarios.id";

        try {
            var agendas = new ArrayList<AgendaModel>();
            
            var query = con.prepareStatement(queryStr);
            var resultado = query.executeQuery();

            while (resultado.next()) {
                var agenda = new AgendaModel();
                agenda.setId(ObterRegistroInt(resultado, 1));
                agenda.setData(ObterRegistroData(resultado, 2));
                agenda.setSituacao(ObterRegistroString(resultado, 3));
                agenda.setData_situacao(ObterRegistroData(resultado, 4));
                agenda.setObservacoes(ObterRegistroString(resultado, 5));

                var vacina = new VacinaModel();
                vacina.setId(ObterRegistroInt(resultado, 6));
                vacina.setDescricao(ObterRegistroString(resultado, 7));
                vacina.setDoses(ObterRegistroInt(resultado, 8));
                vacina.setIntervalo(ObterRegistroInt(resultado, 9));
                vacina.setPeriodicidade(ObterRegistroInt(resultado, 10));
                vacina.setTitulo(ObterRegistroString(resultado, 11));

                var usuario = new UsuarioModel();
                usuario.setId(ObterRegistroInt(resultado, 12));
                usuario.setNome(ObterRegistroString(resultado, 13));
                usuario.setData_nascimento(ObterRegistroData(resultado, 14));
                usuario.setSexo(ObterRegistroChar(resultado, 15));
                usuario.setLogradouro(ObterRegistroString(resultado, 16));
                usuario.setNumero(ObterRegistroInt(resultado, 17));
                usuario.setSetor(ObterRegistroString(resultado, 18));
                usuario.setUf(ObterRegistroString(resultado, 19));
                usuario.setCidade(ObterRegistroString(resultado, 20));
                
                agenda.setUsuario(usuario);
                agenda.setVacina(vacina);
                agendas.add(agenda);
            }

            if(filtrarAgendaDTO.filtrarSituacao) {
                var agendaFiltrada = new ArrayList<AgendaModel>();
                for (AgendaModel agendaz : agendas) {
                    if(agendaz.getSituacao().toUpperCase().equals(filtrarAgendaDTO.situacao.toUpperCase())){
                        agendaFiltrada.add(agendaz);
                    }
                }
                agendas = agendaFiltrada;
            }

            if(filtrarAgendaDTO.filtrarDataAtual){
                var agendaFiltrada = new ArrayList<AgendaModel>();
                for (AgendaModel agendaz : agendas) {
                    LocalDate date = LocalDate.now();
                    
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
                    var formartz = DateTimeFormatter.ofPattern("dd/MM/yy");

                    String dateToday = date.format(formartz);

                    if(formatter.format(agendaz.getData()).equals(dateToday)){
                        agendaFiltrada.add(agendaz);
                    }
                }
                return agendaFiltrada;
            }

            return agendas;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public AgendaModel ObterPorId(int id) throws Exception {
        var queryStr = "select Agendas.id, Agendas.data, Agendas.situacao, Agendas.data_situacao, Agendas.observacoes, Vacinas.id, Vacinas.descricao, Vacinas.doses, Vacinas.intervalo, Vacinas.periodicidade, Vacinas.titulo, Usuarios.id, Usuarios.nome, Usuarios.data_nascimento, Usuarios.sexo, Usuarios.logradouro, Usuarios.numero, Usuarios.setor, Usuarios.uf, Usuarios.cidade  from Agendas inner join Vacinas on Agendas.vacinaid = Vacinas.id inner join Usuarios on Agendas.usuarioid = Usuarios.id where Agendas.id = ?";
    
        try {
            var agendas = new ArrayList<AgendaModel>();
            
            var query = con.prepareStatement(queryStr);
            query.setInt(1, id);
            var resultado = query.executeQuery();

            while (resultado.next()) {
                var agenda = new AgendaModel();
                agenda.setId(ObterRegistroInt(resultado, 0));
                agenda.setData(ObterRegistroData(resultado, 1));
                agenda.setSituacao(ObterRegistroString(resultado, 2));
                agenda.setData_situacao(ObterRegistroData(resultado, 3));
                agenda.setObservacoes(ObterRegistroString(resultado, 4));

                var vacina = new VacinaModel();
                vacina.setId(ObterRegistroInt(resultado, 5));
                vacina.setDescricao(ObterRegistroString(resultado, 6));
                vacina.setDoses(ObterRegistroInt(resultado, 7));
                vacina.setIntervalo(ObterRegistroInt(resultado, 8));
                vacina.setPeriodicidade(ObterRegistroInt(resultado, 9));
                vacina.setTitulo(ObterRegistroString(resultado, 10));

                var usuario = new UsuarioModel();
                usuario.setId(ObterRegistroInt(resultado, 11));
                usuario.setNome(ObterRegistroString(resultado, 12));
                usuario.setData_nascimento(ObterRegistroData(resultado, 13));
                usuario.setSexo(ObterRegistroChar(resultado, 14));
                usuario.setLogradouro(ObterRegistroString(resultado, 15));
                usuario.setNumero(ObterRegistroInt(resultado, 16));
                usuario.setSetor(ObterRegistroString(resultado, 17));
                usuario.setUf(ObterRegistroString(resultado, 18));
                usuario.setCidade(ObterRegistroString(resultado, 19));
                
                agenda.setUsuario(usuario);
                agenda.setVacina(vacina);
                agendas.add(agenda);
            }

            if(agendas.isEmpty()) return null;
            return agendas.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void deletarPorId(int id) throws Exception {
        String queryStr = "DELETE FROM Agendas where id = ?";
        try {
            var query = con.prepareStatement(queryStr);
            query.setInt(1, id);
            query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
