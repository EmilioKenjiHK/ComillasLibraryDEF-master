package dtc.isw.domain;

import java.io.Serializable;

public class Mesa implements Serializable {
    String nombre;

    public Mesa(String nombre)
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
