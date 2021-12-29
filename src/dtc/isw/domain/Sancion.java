package dtc.isw.domain;

public class Sancion {

    //Variables
    String razon;
    int limite; //horas

    public Sancion(String razon, int limite)
    {
        this.razon = razon;
        this.limite = limite;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

}
