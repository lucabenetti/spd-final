package com.luca.vacinacao.repositories;

import java.util.ArrayList;
import java.util.List;

import com.luca.vacinacao.models.VacinaModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

public class VacinaRepository extends BaseRepository {
    private EntityManager entityManager;

    public VacinaRepository(EntityManagerFactory entityManagerFactory){
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void Salvar(VacinaModel vacina){
        String queryStr = "INSERT INTO Vacinas values(?1, ?2, ?3, ?4, ?5, ?6)";
        try {
            EntityTransaction et = entityManager.getTransaction();

            et.begin();
            Query query = entityManager.createNativeQuery(queryStr);
            query.setParameter(1, vacina.getId());
            query.setParameter(2, vacina.getTitulo());
            query.setParameter(3, vacina.getDescricao());
            query.setParameter(4, vacina.getDoses());
            query.setParameter(5, vacina.getPeriodicidade());
            query.setParameter(6, vacina.getIntervalo());
            query.executeUpdate();
            et.commit();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<VacinaModel> ObterTodos() {
        String queryStr = "select id, descricao, doses, intervalo, periodicidade, titulo from Vacinas";
        try {
            var vacinas = new ArrayList<VacinaModel>();
            
            Query query = entityManager.createNativeQuery(queryStr);
            List<Object[]> linhas = query.getResultList();

            for (Object[] coluna : linhas) {
                var vacina = new VacinaModel();
                vacina.setId(ObterRegistroInt(coluna[0]));
                vacina.setDescricao(ObterRegistroString(coluna[1]));
                vacina.setDoses(ObterRegistroInt(coluna[2]));
                vacina.setIntervalo(ObterRegistroInt(coluna[3]));
                vacina.setPeriodicidade(ObterRegistroInt(coluna[4]));
                vacina.setTitulo(ObterRegistroString(coluna[5]));
                vacinas.add(vacina);
            }

            return vacinas;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public VacinaModel ObterPorId(int id) {
        String queryStr = "select id, descricao, doses, intervalo, periodicidade, titulo from Vacinas where id = ?1";
        try {
            var vacinas = new ArrayList<VacinaModel>();
            
            Query query = entityManager.createNativeQuery(queryStr);
            query.setParameter(1, id);
            List<Object[]> linhas = query.getResultList();

            for (Object[] coluna : linhas) {
                var vacina = new VacinaModel();
                vacina.setId(ObterRegistroInt(coluna[0]));
                vacina.setDescricao(ObterRegistroString(coluna[1]));
                vacina.setDoses(ObterRegistroInt(coluna[2]));
                vacina.setIntervalo(ObterRegistroInt(coluna[3]));
                vacina.setPeriodicidade(ObterRegistroInt(coluna[4]));
                vacina.setTitulo(ObterRegistroString(coluna[5]));
                vacinas.add(vacina);
            }

            if(vacinas.isEmpty()) return null;

            return vacinas.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int ObterId() {
        var queryStr = "select max(id) from Vacinas";
        
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

    public void deletarPorId(int id) {
        String queryStr = "DELETE FROM Vacinas where id = ?1";
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
