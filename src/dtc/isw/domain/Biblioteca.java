package dtc.isw.domain;

import java.io.Serializable;

public class Biblioteca implements Serializable {
    String nombre;

    public Biblioteca(String nombre)
    {
        this.nombre = nombre;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
}
