package com.luca.vacinacao.repositories;

import java.util.Date;

public abstract class BaseRepository {
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
}
