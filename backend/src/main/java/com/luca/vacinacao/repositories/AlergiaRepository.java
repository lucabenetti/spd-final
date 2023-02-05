package com.luca.vacinacao.repositories;

import java.util.ArrayList;
import java.util.List;

import com.luca.vacinacao.models.AlergiaModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

public class AlergiaRepository extends BaseRepository {
    private EntityManager entityManager;

    public AlergiaRepository(EntityManagerFactory entityManagerFactory){
        entityManager = entityManagerFactory.createEntityManager();
    }

    public AlergiaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<AlergiaModel> ObterAlergiasAssociadasAoUsuario(int usuarioId){
        String queryStr = "select Alergias.id, Alergias.nome from UsuariosAlergias left join Alergias on alergiaid = Alergias.id where usuarioid = ?1";
        try {
            var alergias = new ArrayList<AlergiaModel>();
            
            Query query = entityManager.createNativeQuery(queryStr);
            query.setParameter(1, usuarioId);
            List<Object[]> linhas = query.getResultList();

            for (Object[] coluna : linhas) {
                var alergia = new AlergiaModel();
                alergia.setId(ObterRegistroInt(coluna[0]));
                alergia.setNome(ObterRegistroString(coluna[1]));
                alergias.add(alergia);
            }

            return alergias;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void Salvar(AlergiaModel alergia){
        String queryStr = "INSERT INTO Alergias values(?1, ?2)";
        try {
            EntityTransaction et = entityManager.getTransaction();

            et.begin();
            Query query = entityManager.createNativeQuery(queryStr);
            query.setParameter(1, alergia.getId());
            query.setParameter(2, alergia.getNome());
            query.executeUpdate();
            et.commit();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<AlergiaModel> ObterTodos() {
        String queryStr = "select id, nome from Alergias";
        try {
            var alergias = new ArrayList<AlergiaModel>();
            
            Query query = entityManager.createNativeQuery(queryStr);
            List<Object[]> linhas = query.getResultList();

            for (Object[] coluna : linhas) {
                var alergia = new AlergiaModel();
                alergia.setId(ObterRegistroInt(coluna[0]));
                alergia.setNome(ObterRegistroString(coluna[1]));
                alergias.add(alergia);
            }

            return alergias;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public AlergiaModel ObterPorId(int id) {
        String queryStr = "select id, nome from Alergias where id = ?1";
        try {
            var alergias = new ArrayList<AlergiaModel>();
            
            Query query = entityManager.createNativeQuery(queryStr);
            query.setParameter(1, id);
            List<Object[]> linhas = query.getResultList();

            for (Object[] coluna : linhas) {
                var alergia = new AlergiaModel();
                alergia.setId(ObterRegistroInt(coluna[0]));
                alergia.setNome(ObterRegistroString(coluna[1]));
                alergias.add(alergia);
            }

            if(alergias.isEmpty()) return null;

            return alergias.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int ObterId() {
        var queryStr = "select max(id) from Alergias";
        
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
        String queryStr = "DELETE FROM Alergias where id = ?1";
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
