package com.luca.vacinacao.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public abstract class BaseRepository {
    public class FabricaDeConexao {
        private static String usuario = "root";
        private static String senha = "my-secret-pw";
    
        private static String textoDeconexao = "jdbc:mysql://host.docker.internal:3306/vacinacao";
    
        public static Connection obterConexao() throws SQLException {
            Connection con = DriverManager.getConnection(textoDeconexao, usuario, senha);
            return con;
        }
    }


    public int ObterRegistroInt(Object registro){
        if (registro == null) {
            return 0; 
        }

        return (int)registro;
    }

    public String ObterRegistroChar(Object registro) {
        if (registro == null) {
            return ""; 
        }

        String valor = "" + (char) registro;

        return valor;
    }

    public String ObterRegistroString(Object registro){
        if (registro == null) {
            return ""; 
        }

        return (String)registro;
    }

    public Date ObterRegistroData(Object registro){
        if(registro == null) {
            return null;
        }

        return (Date)registro;
    }


    public int ObterRegistroInt(ResultSet registro, int index) throws SQLException{
        if (registro == null) {
            return 0; 
        }

        return registro.getInt(index);
    }

    public String ObterRegistroChar(ResultSet registro, int index) throws SQLException {
        if (registro == null) {
            return ""; 
        }

        return registro.getString(index);
    }

    public String ObterRegistroString(ResultSet registro, int index) throws SQLException{
        if (registro == null) {
            return ""; 
        }

        return registro.getString(index);
    }

    public Date ObterRegistroData(ResultSet registro, int index) throws SQLException{
        if(registro == null) {
            return null;
        }

        var datesql = registro.getDate(index);

        return new Date(datesql.getTime());
    }
}
