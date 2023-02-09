package com.luca.vacinacao.repositories;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.luca.vacinacao.dtos.UsuarioAlergiaDTO;
import com.luca.vacinacao.models.UsuarioModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

public class UsuarioRepository extends BaseRepository {
    private Connection con;

    public UsuarioRepository() throws SQLException{
        con = FabricaDeConexao.obterConexao();
    }

    public void Salvar(UsuarioModel usuario) throws Exception {
        String queryStr = "INSERT INTO Usuarios values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            var query = con.prepareStatement(queryStr);
            query.setInt(1, usuario.getId());
            query.setString(2, usuario.getNome());
            query.setDate(3, new java.sql.Date(usuario.getData_nascimento().getTime()));
            query.setString(4, usuario.getSexo());
            query.setString(5, usuario.getLogradouro());
            query.setInt(6, usuario.getNumero());
            query.setString(7, usuario.getSetor());
            query.setString(8, usuario.getCidade());
            query.setString(9, usuario.getUf());
            query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<UsuarioModel> ObterTodos() throws Exception {
        String queryStr = "select id, cidade, data_nascimento, logradouro, nome, numero, setor, sexo, uf from Usuarios";
        try {
            var usuarios = new ArrayList<UsuarioModel>();
            
            var query = con.prepareStatement(queryStr);
            var result = query.executeQuery();

            while (result.next()) {
                var usuario = new UsuarioModel();
                usuario.setId(ObterRegistroInt(result, 1));
                usuario.setCidade(ObterRegistroString(result, 2));
                usuario.setData_nascimento(ObterRegistroData(result, 3));
                usuario.setLogradouro(ObterRegistroString(result, 4));
                usuario.setNome(ObterRegistroString(result, 5));
                usuario.setNumero(ObterRegistroInt(result, 6));
                usuario.setSetor(ObterRegistroString(result, 7));
                usuario.setSexo(ObterRegistroChar(result, 8));
                usuario.setUf(ObterRegistroString(result, 9));
                usuarios.add(usuario);
            }

            for (UsuarioModel usuario : usuarios) {
                usuario.setAlergias(new AlergiaRepository().ObterAlergiasAssociadasAoUsuario(usuario.getId()));
                usuario.setAgendas(new AgendaRepository().ObterAgendasAssociadaAoUsuario(usuario.getId()));
            }

            return usuarios;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int ObterId() throws Exception {
        var queryStr = "select max(id) from Usuarios";
        
        try {
            
            var query = con.prepareStatement(queryStr);
            var result = query.executeQuery();

            if(result.next()){
                var ultimoId = ObterRegistroInt(result, 1);
                return ultimoId + 1;
            }

            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public UsuarioModel ObterPorId(int id) throws Exception {
        String queryStr = "select id, cidade, data_nascimento, logradouro, nome, numero, setor, sexo, uf from Usuarios where id = ?";
        try {
            var usuarios = new ArrayList<UsuarioModel>();
            
            var query = con.prepareStatement(queryStr);
            query.setInt(1, id);
            var result = query.executeQuery();

            while(result.next()) {
                var usuario = new UsuarioModel();
                usuario.setId(ObterRegistroInt(result, 1));
                usuario.setCidade(ObterRegistroString(result, 2));
                usuario.setData_nascimento(ObterRegistroData(result, 3));
                usuario.setLogradouro(ObterRegistroString(result, 4));
                usuario.setNome(ObterRegistroString(result, 5));
                usuario.setNumero(ObterRegistroInt(result, 6));
                usuario.setSetor(ObterRegistroString(result, 7));
                usuario.setSexo(ObterRegistroChar(result, 8));
                usuario.setUf(ObterRegistroString(result, 9));
                usuarios.add(usuario);
            }

            for (UsuarioModel usuario : usuarios) {
                usuario.setAlergias(new AlergiaRepository().ObterAlergiasAssociadasAoUsuario(usuario.getId()));
                usuario.setAgendas(new AgendaRepository().ObterAgendasAssociadaAoUsuario(usuario.getId()));
            }

            if(usuarios.isEmpty()) return null;

            return usuarios.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void deletarPorId(int id) throws Exception {
        String queryStr = "DELETE FROM Usuarios where id = ?";
        try {
            var query = con.prepareStatement(queryStr);
            query.setInt(1, id);
            query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void associarUsuarioAlergia(List<UsuarioAlergiaDTO> dtos) throws Exception {
        String queryStr = "DELETE FROM UsuariosAlergias where usuarioid = ?";
            try {
                
                var query = con.prepareStatement(queryStr);
                query.setInt(1, dtos.get(0).usuarioId);
                query.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }   

        for (UsuarioAlergiaDTO relacao : dtos) {
            String queryzz = "INSERT INTO UsuariosAlergias values(?, ?)";
            try {
                var query = con.prepareStatement(queryzz);
                query.setInt(1, relacao.usuarioId);
                query.setInt(2, relacao.alergiaId);
                query.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }   
        }
    }
}