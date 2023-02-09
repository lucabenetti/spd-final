package com.luca.vacinacao.repositories;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.luca.vacinacao.models.VacinaModel;

public class VacinaRepository extends BaseRepository {
    private Connection con;

    public VacinaRepository() throws SQLException{
        con = FabricaDeConexao.obterConexao();
    }

    public void Salvar(VacinaModel vacina) throws Exception{
        String queryStr = "INSERT INTO Vacinas values(?, ?, ?, ?, ?, ?)";
        try {

            var query = con.prepareStatement(queryStr);
            query.setInt(1, vacina.getId());
            query.setString(2, vacina.getTitulo());
            query.setString(3, vacina.getDescricao());
            query.setInt(4, vacina.getDoses());
            query.setInt(5, vacina.getPeriodicidade());
            query.setInt(6, vacina.getIntervalo());
            query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<VacinaModel> ObterTodos() throws Exception {
        String queryStr = "select id, descricao, doses, intervalo, periodicidade, titulo from Vacinas";
        try {
            var vacinas = new ArrayList<VacinaModel>();
            
            var query = con.prepareStatement(queryStr);
            var result = query.executeQuery();

            while (result.next()) {
                var vacina = new VacinaModel();
                vacina.setId(ObterRegistroInt(result, 1));
                vacina.setDescricao(ObterRegistroString(result, 2));
                vacina.setDoses(ObterRegistroInt(result, 3));
                vacina.setIntervalo(ObterRegistroInt(result, 4));
                vacina.setPeriodicidade(ObterRegistroInt(result, 5));
                vacina.setTitulo(ObterRegistroString(result, 6));
                vacinas.add(vacina);
            }

            return vacinas;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public VacinaModel ObterPorId(int id) throws Exception {
        String queryStr = "select id, descricao, doses, intervalo, periodicidade, titulo from Vacinas where id = ?";
        try {
            var vacinas = new ArrayList<VacinaModel>();
            
            var query = con.prepareStatement(queryStr);
            query.setInt(1, id);
            var result = query.executeQuery();

            while (result.next()) {
                var vacina = new VacinaModel();
                vacina.setId(ObterRegistroInt(result, 1));
                vacina.setDescricao(ObterRegistroString(result, 2));
                vacina.setDoses(ObterRegistroInt(result, 3));
                vacina.setIntervalo(ObterRegistroInt(result, 4));
                vacina.setPeriodicidade(ObterRegistroInt(result, 5));
                vacina.setTitulo(ObterRegistroString(result, 6));
                vacinas.add(vacina);
            }

            if(vacinas.isEmpty()) return null;

            return vacinas.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int ObterId() throws Exception {
        var queryStr = "select max(id) from Vacinas";
        
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
        String queryStr = "DELETE FROM Vacinas where id = ?1";
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
