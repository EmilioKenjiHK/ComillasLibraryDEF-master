package dtc.isw.domain;

import java.io.Serializable;

public class Producto implements Serializable {
    //Variables
    String objeto;
    int cantidad;
    int precio;

    public Producto(String objeto, int cantidad, int precio) {
        this.objeto = objeto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
