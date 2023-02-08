package com.luca.vacinacao.repositories;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.luca.vacinacao.models.AlergiaModel;

public class AlergiaRepository extends BaseRepository {
    private Connection con;

    public AlergiaRepository() throws SQLException{
        con = FabricaDeConexao.obterConexao();
    }

    public List<AlergiaModel> ObterAlergiasAssociadasAoUsuario(int usuarioId) throws Exception{
        String queryStr = "select Alergias.id, Alergias.nome from UsuariosAlergias left join Alergias on alergiaid = Alergias.id where usuarioid = ?1";
        try {
            var alergias = new ArrayList<AlergiaModel>();
            
            var query = con.prepareStatement(queryStr);
            query.setInt(1, usuarioId);
            var result = query.executeQuery();

            while (result.next()) {
                var alergia = new AlergiaModel();
                alergia.setId(ObterRegistroInt(result, 1));
                alergia.setNome(ObterRegistroString(result, 2));
                alergias.add(alergia);
            }

            return alergias;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void Salvar(AlergiaModel alergia) throws Exception{
        String queryStr = "INSERT INTO Alergias values(?, ?)";
        try {
            var query = con.prepareStatement(queryStr);
            query.setInt(1, alergia.getId());
            query.setString(2, alergia.getNome());
            query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<AlergiaModel> ObterTodos() throws Exception {
        String queryStr = "select id, nome from Alergias";
        try {
            var alergias = new ArrayList<AlergiaModel>();
            
            var query = con.prepareStatement(queryStr);

            var result = query.executeQuery();
            while (result.next()) {
                var alergia = new AlergiaModel();
                alergia.setId(ObterRegistroInt(result, 1));
                alergia.setNome(ObterRegistroString(result, 2));
                alergias.add(alergia);
            }

            return alergias;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public AlergiaModel ObterPorId(int id) throws Exception {
        String queryStr = "select id, nome from Alergias where id = ?";
        try {
            var alergias = new ArrayList<AlergiaModel>();
            
            var query = con.prepareStatement(queryStr);
            query.setInt(1, id);
            var result = query.executeQuery();

            while (result.next()) {
                var alergia = new AlergiaModel();
                alergia.setId(ObterRegistroInt(result, 1));
                alergia.setNome(ObterRegistroString(result, 2));
                alergias.add(alergia);
            }

            if(alergias.isEmpty()) return null;

            return alergias.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int ObterId() throws Exception {
        var queryStr = "select max(id) from Alergias";
        
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

    public void deletarPorId(int id) throws Exception {
        String queryStr = "DELETE FROM Alergias where id = ?1";
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
