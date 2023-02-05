package com.luca.vacinacao.repositories;

import java.util.ArrayList;
import java.util.List;

import com.luca.vacinacao.models.AlergiaModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
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
        String queryStr = "select alergias.id, alergias.nome from UsuariosAlergias left join alergias on alergiaid = alergias.id where usuarioid = ?1";
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
}
