package dtc.isw.domain;

import java.io.Serializable;

public class Planta implements Serializable {
    String nombre;

    public Planta(String nombre)
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
