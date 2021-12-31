package dtc.isw.domain;

import java.io.Serializable;

public abstract class Lugar implements Cloneable, Serializable {
    private String id;
    private String nombre;
    protected String type;

    public String getType()
    {
        return type;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getID()
    {
        return id;
    }

    public void setID(String id)
    {
        this.id = id;
    }

    public Object clone() {
        Object clone = null;
        try{
            clone = super.clone();
        }
        catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
