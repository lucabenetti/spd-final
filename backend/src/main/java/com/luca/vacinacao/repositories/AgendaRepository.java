package com.luca.vacinacao.repositories;
import java.util.ArrayList;
import java.util.List;

import com.luca.vacinacao.models.AgendaModel;
import com.luca.vacinacao.models.VacinaModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class AgendaRepository extends BaseRepository {
    private EntityManager entityManager;

    public AgendaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void DeletarAssociacaoUsuarioAgenda(){

    }

    public void AtualizarStatusAssociacaoUsuarioAgenda() {
        
    }

    public List<AgendaModel> ObterAgendasAssociadaAoUsuario(int usuarioId) {
        var queryStr = "select Agendas.id, Agendas.data, Agendas.situacao, Agendas.data_situacao, Agendas.observacoes, Vacinas.id, Vacinas.descricao, Vacinas.doses, Vacinas.intervalo, Vacinas.periodicidade, Vacinas.titulo from Agendas inner join Vacinas on Agendas.vacinaid = Vacinas.id where usuarioid = ?1";
    
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

                agenda.setVacina(vacina);
                agendas.add(agenda);
            }

            return agendas;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
