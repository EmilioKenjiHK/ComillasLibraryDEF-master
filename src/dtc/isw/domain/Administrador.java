package dtc.isw.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Administrador extends Usuario implements Serializable {
    int idAdmin;

    public Administrador(String username, String password, String correo, int puntos, int idAdmin)
    {
        super(username,password,correo,puntos);
        this.idAdmin = idAdmin;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }
}
