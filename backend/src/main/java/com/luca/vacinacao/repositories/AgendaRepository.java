package com.luca.vacinacao.repositories;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.luca.vacinacao.dtos.AgendaDTO;
import com.luca.vacinacao.dtos.AtualizarAgendaDTO;
import com.luca.vacinacao.dtos.FiltrarAgendaDTO;
import com.luca.vacinacao.models.AgendaModel;
import com.luca.vacinacao.models.UsuarioModel;
import com.luca.vacinacao.models.VacinaModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

public class AgendaRepository extends BaseRepository {
    private EntityManager entityManager;

    public AgendaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public AgendaRepository(EntityManagerFactory entityManagerFactory){
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void AtualizarStatusAgenda(AtualizarAgendaDTO atualizarAgendaDTO) {
        String queryStr = "UPDATE Agendas set situacao = ?1, data_situacao = ?2 where id = ?3";
        try {
            var id = ObterId();
            EntityTransaction et = entityManager.getTransaction();

            et.begin();
            Query query = entityManager.createNativeQuery(queryStr);
            query.setParameter(3, id);
            query.setParameter(1, atualizarAgendaDTO.situacao);
            query.setParameter(2, new Date());
            query.executeUpdate();
            et.commit();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void Salvar(AgendaDTO agenda){
        String queryStr = "INSERT INTO Agendas values(?1, ?2, ?3, ?4, ?5, ?6, ?7)";
        try {
            var id = ObterId();
            EntityTransaction et = entityManager.getTransaction();

            et.begin();
            Query query = entityManager.createNativeQuery(queryStr);
            query.setParameter(1, id);
            query.setParameter(2, agenda.data);
            query.setParameter(3, "Agendada");
            query.setParameter(4, new Date());
            query.setParameter(5, agenda.observacoes);
            query.setParameter(6, agenda.usuarioId);
            query.setParameter(7, agenda.vacinaId);
            query.executeUpdate();
            et.commit();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int ObterId() {
        var queryStr = "select max(id) from Agendas";
        
        try {
            
            Query query = entityManager.createNativeQuery(queryStr);
            Object linha = query.getSingleResult();

            var ultimoId = ObterRegistroInt(linha);
            return ultimoId + 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<AgendaModel> ObterAgendasAssociadaAoUsuario(int usuarioId) {
        var queryStr = "select Agendas.id, Agendas.data, Agendas.situacao, Agendas.data_situacao, Agendas.observacoes, Vacinas.id, Vacinas.descricao, Vacinas.doses, Vacinas.intervalo, Vacinas.periodicidade, Vacinas.titulo, Usuarios.id, Usuarios.nome, Usuarios.data_nascimento, Usuarios.sexo, Usuarios.logradouro, Usuarios.numero, Usuarios.setor, Usuarios.uf, Usuarios.cidade  from Agendas inner join Vacinas on Agendas.vacinaid = Vacinas.id inner join Usuarios on Agendas.usuarioid = Usuarios.id where usuarioid = ?1";
    
        try {
            var agendas = new ArrayList<AgendaModel>();
            
            Query query = entityManager.createNativeQuery(queryStr);
            query.setParameter(1, usuarioId);
            List<Object[]> linhas = query.getResultList();

            for (Object[] coluna : linhas) {
                var agenda = new AgendaModel();
                agenda.setId(ObterRegistroInt(coluna[0]));
                agenda.setData(ObterRegistroData(coluna[1]));
                agenda.setSituacao(ObterRegistroString(coluna[2]));
                agenda.setData_situacao(ObterRegistroData(coluna[3]));
                agenda.setObservacoes(ObterRegistroString(coluna[4]));

                var vacina = new VacinaModel();
                vacina.setId(ObterRegistroInt(coluna[5]));
                vacina.setDescricao(ObterRegistroString(coluna[6]));
                vacina.setDoses(ObterRegistroInt(coluna[7]));
                vacina.setIntervalo(ObterRegistroInt(coluna[8]));
                vacina.setPeriodicidade(ObterRegistroInt(coluna[9]));
                vacina.setTitulo(ObterRegistroString(coluna[10]));

                var usuario = new UsuarioModel();
                usuario.setId(ObterRegistroInt(coluna[11]));
                usuario.setNome(ObterRegistroString(coluna[12]));
                usuario.setData_nascimento(ObterRegistroData(coluna[13]));
                usuario.setSexo(ObterRegistroChar(coluna[14]));
                usuario.setLogradouro(ObterRegistroString(coluna[15]));
                usuario.setNumero(ObterRegistroInt(coluna[16]));
                usuario.setSetor(ObterRegistroString(coluna[17]));
                usuario.setUf(ObterRegistroString(coluna[18]));
                usuario.setCidade(ObterRegistroString(coluna[19]));
                
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

    public List<AgendaModel> ObterTodos(FiltrarAgendaDTO filtrarAgendaDTO) {
        var queryStr = "select Agendas.id, Agendas.data, Agendas.situacao, Agendas.data_situacao, Agendas.observacoes, Vacinas.id, Vacinas.descricao, Vacinas.doses, Vacinas.intervalo, Vacinas.periodicidade, Vacinas.titulo, Usuarios.id, Usuarios.nome, Usuarios.data_nascimento, Usuarios.sexo, Usuarios.logradouro, Usuarios.numero, Usuarios.setor, Usuarios.uf, Usuarios.cidade  from Agendas inner join Vacinas on Agendas.vacinaid = Vacinas.id inner join Usuarios on Agendas.usuarioid = Usuarios.id";

        try {
            var agendas = new ArrayList<AgendaModel>();
            
            Query query = entityManager.createNativeQuery(queryStr);

            List<Object[]> linhas = query.getResultList();

            for (Object[] coluna : linhas) {
                var agenda = new AgendaModel();
                agenda.setId(ObterRegistroInt(coluna[0]));
                agenda.setData(ObterRegistroData(coluna[1]));
                agenda.setSituacao(ObterRegistroString(coluna[2]));
                agenda.setData_situacao(ObterRegistroData(coluna[3]));
                agenda.setObservacoes(ObterRegistroString(coluna[4]));

                var vacina = new VacinaModel();
                vacina.setId(ObterRegistroInt(coluna[5]));
                vacina.setDescricao(ObterRegistroString(coluna[6]));
                vacina.setDoses(ObterRegistroInt(coluna[7]));
                vacina.setIntervalo(ObterRegistroInt(coluna[8]));
                vacina.setPeriodicidade(ObterRegistroInt(coluna[9]));
                vacina.setTitulo(ObterRegistroString(coluna[10]));

                var usuario = new UsuarioModel();
                usuario.setId(ObterRegistroInt(coluna[11]));
                usuario.setNome(ObterRegistroString(coluna[12]));
                usuario.setData_nascimento(ObterRegistroData(coluna[13]));
                usuario.setSexo(ObterRegistroChar(coluna[14]));
                usuario.setLogradouro(ObterRegistroString(coluna[15]));
                usuario.setNumero(ObterRegistroInt(coluna[16]));
                usuario.setSetor(ObterRegistroString(coluna[17]));
                usuario.setUf(ObterRegistroString(coluna[18]));
                usuario.setCidade(ObterRegistroString(coluna[19]));
                
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

    public AgendaModel ObterPorId(int id) {
        var queryStr = "select Agendas.id, Agendas.data, Agendas.situacao, Agendas.data_situacao, Agendas.observacoes, Vacinas.id, Vacinas.descricao, Vacinas.doses, Vacinas.intervalo, Vacinas.periodicidade, Vacinas.titulo, Usuarios.id, Usuarios.nome, Usuarios.data_nascimento, Usuarios.sexo, Usuarios.logradouro, Usuarios.numero, Usuarios.setor, Usuarios.uf, Usuarios.cidade  from Agendas inner join Vacinas on Agendas.vacinaid = Vacinas.id inner join Usuarios on Agendas.usuarioid = Usuarios.id where Agendas.id = ?1";
    
        try {
            var agendas = new ArrayList<AgendaModel>();
            
            Query query = entityManager.createNativeQuery(queryStr);
            query.setParameter(1, id);
            List<Object[]> linhas = query.getResultList();

            for (Object[] coluna : linhas) {
                var agenda = new AgendaModel();
                agenda.setId(ObterRegistroInt(coluna[0]));
                agenda.setData(ObterRegistroData(coluna[1]));
                agenda.setSituacao(ObterRegistroString(coluna[2]));
                agenda.setData_situacao(ObterRegistroData(coluna[3]));
                agenda.setObservacoes(ObterRegistroString(coluna[4]));

                var vacina = new VacinaModel();
                vacina.setId(ObterRegistroInt(coluna[5]));
                vacina.setDescricao(ObterRegistroString(coluna[6]));
                vacina.setDoses(ObterRegistroInt(coluna[7]));
                vacina.setIntervalo(ObterRegistroInt(coluna[8]));
                vacina.setPeriodicidade(ObterRegistroInt(coluna[9]));
                vacina.setTitulo(ObterRegistroString(coluna[10]));

                var usuario = new UsuarioModel();
                usuario.setId(ObterRegistroInt(coluna[11]));
                usuario.setNome(ObterRegistroString(coluna[12]));
                usuario.setData_nascimento(ObterRegistroData(coluna[13]));
                usuario.setSexo(ObterRegistroChar(coluna[14]));
                usuario.setLogradouro(ObterRegistroString(coluna[15]));
                usuario.setNumero(ObterRegistroInt(coluna[16]));
                usuario.setSetor(ObterRegistroString(coluna[17]));
                usuario.setUf(ObterRegistroString(coluna[18]));
                usuario.setCidade(ObterRegistroString(coluna[19]));
                
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

    public void deletarPorId(int id) {
        String queryStr = "DELETE FROM Agendas where id = ?1";
        try {
            EntityTransaction et = entityManager.getTransaction();

            et.begin();
            Query query = entityManager.createNativeQuery(queryStr);
            query.setParameter(1, id);
            query.executeUpdate();
            et.commit();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
