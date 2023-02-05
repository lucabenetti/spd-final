package com.luca.vacinacao.repositories;

import java.util.ArrayList;
import java.util.List;

import com.luca.vacinacao.models.UsuarioModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

public class UsuarioRepository extends BaseRepository {
    private EntityManager entityManager;

    public UsuarioRepository(EntityManagerFactory entityManagerFactory){
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void Salvar(UsuarioModel usuario) {
        String queryStr = "INSERT INTO usuarios values(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9)";
        try {
            EntityTransaction et = entityManager.getTransaction();

            et.begin();
            Query query = entityManager.createNativeQuery(queryStr);
            query.setParameter(1, usuario.getId());
            query.setParameter(2, usuario.getCidade());
            query.setParameter(3, usuario.getData_nascimento());
            query.setParameter(4, usuario.getLogradouro());
            query.setParameter(5, usuario.getNome());
            query.setParameter(6, usuario.getNumero());
            query.setParameter(7, usuario.getSetor());
            query.setParameter(8, usuario.getSexo());
            query.setParameter(9, usuario.getUf());
            query.executeUpdate();
            et.commit();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<UsuarioModel> ObterTodos() {
        String queryStr = "select id, cidade, data_nascimento, logradouro, nome, numero, setor, sexo, uf from usuarios";
        try {
            var usuarios = new ArrayList<UsuarioModel>();
            
            Query query = entityManager.createNativeQuery(queryStr);
            List<Object[]> linhas = query.getResultList();

            for (Object[] coluna : linhas) {
                var usuario = new UsuarioModel();
                usuario.setId(ObterRegistroInt(coluna[0]));
                usuario.setCidade(ObterRegistroString(coluna[1]));
                usuario.setData_nascimento(ObterRegistroData(coluna[2]));
                usuario.setLogradouro(ObterRegistroString(coluna[3]));
                usuario.setNome(ObterRegistroString(coluna[4]));
                usuario.setNumero(ObterRegistroInt(coluna[5]));
                usuario.setSetor(ObterRegistroString(coluna[6]));
                usuario.setSexo(ObterRegistroChar(coluna[7]));
                usuario.setUf(ObterRegistroString(coluna[8]));
                usuarios.add(usuario);
            }

            for (UsuarioModel usuario : usuarios) {
                usuario.setAlergias(new AlergiaRepository(entityManager).ObterAlergiasAssociadasAoUsuario(usuario.getId()));
                usuario.setAgendas(new AgendaRepository(entityManager).ObterAgendasAssociadaAoUsuario(usuario.getId()));
            }

            return usuarios;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int ObterId() {
        var queryStr = "select max(id) from usuarios";
        
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

    public UsuarioModel ObterPorId(int id) {
        String queryStr = "select id, cidade, data_nascimento, logradouro, nome, numero, setor, sexo, uf from usuarios where id = ?1";
        try {
            var usuarios = new ArrayList<UsuarioModel>();
            
            Query query = entityManager.createNativeQuery(queryStr);
            query.setParameter(1, id);
            List<Object[]> linhas = query.getResultList();

            for (Object[] coluna : linhas) {
                var usuario = new UsuarioModel();
                usuario.setId(ObterRegistroInt(coluna[0]));
                usuario.setCidade(ObterRegistroString(coluna[1]));
                usuario.setData_nascimento(ObterRegistroData(coluna[2]));
                usuario.setLogradouro(ObterRegistroString(coluna[3]));
                usuario.setNome(ObterRegistroString(coluna[4]));
                usuario.setNumero(ObterRegistroInt(coluna[5]));
                usuario.setSetor(ObterRegistroString(coluna[6]));
                usuario.setSexo(ObterRegistroChar(coluna[7]));
                usuario.setUf(ObterRegistroString(coluna[8]));
                usuarios.add(usuario);
            }

            for (UsuarioModel usuario : usuarios) {
                usuario.setAlergias(new AlergiaRepository(entityManager).ObterAlergiasAssociadasAoUsuario(usuario.getId()));
                usuario.setAgendas(new AgendaRepository(entityManager).ObterAgendasAssociadaAoUsuario(usuario.getId()));
            }

            if(usuarios.isEmpty()) return null;

            return usuarios.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void deletarPorId(int id) {
        String queryStr = "DELETE FROM usuarios where id = ?1";
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